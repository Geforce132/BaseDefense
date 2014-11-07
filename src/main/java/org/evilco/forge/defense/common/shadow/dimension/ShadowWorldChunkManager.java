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
package org.evilco.forge.defense.common.shadow.dimension;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowWorldChunkManager extends WorldChunkManager {

	/**
	 * Stores the biome generator to use.
	 */
	private BiomeGenBase biomeGenerator;

	/**
	 * Constructs a new ShadowWorldChunkManager instance.
	 * @param biomeGenerator The biome generator.
	 */
	public ShadowWorldChunkManager (BiomeGenBase biomeGenerator) {
		super ();

		this.biomeGenerator = biomeGenerator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean areBiomesViable (int p_76940_1_, int p_76940_2_, int p_76940_3_, List p_76940_4_) {
		return p_76940_4_.contains (this.biomeGenerator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChunkPosition findBiomePosition (int p_150795_1_, int p_150795_2_, int p_150795_3_, List p_150795_4_, Random p_150795_5_) {
		return (p_150795_4_.contains (this.biomeGenerator) ? new ChunkPosition ((p_150795_1_ - p_150795_3_ + p_150795_5_.nextInt ((p_150795_3_ * 2 + 1))), 0, (p_150795_2_ - p_150795_3_ + p_150795_5_.nextInt ((p_150795_3_ * 2 + 1)))) : null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BiomeGenBase[] getBiomesForGeneration (BiomeGenBase[] p_76937_1_, int p_76937_2_, int p_76937_3_, int p_76937_4_, int p_76937_5_) {
		// create new biome generator array
		if (p_76937_1_ == null || p_76937_1_.length < (p_76937_4_ * p_76937_5_)) p_76937_1_ = new BiomeGenBase[(p_76937_4_ * p_76937_5_)];

		// fill array
		Arrays.fill (p_76937_1_, 0, (p_76937_4_ * p_76937_5_), this.biomeGenerator);
		return p_76937_1_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BiomeGenBase getBiomeGenAt (int p_76935_1_, int p_76935_2_) {
		return this.biomeGenerator;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt (BiomeGenBase[] p_76931_1_, int p_76931_2_, int p_76931_3_, int p_76931_4_, int p_76931_5_, boolean p_76931_6_) {
		return this.loadBlockGeneratorData (p_76931_1_, p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float[] getRainfall (float[] p_76936_1_, int p_76936_2_, int p_76936_3_, int p_76936_4_, int p_76936_5_) {
		// create new rainfall array
		if (p_76936_1_ == null || p_76936_1_.length < (p_76936_4_ * p_76936_5_)) p_76936_1_ = new float[(p_76936_4_ * p_76936_5_)];

		// fill array
		Arrays.fill (p_76936_1_, 0, (p_76936_4_ * p_76936_5_), 0.0f);
		return p_76936_1_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BiomeGenBase[] loadBlockGeneratorData (BiomeGenBase[] p_76933_1_, int p_76933_2_, int p_76933_3_, int p_76933_4_, int p_76933_5_) {
		return this.getBiomesForGeneration (p_76933_1_, p_76933_2_, p_76933_3_, p_76933_4_, p_76933_5_);
	}
}
