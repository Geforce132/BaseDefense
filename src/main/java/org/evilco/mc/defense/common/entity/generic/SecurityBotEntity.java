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
package org.evilco.mc.defense.common.entity.generic;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.evilco.mc.defense.api.network.identification.DetectedEntity;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkAuthority;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkEntity;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkRespondingEntity;
import org.evilco.mc.defense.api.network.surveillance.SurveillanceNetworkAlarmState;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkConnectionExistsException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnknownConnectionException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnsupportedEntityException;
import org.evilco.mc.defense.common.entity.DefenseDamageSource;
import org.evilco.mc.defense.common.item.DefenseItem;
import org.evilco.mc.defense.common.util.Location;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SecurityBotEntity extends EntityCreature implements IRangedAttackMob, ISurveillanceNetworkRespondingEntity {

	/**
	 * Defines the entity movement speed.
	 */
	public static final double MOVEMENT_SPEED = 1.2;

	/**
	 * Defines whether the entity AI is active.
	 */
	protected boolean aiActive = false;

	/**
	 * Indicates whether the bot already received an attack request.
	 */
	protected boolean attackRequestReceived = false;

	/**
	 * Stores the network authority instance.
	 */
	protected ISurveillanceNetworkAuthority authority = null;

	/**
	 * Stores the network authority location.
	 */
	protected Location authorityLocation = null;

	/**
	 * Stores the original location.
	 */
	protected Location originalLocation = null;

	/**
	 * Indicates whether a return to the original location has been requested.
	 */
	protected boolean returnToOriginRequested = false;

	/**
	 * Constructs a new SecurityBotEntity instance.
	 * @param par1World The world to spawn the entity in.
	 */
	public SecurityBotEntity (World par1World) {
		super (par1World);

		// add tasks
		this.tasks.addTask (1, new EntityAILookIdle (this));
		this.tasks.addTask (2, new EntityAIWander (this, 1.0));
	}

	/**
	 * Constructs a new SecurityBotEntity instance.
	 * @param par1World The world to spawn the entity in.
	 */
	public SecurityBotEntity (World par1World, Location location) {
		this (par1World);

		// store location
		this.originalLocation = location;

		// set position
		this.setLocationAndAngles (location.xCoord, location.yCoord, location.zCoord, 0.00f, 0.00f);
	}

	/**
	 * Constructs a new SecurityBotEntity instance.
	 * @param par1World The world to spawn the entity in.
	 * @param x The x position to spawn the entity at.
	 * @param y The y position to spawn the entity at.
	 * @param z The z position to spawn the entity at.
	 */
	public SecurityBotEntity (World par1World, double x, double y, double z) {
		this (par1World, new Location (x, y, z));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean allowLeashing () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void attackEntityWithRangedAttack (EntityLivingBase var1, float var2) {
		var1.attackEntityFrom (DefenseDamageSource.GENERIC_SECURITY_BOT, var2);
	}

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
	public boolean canBePushed () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean canDespawn () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canModifyConnection (EntityPlayer player) {
		return (this.authority != null ? this.authority.canModifyConnection (player) : (this.authorityLocation != null ? false : true));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canPickUpLoot () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException {
		// verify
		if (this.authorityLocation != null) throw new SurveillanceNetworkConnectionExistsException ();
		if (!(entity instanceof ISurveillanceNetworkAuthority)) throw new SurveillanceNetworkUnsupportedEntityException ();

		// stop writing
		if (simulate) return;

		// store data
		this.authorityLocation = entity.getLocation ();
		this.authority = null;

		// mark entity for update
		this.worldObj.updateEntity (this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException {
		// verify
		if (this.authorityLocation == null) throw new SurveillanceNetworkUnknownConnectionException ();

		// stop writing
		if (simulate) return;

		// reset data
		this.forceDisconnect (entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void entityInit () {
		super.entityInit ();

		this.dataWatcher.addObject (13, ((byte) 0));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forceDisconnect (ISurveillanceNetworkEntity entity) {
		// reset data
		this.authorityLocation = null;
		this.authority = null;

		// mark update
		this.worldObj.updateEntity (this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Location getLocation () {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isAIEnabled () {
		return this.aiActive;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isBroken () {
		return (this.dataWatcher.getWatchableObjectByte (13) > 0);
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNoDespawnRequired () {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyAlarm (ISurveillanceNetworkAuthority authority, SurveillanceNetworkAlarmState alarmState, DetectedEntity entity) {
		// reset back to normal
		if (alarmState == SurveillanceNetworkAlarmState.GREEN) {
			// reset AI
			this.aiActive = false;
			this.attackRequestReceived = false;

			// request return to origin
			this.returnToOriginRequested = true;
		}

		// set pathfinding target
		if (alarmState == SurveillanceNetworkAlarmState.RED && this.getAttackTarget () == null && !this.attackRequestReceived) {
			// request move to alarm location
			this.getNavigator ().tryMoveToXYZ (entity.getLastKnownLocation ().xCoord, entity.getLastKnownLocation ().yCoord, entity.getLastKnownLocation ().zCoord, MOVEMENT_SPEED);

			// enable AI
			this.aiActive = true;
			this.attackRequestReceived = true;
		}
	}

	@Override
	public void notifyConnection (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException {

	}

	@Override
	public void notifyInvalidation (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStruckByLightning (EntityLightningBolt par1EntityLightningBolt) {
		// spawn explosion particle
		this.spawnExplosionParticle ();

		// kill entity
		this.attackEntityFrom (DamageSource.generic, this.getMaxHealth ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUpdate () {
		super.onUpdate ();

		// check whether bot is in water
		if (this.isInWater ()) {
			// spawn explosion particle
			this.spawnExplosionParticle ();

			// kill entity
			if (!this.worldObj.isRemote) this.attackEntityFrom (DamageSource.generic, this.getMaxHealth ());

			// skip further execution
			return;
		}

		// broken entity
		if (this.isBroken ()) this.worldObj.spawnParticle ("largesmoke", (this.posX), (this.posY + 0.8f), (this.posZ), 0, 0, 0);

		// check connection
		if (this.authorityLocation != null && this.authority == null) {
			// search tile entity
			TileEntity tileEntity = this.authorityLocation.getTileEntity (this.worldObj);

			// process
			if (tileEntity != null) {
				// check type
				if (!(tileEntity instanceof ISurveillanceNetworkAuthority))
					// forcefully disconnect
					this.forceDisconnect (null);
				else {
					// cast
					this.authority = ((ISurveillanceNetworkAuthority) tileEntity);

					// notify connection
					try {
						this.authority.notifyConnection (this);
					} catch (SurveillanceNetworkException ex) {
						try {
							// gracefully disconnect
							this.disconnect (this.authority, false, true);
						} catch (SurveillanceNetworkException e) {
							// force disconnect
							this.forceDisconnect (this.authority);
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readEntityFromNBT (NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT (par1NBTTagCompound);

		this.setBroken (par1NBTTagCompound.getBoolean ("broken"));
	}

	/**
	 * Sets whether the security bot is broken.
	 * @param b True if the entity is broken.
	 */
	public void setBroken (boolean b) {
		// update
		this.dataWatcher.updateObject (13, ((byte) (b ? 1 : 0)));

		// mark for update
		this.worldObj.updateEntity (this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeEntityToNBT (NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT (par1NBTTagCompound);

		par1NBTTagCompound.setBoolean ("broken", this.isBroken ());
	}
}