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
package org.evilco.mc.defense.common.tile.machine;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.evilco.mc.defense.common.tile.AbstractTileEntity;
import org.evilco.mc.defense.common.tile.IRotateableTileEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class RollingMillTileEntity extends AbstractTileEntity implements IRotateableTileEntity {

	/**
	 * Defines the drum rotation speed.
	 */
	public static final double DRUM_SPEED = 3;

	/**
	 * Defines how often a player has to click the rolling mill to get the result.
	 */
	public static final int PROCESSING_HITS = 30;

	/**
	 * Stores all possible crafting recipes.
	 */
	protected static final Map<Item, ItemStack> craftingMap = new HashMap<Item, ItemStack> ();

	/**
	 * Stores the current drum angle.
	 */
	protected double drumAngle = 0.0;

	/**
	 * Stores the currently active item.
	 */
	protected Item item = null;

	/**
	 * Stores the amount of ticks left until processing finishes.
	 */
	protected int processing = 0;

	/**
	 * Adds a recipe to the rolling mill.
	 * @param item The item.
	 * @param itemStack The result item.
	 */
	public static void addRecipe (Item item, ItemStack itemStack) {
		craftingMap.put (item, itemStack);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Packet getDescriptionPacket () {
		// create compound
		NBTTagCompound compound = new NBTTagCompound ();

		// write data
		this.writeToNBT (compound);

		// create packet
		return (new S35PacketUpdateTileEntity (this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata (), compound));
	}

	/**
	 * Returns the drum rotation.
	 * @param partialTicks The ticks since last render.
	 * @return The rotation angle for the drums.
	 */
	public double getDrumRotation (float partialTicks) {
		return Math.toRadians (this.drumAngle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getRotationAngle () {
		return (this.getBlockMetadata () * 90.0f + 90.0f);
	}

	/**
	 * Inserts an item into the rolling mill.
	 * @param item The item.
	 * @return True if the insertion was successful.
	 */
	public boolean insertItem (Item item) {
		if (this.item != null) return false;
		if (!this.craftingMap.containsKey (item)) return false;

		// input item
		this.item = item;

		// reset processing timer
		this.processing = PROCESSING_HITS;
		this.drumAngle = 0;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);

		// notify
		return true;
	}

	/**
	 * Checks whether the mill is processing.
	 * @return True if the mill is processing.
	 */
	public boolean isProcessing () {
		return (this.item != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT (pkt.func_148857_g ());
	}

	/**
	 * Processes an item.
	 */
	public void process () {
		// update drum angle
		if (this.worldObj.isRemote) {
			// update drum angle
			this.drumAngle += DRUM_SPEED;

			// exit
			return;
		}

		// reduce ticks
		this.processing = Math.max (0, (this.processing - 1));

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readFromNBT (NBTTagCompound p_145839_1_) {
		super.readFromNBT (p_145839_1_);

		if (p_145839_1_.hasKey ("item"))
			this.item = ((Item) Item.itemRegistry.getObject (p_145839_1_.getString ("item")));
		else
			this.item = null;

		this.processing = p_145839_1_.getInteger ("processing");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity () {
		super.updateEntity ();

		// skip client execution
		if (this.worldObj.isRemote) return;

		// skip if no item is inside
		if (this.item == null) return;

		// spit out result
		if (this.processing == 0) {
			// find result
			ItemStack result = craftingMap.get (this.item).copy ();

			// create entity
			EntityItem item = new EntityItem (this.worldObj, this.xCoord, (this.yCoord + 1), this.zCoord, result);

			// set velocity
			item.addVelocity (0, 0.25f, 0);

			// drop in world
			this.worldObj.spawnEntityInWorld (item);

			// delete item
			this.item = null;

			// mark update
			this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
			this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToNBT (NBTTagCompound p_145841_1_) {
		super.writeToNBT (p_145841_1_);

		if (this.item != null) p_145841_1_.setString ("item", Item.itemRegistry.getNameForObject (this.item));
		p_145841_1_.setInteger ("processing", this.processing);
	}
}