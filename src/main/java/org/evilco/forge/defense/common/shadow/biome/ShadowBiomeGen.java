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
package org.evilco.forge.defense.common.shadow.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowBiomeGen extends BiomeGenBase {

	/**
	 * Stores the biome ID.
	 * @todo Bad coding style
	 */
	public static int BIOME_ID = -1;

	/**
	 * Stores the singleton.
	 */
	private static ShadowBiomeGen instance = null;

	/**
	 * Constructs a new ShadowBiomeGen instance.
	 */
	protected ShadowBiomeGen () {
		super (BIOME_ID);

		// set metadata
		this.setBiomeName ("shadow");
		this.setDisableRain ();

		// clear lists
		this.spawnableMonsterList.clear ();
		this.spawnableCreatureList.clear ();
		this.spawnableWaterCreatureList.clear ();
		this.spawnableCaveCreatureList.clear ();

		// add new spawns
		this.spawnableMonsterList.add (new SpawnListEntry (EntityEnderman.class, 5, 1, 2));
	}

	/**
	 * Returns a singleton instance.
	 * @return The instance.
	 */
	public static ShadowBiomeGen getInstance () {
		if (instance == null) instance = new ShadowBiomeGen ();
		return instance;
	}
}
