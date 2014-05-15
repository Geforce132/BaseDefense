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
package org.evilco.defense.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.defense.common.Strings;
import org.evilco.defense.common.tile.TrapTurbineTileEntity;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class TurbineBlock extends Block implements ITileEntityProvider {

	/**
	 * Constructs a new TurbineBlock.
	 */
	public TurbineBlock () {
		super (Material.iron);

		// set block properties
		this.setHardness (0.5f);
		this.setStepSound (Block.soundTypeMetal);
		this.setBlockBounds (0.0f, 0.0f, 0.0f, 1f, 2f, 1f);

		// set creative properties
		this.setCreativeTab (DefenseCreativeTabs.TRAP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TileEntity createNewTileEntity (World var1, int var2) {
		return new TrapTurbineTileEntity ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRenderType () {
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUnlocalizedName () {
		return Strings.BLOCK_TRAP_TURBINE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBlockSolid (IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
		return false;
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
	public boolean renderAsNormalBlock () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldSideBeRendered (IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return false;
	}
}