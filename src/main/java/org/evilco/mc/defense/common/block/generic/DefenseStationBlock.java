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
package org.evilco.mc.defense.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.evilco.mc.defense.DefenseModification;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;
import org.evilco.mc.defense.common.tile.generic.DefenseStationTileEntity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationBlock extends Block implements ITileEntityProvider {

	/**
	 * Constructs a new DefenseStationBlock instance.
	 */
	public DefenseStationBlock () {
		super (Material.iron);

		this.setBlockName (DefenseNames.BLOCK_GENERIC_DEFENSE_STATION);
		this.setBlockTextureName ("defense:generic/defenseStation");
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
	public boolean isOpaqueCube () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onBlockActivated (World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		// get tile entity
		TileEntity entity = p_149727_1_.getTileEntity (p_149727_2_, p_149727_3_, p_149727_4_);

		// open GUI
		if (entity != null && entity instanceof DefenseStationTileEntity) {
			// check access state
			if (!((DefenseStationTileEntity) entity).isWhitelisted (p_149727_5_)) {
				// display notification
				if (!p_149727_1_.isRemote) p_149727_5_.addChatMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_DEFENSE_STATION_PERMISSION_DENIED));

				// cancel
				return false;
			}

			// open GUI
			p_149727_5_.openGui (DefenseModification.getInstance (), 0, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
			return true;
		}

		// default behaviour
		return super.onBlockActivated (p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldSideBeRendered (IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return false;
	}
}