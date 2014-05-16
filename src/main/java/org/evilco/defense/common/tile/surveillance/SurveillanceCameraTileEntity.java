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
package org.evilco.defense.common.tile.surveillance;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkClient;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkHub;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkPacket;
import org.evilco.defense.common.tile.network.SurveillanceEntityConnectionException;
import org.evilco.defense.util.Location;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SurveillanceCameraTileEntity extends TileEntity implements ISurveillanceNetworkClient {

	/**
	 * Defines the maximum camera angle.
	 */
	public static final float MAX_ANGLE = 0.75f;

	/**
	 * Defines the rotation speed.
	 */
	public static final float ROTATION_SPEED = 0.005f;

	/**
	 * Stores the current camera angle.
	 */
	@SideOnly (Side.CLIENT)
	protected float angle = 0.0f;

	/**
	 * Indicates whether the camera is currently active.
	 */
	protected boolean isActive = false;

	/**
	 * Stores the parent hub instance.
	 */
	protected ISurveillanceNetworkHub hub = null;

	/**
	 * Sotres the parent hub location.
	 */
	protected Location hubLocation = null;

	/**
	 * Indicates whether the motion is reversed.
	 */
	@SideOnly (Side.CLIENT)
	protected boolean isMotionReversed = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity () {
		// initialize hub
		if (this.hub == null && this.hubLocation != null) {
			// find entity
			TileEntity tileEntity = this.hubLocation.getTileEntity (this.worldObj);

			// verify entity
			if (!(tileEntity instanceof ISurveillanceNetworkHub)) {
				// something went terribly wrong here!
				this.hubLocation = null;
				this.isActive = false;

				// update block
				this.worldObj.notifyBlockChange (this.xCoord, this.yCoord, this.zCoord, this.blockType);

				// stop here
				return;
			}

			// store entity
			this.hub = ((ISurveillanceNetworkHub) tileEntity);
			this.isActive = true;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectHub (ISurveillanceNetworkHub hub) throws SurveillanceEntityConnectionException {
		// notify old hub
		if (this.hub != null) this.hub.disconnectEntity (this);

		// store position
		this.hub = hub;
		this.hubLocation = hub.getLocation ();

		// notify hub
		hub.connectEntity (this);

		// update block
		this.worldObj.notifyBlockChange (this.xCoord, this.yCoord, this.zCoord, this.blockType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnectHub () {
		// notify hub
		if (this.hub != null) this.hub.disconnectEntity (this);

		// store new data
		this.hubLocation = null;
		this.hub = null;

		// update block
		this.worldObj.notifyBlockChange (this.xCoord, this.yCoord, this.zCoord, this.blockType);
	}

	/**
	 * Returns the current camera angle.
	 * @param partialTicks The ticks that passed between renders.
	 * @return The current camera angle.
	 */
	@SideOnly (Side.CLIENT)
	public float getCameraAngle (float partialTicks) {
		// de-activated camera
		if (!this.isActive) return this.angle;

		// modify angle
		this.angle += ((partialTicks * ROTATION_SPEED) * (this.isMotionReversed ? -1 : 1));

		// cap
		if (this.angle > MAX_ANGLE) this.angle = MAX_ANGLE;
		if (this.angle < (MAX_ANGLE * -1)) this.angle = (MAX_ANGLE * -1);

		// reverse motion
		if (this.angle >= MAX_ANGLE || this.angle <= (MAX_ANGLE * -1)) this.isMotionReversed = !this.isMotionReversed;

		// return finished angle
		return this.angle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Location getLocation () {
		return (new Location (this.xCoord, this.yCoord, this.zCoord));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActive () {
		return this.isActive;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToNBT (NBTTagCompound p_145841_1_) {
		super.writeToNBT (p_145841_1_);

		// store camera state
		p_145841_1_.setBoolean ("active", this.isActive);

		// store camera controller location
		if (this.hubLocation != null) {
			NBTTagCompound location = new NBTTagCompound ();
			location.setDouble ("x", this.hubLocation.xCoord);
			location.setDouble ("y", this.hubLocation.yCoord);
			location.setDouble ("z", this.hubLocation.zCoord);

			p_145841_1_.setTag ("hubLocation", location);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readFromNBT (NBTTagCompound p_145839_1_) {
		super.readFromNBT (p_145839_1_);

		// get camera state
		this.isActive = p_145839_1_.getBoolean ("active");

		// get controller location
		if (p_145839_1_.hasKey ("hubLocation")) {
			// get tag compound
			NBTTagCompound location = p_145839_1_.getCompoundTag ("location");

			// get location
			this.hubLocation = new Location (location.getDouble ("x"), location.getDouble ("y"), location.getDouble ("z"));
		} else
			this.hubLocation = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveMessage (ISurveillanceNetworkPacket packet) {
		// TODO: Enable/Disable
	}

	@Override
	public void onChunkUnload () {
		super.onChunkUnload ();

		// disable camera
		this.isActive = false;
	}
}