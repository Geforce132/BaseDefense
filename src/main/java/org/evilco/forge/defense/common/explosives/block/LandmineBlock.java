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
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.evilco.forge.defense.common.DefenseCreativeTab;
import org.evilco.forge.defense.common.explosives.ExplosivesString;
import org.evilco.forge.defense.common.explosives.block.entity.LandmineBlockEntity;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class LandmineBlock extends Block implements ITileEntityProvider {

	/**
	 * Defines the block name.
	 */
	public static final String NAME = "landmine";

	/**
	 * Constructs a new LandmineBlock instance.
	 */
	protected LandmineBlock () {
		super (Material.tnt);

		this.setBlockName (ExplosivesString.BLOCK_NAME_LANDMINE);
		this.setCreativeTab (DefenseCreativeTab.EXPLOSIVES);
		this.setBlockBounds (0.25f, 0.0f, 0.25f, 0.75f, 0.1f, 0.75f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canDropFromExplosion (Explosion p_149659_1_) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canPlaceBlockAt (World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
		// check position
		if (p_149742_3_ == 0) return false;

		// get block
		Block block = p_149742_1_.getBlock (p_149742_2_, (p_149742_3_ - 1), p_149742_4_);

		// check position
		return (p_149742_3_ >= 1 && (Blocks.grass.equals (block) || Blocks.dirt.equals (block) || Blocks.gravel.equals (block) || Blocks.netherrack.equals (block)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TileEntity createNewTileEntity (World p_149915_1_, int p_149915_2_) {
		return (new LandmineBlockEntity ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOpaqueCube () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onBlockDestroyedByExplosion (World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
		super.onBlockDestroyedByExplosion (p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);

		// get entity
		TileEntity tileEntity = p_149723_1_.getTileEntity (p_149723_2_, p_149723_3_, p_149723_4_);

		// check instance
		if (!(tileEntity instanceof LandmineBlockEntity)) return;

		// blow up
		((LandmineBlockEntity) tileEntity).explode ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onNeighborBlockChange (World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
		super.onNeighborBlockChange (p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);

		// drop block if position invalid
		if (!this.canPlaceBlockAt (p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
			// break block
			p_149695_1_.setBlockToAir (p_149695_2_, p_149695_3_, p_149695_4_);

			// drop
			this.dropBlockAsItem (p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 1, 0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldSideBeRendered (IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return false;
	}
}
