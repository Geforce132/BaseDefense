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
package org.evilco.mc.defense.common.tile.sensor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.evilco.mc.defense.api.network.identification.DetectedEntity;
import org.evilco.mc.defense.api.network.identification.EntityIdentity;
import org.evilco.mc.defense.api.network.identification.EntityType;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkAuthority;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkEntity;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkSensor;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkConnectionExistsException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnsupportedEntityException;
import org.evilco.mc.defense.common.tile.IRotateableTileEntity;
import org.evilco.mc.defense.common.util.Location;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CameraTileEntity extends TileEntity implements IRotateableTileEntity, ISurveillanceNetworkSensor {

	/**
	 * Defines the aperture size.
	 */
	public static final double APERTURE = (1.2 / 2.0);

	/**
	 * Defines the maximum angle.
	 */
	public static final float MAX_ANGLE = 45.0f;

	/**
	 * Defines the maximum camera detection range.
	 */
	public static final int RANGE = 15;

	/**
	 * Defines the rotation speed.
	 */
	public static final float ROTATION_SPEED = 1.25f;

	/**
	 * Indicates whether the sensor is active.
	 */
	protected boolean active = false;

	/**
	 * Stores the authority instance.
	 */
	protected ISurveillanceNetworkAuthority authority = null;

	/**
	 * Stores the authority location.
	 */
	protected Location authorityLocation = null;

	/**
	 * Stores the camera angle.
	 */
	protected float cameraAngle = 0.0f;

	/**
	 * Indicates whether the rotation is reversed.
	 */
	protected boolean isRotationReversed = false;

	/**
	 * Defines the lens quality.
	 */
	protected float lensQuality = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canConnect (ISurveillanceNetworkEntity entity) {
		return (entity instanceof ISurveillanceNetworkAuthority);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canDeclareType (Entity entity, Location lastKnownLocation) {
		return this.getDetectionBounds ().isVecInside (lastKnownLocation.toVector (entity.worldObj));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canIdentify (Entity entity, Location lastKnownLocation) {
		return this.getDetectionBounds ().isVecInside (lastKnownLocation.toVector (entity.worldObj));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canModifyConnection (EntityPlayer player) {
		return (this.authority != null ? this.authority.canModifyConnection (player) : (this.authorityLocation == null ? true : false));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException {
		// disallow new connections
		if (this.authorityLocation != null) new SurveillanceNetworkConnectionExistsException ();

		// disallow non-authority
		if (!(entity instanceof ISurveillanceNetworkAuthority)) throw new SurveillanceNetworkUnsupportedEntityException ();

		// skip writing
		if (simulate) return;

		// store
		this.authorityLocation = entity.getLocation ();
		this.authority = ((ISurveillanceNetworkAuthority) entity);

		// notify other side
		if (notifyPeer) this.authority.connect (this, false, false);

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException {
		// stop writing
		if (simulate) return;

		// notify other side
		if (notifyPeer) entity.disconnect (this, false, false);

		// delete data
		this.authorityLocation = null;
		this.authority = null;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getActive () {
		return this.active;
	}

	/**
	 * Returns the current camera angle.
	 * @param partialTicks The amount of ticks passed since last rendering.
	 * @return The current angle.
	 */
	public double getCameraAngle (float partialTicks) {
		// modify angle
		if (this.getActive ()) this.cameraAngle += Math.max ((MAX_ANGLE * (this.isRotationReversed ? -1 : 1)), (this.cameraAngle + (ROTATION_SPEED * (this.isRotationReversed ? -1 : 1))));

		// return radians
		return Math.toRadians (this.cameraAngle);
	}

	/**
	 * Returns the cone apex.
	 * @return The cone apex vector.
	 */
	public Vec3 getConeApex () {
		// get angle
		double angle = Math.toRadians ((this.getRotationAngle () + 90.0f));

		// build vector
		return this.worldObj.getWorldVec3Pool ().getVecFromPool ((this.xCoord - (Math.cos (angle) * 1.1)), (this.yCoord + 0.5), (this.zCoord - (Math.sin (angle) * 1.1)));
	}

	/**
	 * Returns the cone base.
	 * @return The cone base vector.
	 */
	public Vec3 getConeBase () {
		// get angle
		double angle = Math.toRadians ((this.getRotationAngle () + 90.0f));

		// build vector
		return this.worldObj.getWorldVec3Pool ().getVecFromPool ((this.xCoord + (Math.cos (angle) * RANGE)), (this.yCoord + 0.5), (this.zCoord + (Math.sin (angle) * 1.1)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Packet getDescriptionPacket () {
		// create NBT tag compound
		NBTTagCompound compound = new NBTTagCompound ();

		// write data
		this.writeToNBT (compound);

		// create packet
		return (new S35PacketUpdateTileEntity (this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata (), compound));
	}

	/**
	 * Returns the detection bounds.
	 * @return The detection hitbox.
	 */
	public AxisAlignedBB getDetectionBounds () {
		return AxisAlignedBB.getAABBPool ().getAABB ((this.xCoord - RANGE), (this.yCoord - RANGE), (this.zCoord - RANGE), (this.xCoord + RANGE), (this.yCoord + 1), (this.zCoord + RANGE));
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
	public float getRotationAngle () {
		return (this.getBlockMetadata () * 90.0f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected () {
		return (this.authorityLocation != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected (ISurveillanceNetworkEntity entity) {
		return (entity.equals (this.authority));
	}

	/**
	 * Checks whether a vector is inside the cone bounds.
	 * @param coneAxis The cone axis.
	 * @param originTargetAxis The axis between origin and target.
	 * @param aperture The aperture.
	 * @return True if the target is inside of the sphere.
	 */
	public boolean isInsideSphericalCone (Vec3 coneAxis, Vec3 originTargetAxis, double aperture) {
		return ((originTargetAxis.dotProduct (coneAxis) / originTargetAxis.lengthVector () / coneAxis.lengthVector ()) > Math.cos (aperture));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyConnection (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException {
		this.authority = ((ISurveillanceNetworkAuthority) entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyInvalidation (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException {
		this.authority = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onChunkUnload () {
		super.onChunkUnload ();

		// disconnect
		if (this.authority != null) {
			try {
				this.authority.notifyInvalidation (this);
			} catch (SurveillanceNetworkException ex) {
				try {
					this.disconnect (this.authority, false, false);
				} catch (SurveillanceNetworkException e) {
					this.authorityLocation = null;
					this.authority = null;
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT (pkt.func_148857_g ());
	}

	/**
	 * Processes an identification or type request.
	 */
	protected void process (boolean mode) {
		// find all entities in range
		List<Entity> entityList = this.worldObj.getEntitiesWithinAABB (Entity.class, this.getDetectionBounds ());

		// skip if no data is found
		if (entityList.isEmpty ()) return;

		// calculate cone vectors
		Vec3 coneApex = this.getConeApex ();
		Vec3 coneBase = this.getConeBase ();

		Vec3 coneAxis = this.worldObj.getWorldVec3Pool ().getVecFromPool ((coneBase.xCoord - coneApex.xCoord), (coneBase.yCoord - coneApex.yCoord), (coneBase.zCoord - coneApex.zCoord));

		// iterate over all entities
		for (Entity entity : entityList) {
			// skip non-living/non-player entities
			if (!(entity instanceof EntityLivingBase) && !(entity instanceof EntityPlayer)) continue;

			// ignore creative players
			if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) continue;

			// calculate origin -> target axis
			Vec3 originTargetAxis = this.worldObj.getWorldVec3Pool ().getVecFromPool ((entity.posX - coneApex.xCoord), (entity.posY - coneApex.yCoord), (entity.posZ - coneApex.zCoord));

			// check whether entity is inside of cone
			if (!this.isInsideSphericalCone (coneAxis, originTargetAxis, APERTURE)) continue;

			// get random
			double random = Math.random ();
			float chance = (this.lensQuality / 100);

			// check chance
			if (random > chance) continue;

			// identify entity
			if (this.authority.isKnownIntruder (entity)) {
				// get detected entity
				DetectedEntity detectedEntity = this.authority.getDetectedEntity (entity);

				// identify
				if (mode) {
					// set identity
					detectedEntity.setIdentity (new EntityIdentity (entity.getPersistentID (), (entity instanceof EntityPlayer ? ((EntityPlayer) entity).getDisplayName () : null)));

					// update known location
					detectedEntity.setLastKnownLocation (new Location (entity.posX, entity.posY, entity.posZ));
				} else
					detectedEntity.setType ((entity instanceof EntityPlayer ? EntityType.PLAYER : (entity instanceof EntityMob ? EntityType.HOSTILE : (entity instanceof EntityAnimal ? EntityType.FRIENDLY : EntityType.UNKNOWN))));
			} else
				// notify about intruder
				this.authority.notifyIntruder (this, entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readFromNBT (NBTTagCompound p_145839_1_) {
		super.readFromNBT (p_145839_1_);

		// read simple variables
		this.active = p_145839_1_.getBoolean ("active");
		this.lensQuality = p_145839_1_.getFloat ("lensQuality");

		// read authority
		if (p_145839_1_.hasKey ("authority")) this.authorityLocation = Location.readFromNBT (p_145839_1_.getCompoundTag ("authority"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActive (boolean b) {
		this.active = b;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * Sets a new lens quality.
	 * @param lensQuality The lens quality.
	 */
	public void setLensQuality (float lensQuality) {
		this.lensQuality = lensQuality;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tryIdentify () {
		this.process (true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tryDeclareType () {
		this.process (false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToNBT (NBTTagCompound p_145841_1_) {
		super.writeToNBT (p_145841_1_);

		// write simple properties
		p_145841_1_.setBoolean ("active", this.active);
		p_145841_1_.setFloat ("lensQuality", this.lensQuality);

		// write authority information
		if (this.authorityLocation != null) {
			// create tag compound
			NBTTagCompound compound = new NBTTagCompound ();

			// write location
			this.authorityLocation.writeToNBT (compound);

			// store data
			p_145841_1_.setTag ("authority", compound);
		}
	}
}