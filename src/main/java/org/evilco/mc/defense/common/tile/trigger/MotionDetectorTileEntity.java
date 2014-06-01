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
package org.evilco.mc.defense.common.tile.trigger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkAuthority;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkEntity;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkConnectionExistsException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnknownConnectionException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnsupportedEntityException;
import org.evilco.mc.defense.common.tile.AbstractTileEntity;
import org.evilco.mc.defense.common.tile.IRotateableTileEntity;
import org.evilco.mc.defense.common.util.Location;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MotionDetectorTileEntity extends AbstractTileEntity implements IRotateableTileEntity, ISurveillanceNetworkEntity {

	/**
	 * Defines the aperture size.
	 */
	public static final double APERTURE = (1.2 / 2.0);

	/**
	 * Stores the maximum detection range.
	 */
	public static final int RANGE = 12;

	/**
	 * Stores the lens quality.
	 */
	protected int lensQuality = 0;

	/**
	 * Stores the authority location.
	 */
	protected Location authorityLocation = null;

	/**
	 * Stores the authority instance.
	 */
	protected ISurveillanceNetworkAuthority authority = null;

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
	public boolean canModifyConnection (EntityPlayer player) {
		return (this.authorityLocation == null ? true : (this.authority == null ? false : this.authority.canModifyConnection (player)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException {
		// disallow new connections
		if (this.authorityLocation != null) throw new SurveillanceNetworkConnectionExistsException ();

		// disallow non-authority
		if (!(entity instanceof ISurveillanceNetworkAuthority)) throw new SurveillanceNetworkUnsupportedEntityException ();

		// stop writing
		if (simulate) return;

		// store location
		this.authorityLocation = entity.getLocation ();
		this.authority = ((ISurveillanceNetworkAuthority) entity);

		// notify other side
		if (notifyPeer) entity.connect (this, false, false);

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
		if (notifyPeer && this.authority != null) this.authority.disconnect (this, false, false);

		// delete data
		this.authorityLocation = null;
		this.authority = null;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
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
		// create tag compound
		NBTTagCompound compound = new NBTTagCompound ();

		// write data
		this.writeToNBT (compound);

		// return finished packet
		return (new S35PacketUpdateTileEntity (this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata (), compound));
	}

	/**
	 * Returns the detection radius.
	 * @return The detection radius.
	 */
	public AxisAlignedBB getDetectionBounds () {
		return AxisAlignedBB.getAABBPool ().getAABB ((this.xCoord - RANGE), (this.yCoord - 1), (this.zCoord - RANGE), (this.xCoord + RANGE), (this.yCoord + 1), (this.zCoord + RANGE));
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
	public void initialize () {
		super.initialize ();

		// try to connect
		if (this.authorityLocation != null) {
			// find TileEntity
			TileEntity tileEntity = this.authorityLocation.getTileEntity (this.worldObj);

			// verify entity
			if (tileEntity != null) return;

			// check for invalid TileEntities
			if (!(tileEntity instanceof ISurveillanceNetworkAuthority)) {
				// reset tile entity
				this.authorityLocation = null;
				this.authority = null;

				// mark update
				this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
				this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);

				// cancel further processing
				return;
			}

			// store instance
			this.authority = ((ISurveillanceNetworkAuthority) tileEntity);

			// notify entity
			try {
				this.authority.notifyConnection (this);
			} catch (SurveillanceNetworkException ex) {
				// try disconnecting properly
				try {
					this.disconnect (this.authority, false, true);
				} catch (SurveillanceNetworkException e) {
					// reset tile entity
					this.authorityLocation = null;
					this.authority = null;

					// mark update
					this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
					this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
				}
			}
		}
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
		return (entity.getLocation () != null ? entity.getLocation ().equals (this.authorityLocation) : entity.equals (this.authority));
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
		// verify data
		if (!(entity instanceof ISurveillanceNetworkAuthority)) throw new SurveillanceNetworkUnsupportedEntityException ();
		if (this.authorityLocation == null) throw new SurveillanceNetworkUnknownConnectionException ();

		// store authority instance
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
	public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT (pkt.func_148857_g ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readFromNBT (NBTTagCompound p_145839_1_) {
		super.readFromNBT (p_145839_1_);

		this.lensQuality = p_145839_1_.getInteger ("lensQuality");
		if (p_145839_1_.hasKey ("authority")) this.authorityLocation = Location.readFromNBT (p_145839_1_.getCompoundTag ("authority"));
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLensQuality (int quality) {
		this.lensQuality = quality;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity () {
		super.updateEntity ();

		// skip client side execution
		if (this.worldObj.isRemote) return;

		// skip if not connected
		if (this.authority == null) return;

		// scan for living entites
		List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB (Entity.class, this.getDetectionBounds ());

		// skip if no data is detected
		if (entities.isEmpty ()) return;

		// calculate constant vectors
		Vec3 coneApex = this.getConeApex ();
		Vec3 coneBase = this.getConeBase ();

		Vec3 coneAxis = this.worldObj.getWorldVec3Pool ().getVecFromPool ((coneBase.xCoord - coneApex.xCoord), (coneBase.yCoord - coneApex.yCoord), (coneBase.zCoord - coneApex.zCoord));

		// notify authority
		for (Entity entity : entities) {
			// skip non living
			if (!(entity instanceof EntityLivingBase) && !(entity instanceof EntityPlayer)) continue;

			// check for people in creative
			if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) continue;

			// skil already known intruders
			if (this.authority.isKnownIntruder (entity)) continue;

			// get axis
			Vec3 originTargetAxis = this.worldObj.getWorldVec3Pool ().getVecFromPool ((entity.posX - coneApex.xCoord), (entity.posY - coneApex.yCoord), (entity.posZ - coneApex.zCoord));

			// check whether entity is in cone
			if (!this.isInsideSphericalCone (coneAxis, originTargetAxis, APERTURE)) continue;

			// get random
			double random = Math.random ();
			double detectionChance = (this.lensQuality / 100);

			// skip by chance
			if (random > detectionChance) continue;

			// notify authority
			this.authority.notifyIntruder (this, entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToNBT (NBTTagCompound p_145841_1_) {
		super.writeToNBT (p_145841_1_);

		p_145841_1_.setInteger ("lensQuality", this.lensQuality);

		if (this.authorityLocation != null) {
			NBTTagCompound compound = new NBTTagCompound ();
			this.authorityLocation.writeToNBT (compound);
			p_145841_1_.setTag ("authority", compound);
		}
	}
}