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
package org.evilco.mc.defense.common.block.sensor;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.tile.sensor.CameraTileEntity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CameraBlock extends Block implements ITileEntityProvider {

	/**
	 * Constructs a new CameraBlock instance.
	 */
	public CameraBlock () {
		super (Material.iron);

		this.setBlockBounds (0.10f, 0.05f, 0.10f, 0.90f, 0.65f, 0.90f);
		this.setBlockName (DefenseNames.BLOCK_SENSOR_CAMERA);
		this.setBlockTextureName ("defense:sensor/camera");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TileEntity createNewTileEntity (World var1, int var2) {
		return (new CameraTileEntity ());
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
	public boolean shouldSideBeRendered (IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return false;
	}
}