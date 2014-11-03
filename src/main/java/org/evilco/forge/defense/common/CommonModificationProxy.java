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
		try {
			// find fields to modify
			Field field = null;

			try {
				field = Potion.class.getDeclaredField ("field_76425_a");
			} catch (NoSuchFieldException ex) { }

			// try to find named field
			if (field == null) field = Potion.class.getDeclaredField ("potionTypes");

			// allow accessing final fields
			Field modifiers = Field.class.getDeclaredField ("modifiers");
			modifiers.setAccessible (true);
			modifiers.setInt (field, field.getModifiers () & ~Modifier.FINAL);

			// get data
			Potion[] potionTypes = ((Potion[]) field.get (null));

			// get length
			if (potionTypes.length < 256) {
				// create copy
				Potion[] oldPotionTypes = potionTypes;

				// create new array
				potionTypes = new Potion[256];

				// copy data
				System.arraycopy (oldPotionTypes, 0, potionTypes, 0, oldPotionTypes.length);

				// store new type
				field.set (null, potionTypes);
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

		// load configuration
		for (IModule module : this.getActiveModules ()) module.loadConfiguration (configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		this.registerPotions ();
		this.registerBlockEntities ();
		this.registerFluids ();
		this.registerItems ();
		this.registerBlocks ();
		this.registerEntities ();

		this.registerDimensions ();
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
	 * Registers modification dimensions.
	 */
	protected final void registerDimensions () {
		// call modules
		for (IModule module : this.getActiveModules ()) module.registerDimensions ();
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
	 * Registers modification potions.
	 */
	protected final void registerPotions () {
		// call modules
		for (IModule module : this.getActiveModules ()) module.registerPotions ();
	}

	/**
	 * Registers modification block entities.
	 */
	protected final void registerBlockEntities () {
		// call modules
		for (IModule module : this.activeModules) module.registerBlockEntities ();
	}
}
