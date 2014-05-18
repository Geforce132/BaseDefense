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
package org.evilco.defense.common.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.evilco.defense.client.gui.generic.DefenseStationGui;
import org.evilco.defense.common.tile.generic.DefenseStationTileEntity;


public class DefenseGuiHandler implements IGuiHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null; // FIXME: Using a workaround here ...
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
		// get tile entity
		TileEntity tileEntity = world.getTileEntity (x, y, z);

		// defense station
		if (tileEntity instanceof DefenseStationTileEntity) return (new DefenseStationGui (((DefenseStationTileEntity) tileEntity)));

		// not found?!
		return null;
	}
}