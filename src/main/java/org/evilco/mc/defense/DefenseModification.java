/*
 * Copyright (C) 2014 Evil-Co <http://wwww.evil-co.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.evilco.mc.defense;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.defense.common.DefenseConfiguration;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Mod (modid = DefenseModification.IDENTIFIER, version = DefenseModification.VERSION)
public class DefenseModification {

	/**
	 * Defines the modification identifier.
	 */
	public static final String IDENTIFIER = "baseDefense"; // FIXME: 2.0 will change this to "defense". This is a breaking change!

	/**
	 * Defines the modification version.
	 */
	public static final String VERSION = "1.1.0-SNAPSHOT";

	/**
	 * Stores the modification configuration.
	 */
	protected DefenseConfiguration configuration = null;

	/**
	 * Stores the modification instance.
	 */
	@Mod.Instance
	protected static DefenseModification instance = null;

	/**
	 * Stores the modification logger.
	 */
	protected Logger logger;

	/**
	 * Stores the modification proxy.
	 */
	@SidedProxy (serverSide = "org.evilco.mc.defense.common.CommonModificationProxy", clientSide = "org.evilco.mc.defense.client.ClientModificationProxy")
	protected static IModificationProxy proxy = null;

	/**
	 * Initializes the modification.
	 * @param event The event.
	 */
	@Mod.EventHandler
	public void initialize (FMLInitializationEvent event) {
		proxy.initialize ();
	}

	/**
	 * Pre-Initializes the modification.
	 * @param event The event.
	 */
	@Mod.EventHandler
	public void preInitialize (FMLPreInitializationEvent event) {
		// store logger
		this.logger = event.getModLog ();

		// load configuration
		this.configuration = new DefenseConfiguration (event.getSuggestedConfigurationFile ());

		// initialize proxy
		proxy.preInitialize ();
	}

	/**
	 * Post-Initializes the modification.
	 * @param event The event.
	 */
	@Mod.EventHandler
	public void postInitialize (FMLPostInitializationEvent event) {
		proxy.postInitialize ();
	}

	/**
	 * Returns the modification configuration.
	 * @return The configuration instance.
	 */
	public DefenseConfiguration getConfiguration () {
		return this.configuration;
	}

	/**
	 * Returns the modification instance.
	 * @return The instance.
	 */
	public static DefenseModification getInstance () {
		return instance;
	}

	/**
	 * Returns the modification logger.
	 * @return The logger.
	 */
	public Logger getLogger () {
		return this.logger;
	}

	/**
	 * Returns the modification proxy.
	 * @return The proxy.
	 */
	public static IModificationProxy getProxy () {
		return proxy;
	}
}