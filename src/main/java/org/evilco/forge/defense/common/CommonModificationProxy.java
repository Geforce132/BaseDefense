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
import net.minecraftforge.common.config.Configuration;
import org.evilco.forge.defense.DefenseModification;
import org.evilco.forge.defense.IModificationProxy;
import org.evilco.forge.defense.module.ExplosivesModule;
import org.evilco.forge.defense.module.IModule;

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
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		this.registerBlockEntities ();
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

		// log
		DefenseModification.getInstance ().getLogger ().info ("Explosives enabled: " + this.moduleExplosivesEnabled);
		DefenseModification.getInstance ().getLogger ().info ("Network enabled: " + this.moduleNetworkEnabled);
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
