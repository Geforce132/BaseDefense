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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import org.evilco.forge.defense.common.shadow.ShadowString;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowWorldProvider extends WorldProvider {

	/**
	 * Defines the default dimension ID.
	 */
	public static final int DIMENSION_ID = -42;

	/**
	 * Constructs a new ShadowWorldProvider instance.
	 */
	public ShadowWorldProvider () {
		super ();

		this.dimensionId = DIMENSION_ID;
		this.hasNoSky = true;
		this.isHellWorld = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float calculateCelestialAngle (long p_76563_1_, float p_76563_3_) {
		return 0.0f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SideOnly (Side.CLIENT)
	public float[] calcSunriseSunsetColors (float p_76560_1_, float p_76560_2_) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canCoordinateBeSpawn (int p_76566_1_, int p_76566_2_) {
		return this.worldObj.getTopBlock (p_76566_1_, p_76566_2_).getMaterial ().blocksMovement ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canDoLightning (Chunk chunk) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canDoRainSnowIce (Chunk chunk) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canRespawnHere () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IChunkProvider createChunkGenerator () {
		return (new ShadowChunkProvider (this.worldObj, this.worldObj.getSeed ()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SideOnly (Side.CLIENT)
	public boolean doesXZShowFog (int p_76568_1_, int p_76568_2_) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDimensionName () {
		return ShadowString.DIMENSION_NAME_SHADOW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getMovementFactor () {
		return 3.0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSurfaceWorld () {
		return false;
	}
}
