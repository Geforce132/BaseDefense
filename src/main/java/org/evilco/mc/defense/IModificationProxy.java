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

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.relauncher.Side;

import java.util.EnumMap;

/**
 * Defines required method structures for proxy implementations.
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IModificationProxy {

	/**
	 * Returns the network channel map.
	 * @return The channel map.
	 */
	public EnumMap<Side, FMLEmbeddedChannel> getChannels ();

	/**
	 * Initializes the proxy implementation.
	 */
	public void initialize ();

	/**
	 * Pre-Initializes the proxy implementation.
	 */
	public void preInitialize ();

	/**
	 * Post-Initializes the proxy implementation.
	 */
	public void postInitialize ();
}