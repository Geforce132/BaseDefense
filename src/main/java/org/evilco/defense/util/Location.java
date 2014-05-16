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
package org.evilco.defense.util;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class Location {

	/**
	 * Stores the X world coordinate.
	 */
	public double xCoord;

	/**
	 * Stores the Y world coordinate.
	 */
	public double yCoord;

	/**
	 * Stores the Z world coordinate.
	 */
	public double zCoord;

	/**
	 * Constructs a new Location.
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @param z The Z coordinate.
	 */
	public Location (double x, double y, double z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}

	/**
	 * Returns the block stored at this location.
	 * @param world The world to search in.
	 * @return The block.
	 */
	public Block getBlock (World world) {
		return world.getBlock ((int) this.xCoord, (int) this.yCoord, (int) this.zCoord);
	}

	/**
	 * Returns the tile entity at this location.
	 * @param world The world to search in.
	 * @return The tile entity.
	 */
	public TileEntity getTileEntity (World world) {
		return world.getTileEntity ((int) this.xCoord, (int) this.yCoord, (int) this.zCoord);
	}

	/**
	 * Reads a location from NBT.
	 * @param compound The nbt tag.
	 * @return The location.
	 */
	public static Location readFromNBT (NBTTagCompound compound) {
		// verify data structure
		if (!compound.hasKey ("x") || !compound.hasKey ("y") || !compound.hasKey ("z")) return null;

		// get coordinates
		double x = compound.getDouble ("x");
		double y = compound.getDouble ("y");
		double z = compound.getDouble ("z");

		// construct location
		return (new Location (x, y, z));
	}

	/**
	 * Writes a location to NBT.
	 * @param compound The nbt tag.
	 */
	public void writeToNBT (NBTTagCompound compound) {
		compound.setDouble ("x", this.xCoord);
		compound.setDouble ("y", this.yCoord);
		compound.setDouble ("z", this.zCoord);
	}
}