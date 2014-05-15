/*
 * Copyright (C) 2014 Evil-Co <http://wwww.evil-co.com>
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
package org.evilco.defense;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import org.evilco.defense.common.CommonProxy;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Mod (modid = DefenseMod.MOD_ID, version = DefenseMod.VERSION)
public class DefenseMod {

	/**
	 * Defines the mod identification.
	 */
	public static final String MOD_ID = "baseDefense";

	/**
	 * Defines the mod version.
	 */
	public static final String VERSION = "1.0.0";

	/**
	 * Stores the proxy implementation.
	 */
	@SidedProxy (clientSide = "org.evilco.defense.client.ClientProxy", serverSide = "org.evilco.defense.common.CommonProxy")
	public static CommonProxy proxy;

	/**
	 * Modification entry point.
	 * @param event
	 */
	@Mod.EventHandler
	public void initialize (FMLInitializationEvent event) {
		proxy.registerBlocks ();
		proxy.registerTileEntities ();
		proxy.registerRenderers ();
	}
}