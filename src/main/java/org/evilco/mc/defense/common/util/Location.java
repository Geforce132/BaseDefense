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
package org.evilco.mc.defense.common.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
	 * Constructs a new Location instance.
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
	 * Constructs a new Location instance.
	 * @param entity The entity to copy a position from.
	 */
	public Location (Entity entity) {
		this (entity.posX, entity.posY, entity.posZ);
	}

	/**
	 * Constructs a new Location instance.
	 * @param vector The vector to copy the coordinates from.
	 */
	public Location (Vec3 vector) {
		this (vector.xCoord, vector.yCoord, vector.zCoord);
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
	 * Moves the location into a specific location.
	 * @param direction The direction.
	 * @param distance The distance.
	 */
	public void moveTowardsDirection (ForgeDirection direction, int distance) {
		this.xCoord += direction.offsetX;
		this.yCoord += direction.offsetY;
		this.zCoord += direction.offsetZ;
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
	 * Returns the vector representation of this location.
	 * @param world The world to create the vector for.
	 * @return The vector.
	 */
	public Vec3 toVector (World world) {
		return world.getWorldVec3Pool ().getVecFromPool (this.xCoord, this.yCoord, this.zCoord);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals (Object obj) {
		// check null
		if (obj == null) return false;

		// call parent
		if (!(obj instanceof Location)) return super.equals (obj);

		// cast
		Location location = ((Location) obj);

		// check
		return (location.xCoord == this.xCoord && location.yCoord == this.yCoord && location.zCoord == this.zCoord);
	}
}