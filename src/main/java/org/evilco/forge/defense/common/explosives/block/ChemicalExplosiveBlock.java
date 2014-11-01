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
package org.evilco.forge.defense.common.explosives.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.evilco.forge.defense.api.explosive.IExplosiveContainer;
import org.evilco.forge.defense.common.DefenseCreativeTab;
import org.evilco.forge.defense.common.explosives.entity.PrimedChemicalExplosiveEntity;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ChemicalExplosiveBlock extends Block implements IExplosiveContainer {

	/**
	 * Defines the default fuse time in ticks.
	 */
	public static final short FUSE = 160;

	/**
	 * Defines the block name.
	 */
	public static final String NAME = "explosive_chemical";

	/**
	 * Constructs a new ChemicalExplosiveBlock instance.
	 */
	protected ChemicalExplosiveBlock () {
		super (Material.tnt);

		this.setCreativeTab (DefenseCreativeTab.EXPLOSIVES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canConnectRedstone (IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canDropFromExplosion (Explosion p_149659_1_) {
		return false;
	}

	@Override
	public void onNeighborBlockChange (World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
		super.onNeighborBlockChange (p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);

		// check for redstone
		if (p_149695_1_.isBlockIndirectlyGettingPowered (p_149695_2_, p_149695_3_, p_149695_4_)) {
			// set block to air
			p_149695_1_.setBlockToAir (p_149695_2_, p_149695_3_, p_149695_4_);

			// blow up
			this.spawnExplosive (p_149695_1_, ((double) p_149695_2_), ((double) p_149695_3_), ((double) p_149695_4_), FUSE);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spawnExplosive (World world, double x, double y, double z, short fuse) {
		// create entity
		PrimedChemicalExplosiveEntity chemicalExplosiveEntity = new PrimedChemicalExplosiveEntity (world, fuse);

		// set position
		chemicalExplosiveEntity.setPosition (x, y, z);

		// spawn in world
		world.spawnEntityInWorld (chemicalExplosiveEntity);
	}
}
