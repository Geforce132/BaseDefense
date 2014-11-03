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

import cpw.mods.fml.common.eventhandler.Event;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import org.evilco.forge.defense.common.shadow.ShadowBlock;

import java.util.List;
import java.util.Random;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowChunkProvider implements IChunkProvider {

	/**
	 * Stores a list of biomes to generate.
	 */
	private BiomeGenBase[] biomesForGeneration;

	// TODO: Document
	private double[] field_147434_q;

	private MapGenBase generatorCave = new MapGenCaves ();

	// Noise arrays
	private double[] noiseArray1;
	private double[] noiseArray2;
	private double[] noiseArray3;
	private double[] noiseArray4;

	// Noise generators
	private NoiseGeneratorOctaves noiseGenerator1;
	private NoiseGeneratorOctaves noiseGenerator2;
	private NoiseGeneratorOctaves noiseGenerator3;
	private NoiseGeneratorPerlin noiseGenerator4;
	private NoiseGeneratorOctaves noiseGenerator5;
	private NoiseGeneratorOctaves noiseGenerator6;
	private NoiseGeneratorOctaves noiseGeneratorMobSpawner;

	/**
	 * Stores some parabolic stuff.
	 */
	private float[] parabolicField;

	/**
	 * Stores the current random number generator.
	 */
	@Getter (AccessLevel.PROTECTED)
	private Random random;

	/**
	 * Stores the stone noise.
	 */
	private double[] stoneNoise = new double[256];

	/**
	 * Stores the world.
	 */
	@Getter (AccessLevel.PROTECTED)
	private World world;

	/**
	 * Constructs a new ShadowChunkProvider instance.
	 * @param world The world.
	 * @param seed The seed.
	 */
	public ShadowChunkProvider (World world, long seed) {
		// store arguments
		this.world = world;
		this.random = new Random (seed);

		// create noise generator instances
		NoiseGenerator[] generators = new NoiseGenerator[] {
			new NoiseGeneratorOctaves (this.random, 16),
			new NoiseGeneratorOctaves (this.random, 16),
			new NoiseGeneratorOctaves (this.random, 8),
			new NoiseGeneratorPerlin (this.random, 4),
			new NoiseGeneratorOctaves (this.random, 10),
			new NoiseGeneratorOctaves (this.random, 16),
			new NoiseGeneratorOctaves (this.random, 8)
		};

		// call hooks
		generators = TerrainGen.getModdedNoiseGenerators (this.world, this.random, generators);

		// store generators
		this.noiseGenerator1 = ((NoiseGeneratorOctaves) generators[0]);
		this.noiseGenerator2 = ((NoiseGeneratorOctaves) generators[1]);
		this.noiseGenerator3 = ((NoiseGeneratorOctaves) generators[2]);
		this.noiseGenerator4 = ((NoiseGeneratorPerlin) generators[3]);
		this.noiseGenerator5 = ((NoiseGeneratorOctaves) generators[4]);
		this.noiseGenerator6 = ((NoiseGeneratorOctaves) generators[5]);
		this.noiseGeneratorMobSpawner = ((NoiseGeneratorOctaves) generators[6]);

		// initialize arrays
		this.field_147434_q = new double[825];
		this.parabolicField = new float[25];

		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				this.parabolicField[(i + 2 + (j + 2) * 5)] = (10.0f / MathHelper.sqrt_float (((i * i + j * j) + 0.2f)));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canSave () {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chunkExists (int p_73149_1_, int p_73149_2_) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChunkPosition func_147416_a (World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
		return null;
	}

	public void generateNoise (int p_147423_1_, int p_147423_2_, int p_147423_3_) {
		// initialize variables
		double d0 = 684.412D;
		double d1 = 684.412D;
		double d2 = 512.0D;
		double d3 = 512.0D;

		// generate some fun noise
		this.noiseArray1 = this.noiseGenerator3.generateNoiseOctaves (this.noiseArray1, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001d, 4.277575000000001d, 8.555150000000001d);
		this.noiseArray2 = this.noiseGenerator1.generateNoiseOctaves (this.noiseArray2, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412d, 684.412d, 684.412d);
		this.noiseArray3 = this.noiseGenerator2.generateNoiseOctaves (this.noiseArray3, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412d, 684.412d, 684.412d);
		this.noiseArray4 = this.noiseGenerator6.generateNoiseOctaves (this.noiseArray4, p_147423_1_, p_147423_3_, 5, 5, 200.0d, 200.0d, 0.5d);

		// initialize some more
		boolean flag1 = false;
		boolean flag = false;
		int l = 0;
		int i1 = 0;
		double d4 = 8.5d;

		for (int j1 = 0; j1 < 5; ++j1) {
			for (int k1 = 0; k1 < 5; ++k1) {
				float f = 0.0f;
				float f1 = 0.0f;
				float f2 = 0.0f;
				byte b0 = 2;
				BiomeGenBase biomegenbase = this.biomesForGeneration[(j1 + 2 + (k1 + 2) * 10)];

				for (int l1 = -b0; l1 <= b0; ++l1) {
					for (int i2 = -b0; i2 <= b0; ++i2) {
						BiomeGenBase biomegenbase1 = this.biomesForGeneration[(j1 + l1 + 2 + (k1 + i2 + 2) * 10)];
						float f3 = biomegenbase1.rootHeight;
						float f4 = biomegenbase1.heightVariation;

						float f5 = (this.parabolicField[(l1 + 2 + (i2 + 2) * 5)] / (f3 + 2.0f));
						if (biomegenbase1.rootHeight > biomegenbase.rootHeight) f5 /= 2.0f;

						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}
				}

				f /= f2;
				f1 /= f2;
				f = (f * 0.9f + 0.1f);
				f1 = ((f1 * 4.0f - 1.0f) / 8.0f);
				double d12 = (this.noiseArray4[i1] / 8000.0d);

				if (d12 < 0.0d) d12 = -d12 * 0.3d;
				d12 = d12 * 3.0d - 2.0d;

				if (d12 < 0.0d) {
					d12 /= 2.0d;
					if (d12 < -1.0d) d12 = -1.0d;

					d12 /= 1.4d;
					d12 /= 2.0d;
				} else {
					if (d12 > 1.0d) d12 = 1.0d;
					d12 /= 8.0d;
				}

				++i1;
				double d13 = ((double) f1);
				double d14 = ((double) f);
				d13 += (d12 * 0.2d);
				d13 = (d13 * 8.5d / 8.0d);
				double d5 = (8.5d + d13 * 4.0d);

				for (int j2 = 0; j2 < 33; ++j2) {
					double d6 = (((double) j2 - d5) * 12.0d * 128.0d / 256.0d / d14);

					if (d6 < 0.0d) d6 *= 4.0d;

					double d7 = (this.noiseArray2[l] / 512.0d);
					double d8 = (this.noiseArray3[l] / 512.0d);
					double d9 = ((this.noiseArray1[l] / 10.0d + 1.0d) / 2.0d);
					double d10 = (MathHelper.denormalizeClamp (d7, d8, d9) - d6);

					if (j2 > 29) {
						double d11 = ((double) ((float)(j2 - 29) / 3.0f));
						d10 = (d10 * (1.0D - d11) + -10.0D * d11);
					}

					this.field_147434_q[l] = d10;
					++l;
				}
			}
		}
	}

	/**
	 * Generates the terrain for a chunk.
	 * @param x The X-Coordinate.
	 * @param y The Y-Coordinate.
	 * @param blocks The block matrix.
	 */
	public void generateTerrain (int x, int y, Block[] blocks) {
		// initialize variables
		byte b0 = 63;
		this.biomesForGeneration = this.world.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, (x * 4 - 2), (y * 4 - 2), 10, 10);

		// generate some noise
		this.generateNoise ((x * 4), 0, (y * 4));

		// start generating
		for (int k = 0; k < 4; ++k) {
			int l = (k * 5);
			int i1 = ((k + 1) * 5);

			for (int j1 = 0; j1 < 4; ++j1) {
				int k1 = ((l + j1) * 33);
				int l1 = ((l + j1 + 1) * 33);
				int i2 = ((i1 + j1) * 33);
				int j2 = ((i1 + j1 + 1) * 33);

				for (int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125d;
					double d1 = this.field_147434_q[k1 + k2];
					double d2 = this.field_147434_q[l1 + k2];
					double d3 = this.field_147434_q[i2 + k2];
					double d4 = this.field_147434_q[j2 + k2];
					double d5 = ((this.field_147434_q[k1 + k2 + 1] - d1) * d0);
					double d6 = ((this.field_147434_q[l1 + k2 + 1] - d2) * d0);
					double d7 = ((this.field_147434_q[i2 + k2 + 1] - d3) * d0);
					double d8 = ((this.field_147434_q[j2 + k2 + 1] - d4) * d0);

					for (int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25d;
						double d10 = d1;
						double d11 = d2;
						double d12 = ((d3 - d1) * d9);
						double d13 = ((d4 - d2) * d9);

						for (int i3 = 0; i3 < 4; ++i3) {
							int j3 = (i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2);
							short short1 = 256;
							j3 -= short1;
							double d14 = 0.25d;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3) {
								if ((d15 += d16) > 0.0d)
									blocks[j3 += short1] = ShadowBlock.SHADOW_MATTER;
								else if (k2 * 8 + l2 < b0)
									blocks[j3 += short1] = Blocks.water; // TODO: Use shadow matter
								else
									blocks[j3 += short1] = null;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List getPossibleCreatures (EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
		// get biome generator
		BiomeGenBase biomeGenerator = this.world.getBiomeGenForCoords (p_73155_2_, p_73155_4_);

		// collect list
		// TODO: Scattered features ... maybe?
		return biomeGenerator.getSpawnableList (p_73155_1_);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLoadedChunkCount () {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Chunk loadChunk (int p_73158_1_, int p_73158_2_) {
		return this.provideChunk (p_73158_1_, p_73158_2_);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String makeString () {
		return "RandomLevelSource";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populate (IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
		// enable instant block falling
		BlockFalling.fallInstantly = true;

		// calculate chunk coordinates
		int x = (p_73153_2_ * 16);
		int z = (p_73153_3_ * 16);

		// get generator
		BiomeGenBase biomeGenerator = this.world.getBiomeGenForCoords ((x + 16), (z + 16));

		// update seed
		this.random.setSeed (this.world.getSeed ());

		// initialize populator flag
		boolean flag = false;

		// fire event
		MinecraftForge.EVENT_BUS.post (new PopulateChunkEvent.Pre (p_73153_1_, this.world, this.random, p_73153_2_, p_73153_3_, flag));

		// initialize position
		int generatorX;
		int generatorY;
		int generatorZ;

		// populate shadow lakes
		if (!flag && this.random.nextInt (4) == 0 && TerrainGen.populate (p_73153_1_, this.world, this.random, p_73153_2_, p_73153_3_, flag, PopulateChunkEvent.Populate.EventType.LAKE)) {
			// generate position
			generatorX = (x + this.random.nextInt (16) + 8);
			generatorY = this.random.nextInt (256);
			generatorZ = (z + this.random.nextInt (16) + 8);

			// generate a nice little lake
			// TODO: Replace water against dark water
			(new WorldGenLakes (Blocks.water)).generate (this.world, this.random, generatorX, generatorY, generatorZ);
		}

		// populate lava lakes
		if (!flag && this.random.nextInt (8) == 0 && TerrainGen.populate (p_73153_1_, this.world, this.random, p_73153_2_, p_73153_3_, flag, PopulateChunkEvent.Populate.EventType.LAVA)) {
			// generate position
			generatorX = (x + this.random.nextInt (16) + 8);
			generatorY = (this.random.nextInt (248) + 8);
			generatorZ = (z + this.random.nextInt (16) + 8);

			// generate a lava lake
			if (generatorY < 63 && this.random.nextInt (10) == 0) (new WorldGenLakes (Blocks.lava)).generate (this.world, this.random, generatorX, generatorY, generatorZ);
		}

		// decorate chunk
		biomeGenerator.decorate (this.world, this.random, x, z);

		// TODO: Populate shadow animals

		// fire event
		MinecraftForge.EVENT_BUS.post (new PopulateChunkEvent.Post (p_73153_1_, this.world, this.random, p_73153_2_, p_73153_3_, flag));

		// disable instant block falling
		BlockFalling.fallInstantly = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Chunk provideChunk (int p_73154_1_, int p_73154_2_) {
		// update seed
		this.random.setSeed (((p_73154_1_ * 341873128712l) + (p_73154_2_ * 132897987541l)));

		// initialize variables
		Block[] blocks = new Block[(16 * 16 * 256)];
		byte[] metadata = new byte[blocks.length];

		// generate terrain
		this.generateTerrain (p_73154_1_, p_73154_2_, blocks);

		// collect a list of biomes to generate
		this.biomesForGeneration = this.world.getWorldChunkManager ().loadBlockGeneratorData (this.biomesForGeneration, (p_73154_1_ * 16), (p_73154_2_ * 16), 16, 16);

		// replace blocks
		this.replaceBlocksForBiome (p_73154_1_, p_73154_2_, blocks, metadata, this.biomesForGeneration);

		// apply generators
		this.generatorCave.func_151539_a (this, this.world, p_73154_1_, p_73154_2_, blocks);
		// TODO: Add ravines
		// TODO: Add shadow specific features

		// create a new chunk
		Chunk chunk = new Chunk (this.world, blocks, metadata, p_73154_1_, p_73154_2_);

		// get biome array
		byte[] biomeArray = chunk.getBiomeArray ();

		// populate biomes
		for (int i = 0; i < biomeArray.length; i++) {
			biomeArray[i] = ((byte) this.biomesForGeneration[i].biomeID);
		}

		// generate sky light map
		chunk.generateSkylightMap ();

		// return finished chunk instance
		return chunk;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recreateStructures (int p_82695_1_, int p_82695_2_) { }

	/**
	 * Replaces the blocks in a generated chunk with biome specific structures.
	 * @param p_147422_1_ The X-Coordinate.
	 * @param p_147422_2_ The Y-Coordinate.
	 * @param p_147422_3_ The block matrix.
	 * @param p_147422_4_ The metadata matrix.
	 * @param p_147422_5_ The biome generator array.
	 */
	public void replaceBlocksForBiome (int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_) {
		// fire event
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks (this, p_147422_1_, p_147422_2_, p_147422_3_, p_147422_4_, p_147422_5_, this.world);
		MinecraftForge.EVENT_BUS.post (event);

		// check for cancellation
		if (event.getResult () == Event.Result.DENY) return;

		// generate noise
		double d0 = 0.03125d;
		this.stoneNoise = this.noiseGenerator4.func_151599_a (this.stoneNoise, (double) (p_147422_1_ * 16), (double) (p_147422_2_ * 16), 16, 16, (d0 * 2.0d), (d0 * 2.0d), 1.0d);

		for (int k = 0; k < 16; ++k) {
			for (int l = 0; l < 16; ++l) {
				BiomeGenBase biomegenbase = p_147422_5_[(l + k * 16)];
				biomegenbase.genTerrainBlocks (this.world, this.random, p_147422_3_, p_147422_4_, (p_147422_1_ * 16 + k), (p_147422_2_ * 16 + l), this.stoneNoise[(l + k * 16)]);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean saveChunks (boolean p_73151_1_, IProgressUpdate p_73151_2_) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveExtraData () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean unloadQueuedChunks () {
		return false;
	}
}
