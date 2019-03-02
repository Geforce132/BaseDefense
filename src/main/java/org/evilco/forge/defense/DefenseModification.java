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
package org.evilco.forge.defense;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lombok.Getter;
import net.minecraftforge.common.config.Configuration;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
@Mod (modid = DefenseModification.MOD_ID, useMetadata = true, canBeDeactivated = false)
public class DefenseModification {

	/**
	 * Defines the modification identifier.
	 */
	public static final String MOD_ID = "Defense";

	/**
	 * Stores the modification instance
	 */
	@Mod.Instance (MOD_ID)
	public static DefenseModification instance = new DefenseModification();

	/**
	 * Stores the active proxy instance.
	 */
	@SidedProxy (serverSide = "org.evilco.forge.defense.common.CommonModificationProxy", clientSide = "org.evilco.forge.defense.client.ClientModificationProxy")
	private static IModificationProxy proxy = null;

	/**
	 * Stores the taco state.
	 */
	@Getter
	private boolean tacos = true;

	/**
	 * Stores the logger.
	 */
	@Getter
	private Logger logger = null;

	/**
	 * Callback for {@link cpw.mods.fml.common.event.FMLPreInitializationEvent}.
	 * @param event The event.
	 */
	@Mod.EventHandler
	public void onPreInitialize (FMLPreInitializationEvent event) {
		// store logger
		this.logger = event.getModLog ();

		// load configuration
		Configuration configuration = new Configuration (event.getSuggestedConfigurationFile ());
		this.tacos = configuration.getBoolean ("tacos", "module", this.tacos, "Enables tacos.");

		// load configuration
		configuration.load ();

		// do some serious work
		this.getLogger ().info ("Adding tacos to the game ...");
		if (this.tacos)
			proxy.registerTacos ();
		else
			this.getLogger ().info ("Tacos are currently disabled ... WHY?!?!");

		// call proxy
		proxy.preInitialize (configuration);
	}

	/**
	 * Callback for {@link cpw.mods.fml.common.event.FMLInitializationEvent}.
	 * @param event The event.
	 */
	@Mod.EventHandler
	public void onInitialize (FMLInitializationEvent event) {
		proxy.initialize ();
	}

	/**
	 * Callback for {@link cpw.mods.fml.common.event.FMLPostInitializationEvent}.
	 * @param event The event.
	 */
	@Mod.EventHandler
	public void onPostInitialize (FMLPostInitializationEvent event) {
		// TODO: Handle soft dependencies here

		// call proxy
		proxy.postInitialize ();

		// some more serious work
		if (this.tacos) {
			// log
			this.getLogger ().info ("Removing tacos from the game ...");

			// unregister
			proxy.unregisterTacos ();
		}
	}
	
	public Logger getLogger() {
		return logger;
	}
}
