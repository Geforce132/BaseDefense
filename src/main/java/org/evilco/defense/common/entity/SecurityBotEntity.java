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
package org.evilco.defense.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.evilco.defense.common.entity.ai.EntityAISecurityBot;
import org.evilco.defense.common.tile.network.*;
import org.evilco.defense.util.Location;

import java.util.UUID;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SecurityBotEntity extends EntityCreature implements ISurveillanceNetworkClient {

	/**
	 * Defines the maximum gun distance.
	 */
	public static final float MAXIMUM_GUN_DISTANCE = 15.0f;

	/**
	 * Defines the entity movement speed.
	 */
	public static final double MOVEMENT_SPEED = 1.2d;

	/**
	 * Stores the security bot AI.
	 */
	protected EntityAISecurityBot securityBotAI = null;

	/**
	 * Stores the hub location (if connected).
	 */
	protected Location hubLocation = null;

	/**
	 * Defines whether the entity is connected.
	 */
	protected boolean isConnected = false;

	/**
	 * Stores the hub (if connected).
	 */
	protected ISurveillanceNetworkHub hub = null;

	/**
	 * Stores the entity owner.
	 */
	protected UUID owner = null;

	/**
	 * Constructs a new SecurityBotEntity.
	 * @param par1World The world to spawn the entity in.
	 */
	public SecurityBotEntity (World par1World) {
		super (par1World);

		// create task
		this.securityBotAI = new EntityAISecurityBot (this);

		// add tasks
		this.tasks.addTask (1, this.securityBotAI);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void applyEntityAttributes () {
		super.applyEntityAttributes ();

		this.getEntityAttribute (SharedMonsterAttributes.maxHealth).setBaseValue (200.0d);
		this.getEntityAttribute (SharedMonsterAttributes.followRange).setBaseValue (32.0d);
		this.getEntityAttribute (SharedMonsterAttributes.knockbackResistance).setBaseValue (20.0d); // insanely high value ... no knockback whatsoever
		this.getEntityAttribute (SharedMonsterAttributes.movementSpeed).setBaseValue (0.25d);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectHub (ISurveillanceNetworkHub hub) throws SurveillanceEntityConnectionException {
		// disconnect from previous hub
		if (this.hub != null) this.hub.disconnectEntity (this);

		// store new hub
		this.hub = hub;
		this.hubLocation = hub.getLocation ();

		// fire connect event
		this.hub.connectEntity (this);

		this.isConnected = true;

		// notify about entity change
		this.worldObj.updateEntity (this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnectHub () {
		// disconnect from previous hub
		if (this.hub != null) this.hub.disconnectEntity (this);

		// delete data
		this.hub = null;
		this.hubLocation = null;

		this.isConnected = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AxisAlignedBB getBoundingBox () {
		return AxisAlignedBB.getBoundingBox (-0.05f, 0.00f, -0.05f, 1.05f, 0.5f, 1.05f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Location getLocation () {
		return (new Location (this.posX, this.posY, this.posZ));
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
	protected boolean isAIEnabled () {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActive () {
		return (this.isEntityAlive ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDeath (DamageSource par1DamageSource) {
		super.onDeath (par1DamageSource);

		// TODO: Drop bot as item.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readEntityFromNBT (NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT (par1NBTTagCompound);

		// read connection state
		this.isConnected = par1NBTTagCompound.getBoolean ("connected");

		if (par1NBTTagCompound.hasKey ("owner"))
			this.owner = UUID.fromString (par1NBTTagCompound.getString ("owner"));
		else
			this.owner = null;

		// read hub location
		if (par1NBTTagCompound.hasKey ("hub"))
			this.hubLocation = Location.readFromNBT (par1NBTTagCompound.getCompoundTag ("hub"));
		else
			this.hubLocation = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveMessage (ISurveillanceNetworkPacket packet) {
		// defense orders
		if (packet instanceof DefenseOrderPacket && this.getAttackTarget () == null && this.getNavigator ().noPath ()) {
			// cast packet
			DefenseOrderPacket defenseOrderPacket = ((DefenseOrderPacket) packet);

			// set navigator target
			this.getNavigator ().tryMoveToXYZ (defenseOrderPacket.getLocation ().xCoord, defenseOrderPacket.getLocation ().yCoord, defenseOrderPacket.getLocation ().zCoord, MOVEMENT_SPEED);

			// stop execution
			return;
		}

		// attack orders
		if (packet instanceof AttackOrderPacket) {
			// cast packet
			AttackOrderPacket attackOrderPacket = ((AttackOrderPacket) packet);

			// set target
			this.setAttackTarget (attackOrderPacket.getTarget ());

			// notify ai
			this.securityBotAI.waitingForResponse = false;
		}
	}

	@Override
	public void onUpdate () {
		super.onUpdate ();

		// try connecting to the
		if (this.hub == null && this.hubLocation != null) {
			// try to find the hub entity
			TileEntity tileEntity = this.hubLocation.getTileEntity (this.worldObj);

			// verify entity type
			if (!(tileEntity instanceof ISurveillanceNetworkHub)) {
				// disconnect from hub
				this.disconnectHub ();

				// stop execution
				return;
			}

			// store hub
			this.hub = ((ISurveillanceNetworkHub) tileEntity);
			this.isConnected = true;

			// notify hub
			try {
				this.hub.connectEntity (this);
			} catch (SurveillanceEntityConnectionException ex) {
				// disconnect from hub
				this.disconnectHub ();

				// stop execution
				return;
			}
		}
	}

	/**
	 * Tries to send a packet to the hub.
	 * @param packet The packet to send.
	 */
	public void sendPacket (ISurveillanceNetworkPacket packet) {
		if (this.hub != null) this.hub.receiveMessage (packet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeEntityToNBT (NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT (par1NBTTagCompound);

		// write connection state
		par1NBTTagCompound.setBoolean ("connected", this.isConnected);
		if (this.owner != null) par1NBTTagCompound.setString ("owner", this.owner.toString ());

		// write hub location to NBT
		if (this.hubLocation != null) {
			NBTTagCompound compound = new NBTTagCompound ();
			this.hubLocation.writeToNBT (compound);
			par1NBTTagCompound.setTag ("hub", compound);
		}
	}
}