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
package org.evilco.defense.common.block.generic;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.evilco.defense.DefenseMod;
import org.evilco.defense.common.DefenseItem;
import org.evilco.defense.common.Strings;
import org.evilco.defense.common.tile.generic.DefenseStationTileEntity;

import java.util.Random;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationBlock extends Block implements ITileEntityProvider {

	/**
	 * Constructs a new DefenseStationBlock.
	 */
	public DefenseStationBlock () {
		super (Material.iron);

		// apply properties
		this.setHardness (0.5f);
		this.setStepSound (Block.soundTypeMetal);
		this.setBlockBounds (0.05f, 0.0f, 0.05f, 0.95f, 2.0f, 0.95f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TileEntity createNewTileEntity (World var1, int var2) {
		return (new DefenseStationTileEntity ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item getItemDropped (int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return DefenseItem.GENERIC_DEFENSE_STATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack getPickBlock (MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack (DefenseItem.GENERIC_DEFENSE_STATION, 1);
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
		return Strings.BLOCK_GENERIC_DEFENSE_STATION;
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
	public boolean onBlockActivated (World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (FMLCommonHandler.instance ().getEffectiveSide () != Side.CLIENT) return false;

		// get tile entity
		TileEntity tileEntity = p_149727_1_.getTileEntity (p_149727_2_, p_149727_3_, p_149727_4_);

		// verify
		if (tileEntity == null || !(tileEntity instanceof DefenseStationTileEntity)) {
			// update block (something went wrong so we void the block ... sorry)
			p_149727_1_.setBlock (p_149727_2_, p_149727_3_, p_149727_4_, Block.getBlockFromName ("air"), 0, 2);

			// skip event
			return false;
		}

		// cast
		DefenseStationTileEntity defenseStationTileEntity = ((DefenseStationTileEntity) tileEntity);

		// verify owner
		if (defenseStationTileEntity.getOwner () == null || !defenseStationTileEntity.getOwner ().equals (p_149727_5_.getPersistentID ())) {
			// notify user
			p_149727_5_.addChatMessage (new ChatComponentTranslation ("defense.generic.defenseStation.accessDenied"));

			// skip event
			return false;
		}

		// open GUI
		p_149727_5_.openGui (DefenseMod.instance, 0, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);

		// yep! worked ;-)
		return true;
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