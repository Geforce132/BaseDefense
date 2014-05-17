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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.evilco.defense.common.tile.network.*;
import org.evilco.defense.util.Location;

import java.util.List;
import java.util.UUID;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SurveillanceCameraTileEntity extends TileEntity implements ISurveillanceNetworkClient {

	/**
	 * Defines the camera detection radius.
	 */
	public static final float CAMERA_RANGE = 10.0f;

	/**
	 * Defines the cone aperture.
	 */
	public static final double CONE_HALF_APERTURE = (1.2 / 2.0);

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
	 * Indicates whether the camera is scanning for mobs.
	 */
	protected boolean isScanningMobs = false;

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
	 * Stores the entity owner.
	 */
	protected UUID owner = null;

	/**
	 * Defines an amount of ticks to the next report.
	 */
	protected int reportTicks = 0;

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
				this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
				this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);

				// stop here
				return;
			}

			// store entity
			this.hub = ((ISurveillanceNetworkHub) tileEntity);
			this.isActive = true;

			// update block
			this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
			this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
		}

		// execute surveillance features
		if (this.hub != null && this.isActive) {
			this.reportTicks = Math.max ((this.reportTicks - 1), 0);

			// only execute if reporting is available again.
			if (this.reportTicks == 0) {
				// find entities in detection radius
				List<Entity> entityList = worldObj.getEntitiesWithinAABB ((this.isScanningMobs ? EntityCreature.class : EntityPlayer.class), this.getCameraDetectionBounds ());

				// notify hub
				if (entityList.size () > 0) this.hub.receiveMessage (new CameraDetectionPacket (this, entityList));

				// reset report period
				this.reportTicks = 300;
			}
		}

		// invalidate hub object if it goes down
		if (this.hub != null && !this.hub.isActive ()) {
			this.hub = null;
			this.isActive = false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectHub (ISurveillanceNetworkHub hub) throws SurveillanceEntityConnectionException {
		// verify hub
		if (hub.getOwner () == null || !hub.getOwner ().equals (this.getOwner ())) throw new SurveillanceEntityConnectionException ("defense.surveillance.tuner.differentOwner");

		// notify old hub
		if (this.hub != null) this.hub.disconnectEntity (this);

		// store position
		this.hub = hub;
		this.hubLocation = hub.getLocation ();

		// notify hub
		hub.connectEntity (this);

		// activate block
		this.isActive = true;

		// update block
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
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
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * Returns the camera rotation angle (in 90 degree steps).
	 * @return The rotation.
	 */
	public float getRotationAngle () {
		switch (this.getBlockMetadata ()) {
			case 0: return 180.0f;
			case 1: return 0.0f;
			case 2: return 90.0f;
			case 3: return -90.0f;
			default: return -45.0f; // confuse some people doing it wrong
		}
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
	 * Returns the camera detection radius.
	 * @return The detection radius.
	 */
	public AxisAlignedBB getCameraDetectionBounds () {
		switch (this.getBlockMetadata ()) {
			case 3: return AxisAlignedBB.getBoundingBox (this.xCoord, (this.yCoord - CAMERA_RANGE), (this.zCoord - CAMERA_RANGE), (this.xCoord + CAMERA_RANGE), this.yCoord, (this.zCoord + CAMERA_RANGE));
			case 1: return AxisAlignedBB.getBoundingBox ((this.xCoord - CAMERA_RANGE), (this.yCoord - CAMERA_RANGE), this.zCoord, (this.xCoord + CAMERA_RANGE), this.yCoord, (this.zCoord + CAMERA_RANGE));
			case 2: return AxisAlignedBB.getBoundingBox ((this.xCoord - CAMERA_RANGE), (this.yCoord - CAMERA_RANGE), (this.zCoord - CAMERA_RANGE), this.xCoord, this.yCoord, (this.zCoord + CAMERA_RANGE));
			case 0: return AxisAlignedBB.getBoundingBox ((this.xCoord - CAMERA_RANGE), (this.yCoord - CAMERA_RANGE), (this.zCoord - CAMERA_RANGE), (this.xCoord + CAMERA_RANGE), this.yCoord, this.zCoord);
			default: return null; // Let's cause some NPEs
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Packet getDescriptionPacket () {
		// serialize tile entity
		NBTTagCompound nbtTagCompound = new NBTTagCompound ();
		this.writeToNBT (nbtTagCompound);

		// construct packet
		return (new S35PacketUpdateTileEntity (this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound));
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
	public UUID getOwner () {
		return this.owner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActive () {
		return this.isActive;
	}

	/**
	 * Checks whether an entity lies within the scanning range.
	 * @param coneAxis
	 * @param origin
	 * @param halfAperture
	 * @return
	 */
	public boolean isWithinCameraBounds (Vec3 coneAxis, Vec3 origin, double halfAperture) { // Do some crazy maths. Google it ... srsly
		return ((origin.dotProduct (coneAxis) / origin.lengthVector () / coneAxis.lengthVector ()) > Math.cos (halfAperture));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT (pkt.func_148857_g ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOwner (UUID owner) {
		this.owner = owner;

		// update tile entity
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOwner (EntityPlayer player) {
		this.setOwner (player.getPersistentID ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToNBT (NBTTagCompound p_145841_1_) {
		super.writeToNBT (p_145841_1_);

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

		// get controller location
		if (p_145839_1_.hasKey ("hubLocation")) {
			// get tag compound
			NBTTagCompound location = p_145839_1_.getCompoundTag ("hubLocation");

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onChunkUnload () {
		super.onChunkUnload ();

		// disable camera
		this.isActive = false;

		// disconnect from hub
		if (this.hub != null) this.hub.disconnectEntity (this);
	}
}