/*
 * Copyright 2014 Johannes Donath <johannesd@evil-co.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.evilco.forge.defense.common;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.config.Configuration;
import org.evilco.forge.defense.DefenseModification;
import org.evilco.forge.defense.IModificationProxy;
import org.evilco.forge.defense.module.ExplosivesModule;
import org.evilco.forge.defense.module.IModule;
import org.evilco.forge.defense.module.ShadowModule;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class CommonModificationProxy implements IModificationProxy {

	/**
	 * Stores a list of active modules.
	 */
	@Getter (AccessLevel.PROTECTED)
	private List<IModule> activeModules = new ArrayList<IModule> ();

	/**
	 * Defines whether explosives are enabled.
	 */
	private boolean moduleExplosivesEnabled = true;

	/**
	 * Defines whether the network module is enabled.
	 */
	@Getter
	private boolean moduleNetworkEnabled = true;

	/**
	 * Defines whether the shadow module is enabled.
	 */
	@Getter
	private boolean moduleShadowEnabled = true;

	/**
	 * Applies dirty fixes.
	 */
	protected void applyDirtyFixes () {
		// get potion field
		Field potionField = null;

		try { potionField = Potion.class.getDeclaredField ("potionTypes"); } catch (NoSuchFieldException ignore) { }
		if (potionField == null) try { potionField = Potion.class.getDeclaredField ("field_76425_a"); } catch (NoSuchFieldException ignore) { }

		// ensure we got a field
		Preconditions.checkNotNull (potionField, "potionField");

		// make accessible
		potionField.setAccessible (true);

		try {
			Field modifierField = Field.class.getDeclaredField ("modifiers");
			modifierField.setAccessible (true);
			modifierField.setInt (potionField, (potionField.getModifiers () & ~Modifier.FINAL));
		} catch (Exception ignore) { }

		// resize potion field
		try {
			Potion[] potionTypes = ((Potion[]) potionField.get (null));

			// verify size
			if (potionTypes.length < 256) {
				// create new array
				final Potion[] newPotionTypes = new Potion[256];

				// copy array elements
				System.arraycopy (potionTypes, 0, newPotionTypes, 0, potionTypes.length);

				// update field
				potionField.set (null, newPotionTypes);
			}
		} catch (Exception ex) {
			DefenseModification.getInstance ().getLogger ().error (ex);
		}
	}

	/**
	 * Enables a module.
	 * @param module The module.
	 */
	protected void enableModule (IModule module) {
		this.activeModules.add (module);
	}

	/**
	 * Loads all modification modules.
	 */
	protected void loadModules () {
		if (this.moduleExplosivesEnabled) this.enableModule (new ExplosivesModule ());
		if (this.moduleShadowEnabled) this.enableModule (new ShadowModule ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preInitialize (Configuration configuration) {
		// read configuration options
		this.readConfiguration (configuration);

		// enable modules
		this.loadModules ();

		// apply dirty fixes
		this.applyDirtyFixes ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		this.registerBlockEntities ();
		this.registerFluids ();
		this.registerItems ();
		this.registerBlocks ();
		this.registerEntities ();

		this.registerCraftingRecipes ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postInitialize () { }

	/**
	 * Reads the modification configuration.
	 * @param configuration The configuration.
	 */
	protected void readConfiguration (Configuration configuration) {
		this.moduleExplosivesEnabled = configuration.getBoolean ("explosives", "module", this.moduleExplosivesEnabled, "Enables explosives.");
		this.moduleNetworkEnabled = configuration.getBoolean ("network", "module", this.moduleNetworkEnabled, "Enables the security network.");
		this.moduleShadowEnabled = configuration.getBoolean ("shadow", "module", this.moduleShadowEnabled, "Enables the shadow realm.");

		// log
		DefenseModification.getInstance ().getLogger ().info ("Explosives enabled: " + this.moduleExplosivesEnabled);
		DefenseModification.getInstance ().getLogger ().info ("Network enabled: " + this.moduleNetworkEnabled);
		DefenseModification.getInstance ().getLogger ().info ("Shadow enabled: " + this.moduleShadowEnabled);
	}

	/**
	 * Registers modification blocks.
	 */
	protected final void registerBlocks () {
		// call modules
		for (IModule module : this.activeModules) module.registerBlocks ();
	}

	/**
	 * Registers modification crafting recipes.
	 */
	protected final void registerCraftingRecipes () {
		// call modules
		for (IModule module : this.getActiveModules ()) module.registerCraftingRecipes ();
	}

	/**
	 * Registers modification entities.
	 */
	protected final void registerEntities () {
		// call modules
		for (IModule module : this.getActiveModules ()) module.registerEntities ();
	}

	/**
	 * Registers modification fluids.
	 */
	protected final void registerFluids () {
		// call modules
		for (IModule module : this.getActiveModules ()) module.registerFluids ();
	}

	/**
	 * Registers modification items.
	 */
	protected final void registerItems () {
		// call modules
		for (IModule module : this.activeModules) module.registerItems ();
	}

	/**
	 * Registers modification block entities.
	 */
	protected final void registerBlockEntities () {
		// call modules
		for (IModule module : this.activeModules) module.registerBlockEntities ();
	}
}
