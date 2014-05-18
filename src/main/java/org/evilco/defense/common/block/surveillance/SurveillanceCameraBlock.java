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
package org.evilco.defense.common.block.surveillance;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseItem;
import org.evilco.defense.common.Strings;
import org.evilco.defense.common.tile.surveillance.SurveillanceCameraTileEntity;

import java.util.Random;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SurveillanceCameraBlock extends Block implements ITileEntityProvider {

	/**
	 * Constructs a new SurveillanceCameraBlock.
	 */
	public SurveillanceCameraBlock () {
		super (Material.iron);

		this.setHardness (0.5f);
		this.setStepSound (Block.soundTypeMetal);
		this.setBlockBounds (0.10f, 0.05f, 0.10f, 0.90f, 0.65f, 0.90f);
		this.setBlockTextureName ("defense:surveillance/camera");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TileEntity createNewTileEntity (World var1, int var2) {
		return (new SurveillanceCameraTileEntity ());
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
		return Strings.BLOCK_SURVEILLANCE_CAMERA;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item getItemDropped (int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return DefenseItem.SURVEILLANCE_CAMERA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int damageDropped (int p_149692_1_) {
		return (p_149692_1_ > 1000 ? 1 : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack getPickBlock (MovingObjectPosition target, World world, int x, int y, int z) {
		// get tile entity
		SurveillanceCameraTileEntity tileEntity = ((SurveillanceCameraTileEntity) world.getTileEntity (x, y, z));

		// return correct item
		return (new ItemStack (DefenseItem.SURVEILLANCE_CAMERA, 1, (tileEntity.isScanningMobs () ? 1 : 0)));
	}
}