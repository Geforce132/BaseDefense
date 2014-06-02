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
package org.evilco.mc.defense.common.tile.generic;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import org.evilco.mc.defense.DefenseModification;
import org.evilco.mc.defense.api.network.identification.DetectedEntity;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkAuthority;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkEntity;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkSensor;
import org.evilco.mc.defense.api.network.surveillance.SurveillanceNetworkAlarmState;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnknownConnectionException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnsupportedEntityException;
import org.evilco.mc.defense.common.tile.AbstractTileEntity;
import org.evilco.mc.defense.common.tile.IRotateableTileEntity;
import org.evilco.mc.defense.common.util.Location;

import java.util.*;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationTileEntity extends AbstractTileEntity implements ISurveillanceNetworkAuthority, IRotateableTileEntity {

	/**
	 * Defines the maximum amount of entities which may be bound to this authority.
	 */
	public static final int MAX_ENTITY_AMOUNT = 5;

	/**
	 * Stores a list of active connections.
	 */
	protected List<ISurveillanceNetworkEntity> activeConnections = new ArrayList<ISurveillanceNetworkEntity> ();

	/**
	 * Stores a list of evil users.
	 */
	protected Map<UUID, String> blacklist = new HashMap<UUID, String> ();

	/**
	 * Caches an immutable copy of the blacklist.
	 */
	protected Map<UUID, String> blacklistCache = null;

	/**
	 * Stores a list of theoretically connected entities.
	 */
	protected List<Location> connections = new ArrayList<Location> ();

	/**
	 * Stores all detected intruders.
	 */
	protected Map<Integer, DetectedEntity> detectedIntruders = new HashMap<Integer, DetectedEntity> ();

	/**
	 * Stores the current alarm state.
	 */
	protected SurveillanceNetworkAlarmState state = SurveillanceNetworkAlarmState.GREEN;

	/**
	 * Defines the state timeout.
	 */
	protected int stateTimeout = 0;

	/**
	 * Stores the authority tier.
	 */
	protected int tier = 0;

	/**
	 * Stores a list of known users.
	 */
	protected Map<UUID, String> whitelist = new HashMap<UUID, String> ();

	/**
	 * Stores an immutable copy of the whitelist.
	 */
	protected Map<UUID, String> whitelistCache = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addBlacklist (UUID playerID, String displayName) {
		// store data
		this.blacklist.put (playerID, displayName);

		// delete cache
		this.blacklistCache = null;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addBlacklist (EntityPlayer player) {
		this.addBlacklist (player.getPersistentID (), player.getDisplayName ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWhitelist (UUID playerID, String displayName) {
		// store data
		this.whitelist.put (playerID, displayName);

		// delete cache
		this.whitelistCache = null;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWhitelist (EntityPlayer player) {
		this.addWhitelist (player.getPersistentID (), player.getDisplayName ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canConnect (ISurveillanceNetworkEntity entity) {
		return !(entity instanceof ISurveillanceNetworkAuthority);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canModifyConnection (EntityPlayer player) {
		return this.isWhitelisted (player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException {
		// skip writing
		if (simulate) return;

		// store new connection
		Location location = entity.getLocation ();

		// do not store variable connections
		if (location != null) this.connections.add (location);

		// notify entity
		if (notifyPeer) entity.connect (this, false, false);

		// store active connection
		this.activeConnections.add (entity);

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException {
		if (entity != null) {
			// verify entity
			if (entity instanceof ISurveillanceNetworkAuthority) throw new SurveillanceNetworkUnsupportedEntityException ("Cannot connect multiple authorities.");

			// verify connection to other side
			if (!entity.canConnect (this)) throw new SurveillanceNetworkUnsupportedEntityException ("Cannot connect entity for unknown reasons.");

			// check connection
			if (!this.connections.contains (entity.getLocation ())) throw new SurveillanceNetworkUnknownConnectionException ("There is no connection to the supplied entity.");
		}

		// skip writing
		if (simulate) return;

		// copy active connections
		List<ISurveillanceNetworkEntity> entityList = new ArrayList (this.activeConnections);

		// remove connection
		this.forceDisconnect (entity);

		// notify entity
		if (notifyPeer && entity != null)
			entity.disconnect (this, false, false);
		else {
			for (ISurveillanceNetworkEntity entity1 : entityList) {
				entity1.disconnect (this, false, false);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forceDisconnect (ISurveillanceNetworkEntity entity) {
		// delete connection
		if (entity != null) {
			this.connections.remove (entity.getLocation ());
			this.activeConnections.remove (entity);
		} else {
			this.connections.clear ();
			this.activeConnections.clear ();
		}

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SurveillanceNetworkAlarmState getAlarmState () {
		return this.state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getAlarmTimeout () {
		return this.stateTimeout;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<UUID, String> getBlacklist () {
		if (this.blacklistCache == null) this.blacklistCache = (new ImmutableMap.Builder<UUID, String> ()).putAll (this.blacklist).build ();
		return this.blacklistCache;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Packet getDescriptionPacket () {
		// get compound
		NBTTagCompound compound = new NBTTagCompound ();

		// write NBT
		this.writeToNBT (compound);

		// create packet
		return (new S35PacketUpdateTileEntity (this.xCoord, this.yCoord, this.zCoord, this.blockMetadata, compound));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DetectedEntity getDetectedEntity (int hashCode) {
		if (!this.detectedIntruders.containsKey (hashCode)) return null;
		return this.detectedIntruders.get (hashCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DetectedEntity getDetectedEntity (Entity entity) {
		return this.getDetectedEntity (entity.hashCode ());
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
	 * Returns the authority tier.
	 * @return The tier.
	 */
	public int getTier () {
		return this.tier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<UUID, String> getWhitelist () {
		if (this.whitelistCache == null) this.whitelistCache = (new ImmutableMap.Builder<UUID, String> ()).putAll (this.whitelist).build ();
		return this.whitelistCache;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		super.initialize ();

		// make sure caches are empty
		this.blacklistCache = null;
		this.whitelistCache = null;

		// try connecting to all entities
		Iterator<Location> it = (new ArrayList<Location> (this.connections)).iterator ();

		while (it.hasNext ()) {
			// get current location
			Location location = it.next ();

			// get TileEntity
			TileEntity entity = location.getTileEntity (this.worldObj);

			// skip non-existing entities
			if (entity == null) continue;

			// delete non-network entities
			if (!(entity instanceof ISurveillanceNetworkEntity)) {
				// log
				DefenseModification.getInstance ().getLogger ().warn ("Removing an invalid network entity from known connections: " + entity.getClass ().getName () + " (" + location.xCoord + "," + location.yCoord + "," + location.zCoord + ").");

				// remove
				this.connections.remove (location);

				// skip execution
				return;
			}

			// cast entity
			ISurveillanceNetworkEntity networkEntity = ((ISurveillanceNetworkEntity) entity);

			// add to active connections
			this.activeConnections.add (networkEntity);

			// notify entity
			try {
				networkEntity.notifyConnection (this);
			} catch (SurveillanceNetworkException ex) {
				// log
				DefenseModification.getInstance ().getLogger ().warn ("Removing an invalid network connection!", ex);

				// remove connection
				this.activeConnections.remove (networkEntity);

				// remove from data
				this.connections.remove (location);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBlacklisted (UUID uuid) {
		return this.blacklist.containsKey (uuid);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBlacklisted (EntityPlayer player) {
		return this.isBlacklisted (player.getPersistentID ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected () {
		return (this.activeConnections.size () > 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected (ISurveillanceNetworkEntity entity) {
		return (this.activeConnections.contains (entity) || this.connections.contains (entity.getLocation ()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKnownIntruder (int hashCode) {
		return this.detectedIntruders.containsKey (hashCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isKnownIntruder (Entity entity) {
		return this.isKnownIntruder (entity.hashCode ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidAttackTarget (EntityLivingBase entity) {
		// allow attack to everybody else
		if (!(entity instanceof EntityPlayer)) return true;

		// cast
		EntityPlayer player = ((EntityPlayer) entity);

		// check whitelist
		if (this.isWhitelisted (player)) return false;

		// check blacklist
		if (this.isBlacklisted (player)) return true;

		// check alarm state
		// if (this.state == SurveillanceNetworkAlarmState.GREEN) return false; // TODO: Replace with detection mode variable

		// kill everything else
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWhitelisted (UUID uuid) {
		return this.whitelist.containsKey (uuid);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWhitelisted (EntityPlayer player) {
		return this.isWhitelisted (player.getPersistentID ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyIntruder (ISurveillanceNetworkEntity entity, Entity intruder) {
		// disabled?
		if (this.getAlarmState () == SurveillanceNetworkAlarmState.OFFLINE) return;

		// create wrapper
		Location intruderLocation = new Location (intruder);
		DetectedEntity detected = new DetectedEntity (intruder, intruderLocation);

		// add to intruder list
		this.detectedIntruders.put (intruder.hashCode (), detected);

		// try to find type and identify entities
		for (ISurveillanceNetworkEntity current : this.activeConnections) {
			// skip non-sensors
			if (!(current instanceof ISurveillanceNetworkSensor)) continue;

			// get sensor
			ISurveillanceNetworkSensor sensor = ((ISurveillanceNetworkSensor) current);

			// check typing
			if (this.tier >= 1 && sensor.canDeclareType (intruder, intruderLocation)) sensor.setActive (true); // TODO: Add check settings

			// check identify
			if (this.tier >= 2 && sensor.canIdentify (intruder, intruderLocation)) sensor.setActive (true);

			// enable alarm
			if (sensor.getActive ()) this.setAlarmState (SurveillanceNetworkAlarmState.YELLOW);
		}

		// enable red alarm if not already activated
		if (this.state != SurveillanceNetworkAlarmState.YELLOW) this.setAlarmState (SurveillanceNetworkAlarmState.RED);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyInvalidation (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException {
		this.activeConnections.remove (entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyConnection (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException {
		this.activeConnections.add (entity);
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
	public void onChunkUnload () {
		super.onChunkUnload ();

		for (ISurveillanceNetworkEntity entity : this.activeConnections) {
			try {
				entity.notifyInvalidation (this);
			} catch (SurveillanceNetworkException ex) {
				try {
					this.disconnect (entity, false, true);
				} catch (SurveillanceNetworkException e) {
					// remove manually
					this.connections.remove (entity.getLocation ());
					this.activeConnections.remove (entity);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void readFromNBT (NBTTagCompound p_145839_1_) {
		super.readFromNBT (p_145839_1_);

		// read alarm state
		this.state = SurveillanceNetworkAlarmState.valueOf (p_145839_1_.getString ("state"));
		this.stateTimeout = p_145839_1_.getInteger ("stateTimeout");

		// read tier
		this.tier = p_145839_1_.getInteger ("tier");

		// read connections
		NBTTagList connections = p_145839_1_.getTagList ("connections", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < connections.tagCount (); i++) { // Mojang, do you even know how to iterate?
			// get tag
			NBTTagCompound compound = connections.getCompoundTagAt (i);

			// read location
			this.connections.add (Location.readFromNBT (compound));
		}

		// read whitelist
		NBTTagList whitelist = p_145839_1_.getTagList ("whitelist", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < whitelist.tagCount (); i++) {
			// get tag
			NBTTagCompound compound = whitelist.getCompoundTagAt (i);

			// add to whitelist
			this.whitelist.put (UUID.fromString (compound.getString ("uuid")), compound.getString ("displayName"));
		}

		// read blacklist
		NBTTagList blacklist = p_145839_1_.getTagList ("blacklist", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < blacklist.tagCount (); i++) {
			// get tag
			NBTTagCompound compound = blacklist.getCompoundTagAt (i);

			// add to blacklist
			this.blacklist.put (UUID.fromString (compound.getString ("uuid")), compound.getString ("displayName"));
		}

		// clear out caches
		this.blacklistCache = null;
		this.whitelistCache = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBlacklist (UUID uuid) {
		this.blacklist.remove (uuid);

		// delete cache
		this.blacklistCache = null;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBlacklist (EntityPlayer player) {
		this.removeBlacklist (player.getPersistentID ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeWhitelist (UUID uuid) {
		this.whitelist.remove (uuid);

		// delete cache
		this.whitelistCache = null;

		// mark update
		this.worldObj.markTileEntityChunkModified (this.xCoord, this.yCoord, this.zCoord, this);
		this.worldObj.markBlockForUpdate (this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeWhitelist (EntityPlayer player) {
		this.removeWhitelist (player.getPersistentID ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAlarmState (SurveillanceNetworkAlarmState alarmState) {
		this.state = alarmState;
		if (alarmState != SurveillanceNetworkAlarmState.GREEN && alarmState != SurveillanceNetworkAlarmState.OFFLINE)
			this.stateTimeout = DefenseModification.getInstance ().getConfiguration ().getStateTimeout ();
		else
			this.stateTimeout = 0;

		// reset state
		if (this.state == SurveillanceNetworkAlarmState.GREEN) {
			// disable all sensors
			for (ISurveillanceNetworkEntity entity : this.activeConnections) {
				// skip non-sensors
				if (!(entity instanceof ISurveillanceNetworkSensor)) continue; // TODO: Add support for response entities

				// cast
				ISurveillanceNetworkSensor sensor = ((ISurveillanceNetworkSensor) entity);

				// de-activate
				if (sensor.getActive ()) sensor.setActive (false);
			}

			// delete intruder list
			this.detectedIntruders.clear ();
		}
	}

	/**
	 * Sets a new authority tier.
	 * @param tier The new tier.
	 */
	public void setTier (int tier) {
		this.tier = tier;

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

		// cancel on client side
		if (this.worldObj.isRemote) return;

		// cancel if system is offline
		if (this.getAlarmState () == SurveillanceNetworkAlarmState.OFFLINE) return;

		// decrease state timeout
		this.stateTimeout = Math.max (0, (this.stateTimeout - 1));

		// reset alarm state
		if (this.stateTimeout == 0 && this.getAlarmState () != SurveillanceNetworkAlarmState.GREEN) this.setAlarmState (SurveillanceNetworkAlarmState.valueOf ((this.getAlarmState ().stateNumber - 1)));

		// handle sensors
		if (this.state != SurveillanceNetworkAlarmState.GREEN) {
			for (ISurveillanceNetworkEntity entity : this.activeConnections) {
				// skip non-sensors
				if (!(entity instanceof ISurveillanceNetworkSensor)) continue;

				// cast
				ISurveillanceNetworkSensor sensor = ((ISurveillanceNetworkSensor) entity);

				// skip disabled sensors
				if (!sensor.getActive ()) continue;

				// try to identify/type
				if (this.tier >= 1) sensor.tryDeclareType ();
				if (this.tier >= 2) sensor.tryIdentify ();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToNBT (NBTTagCompound p_145841_1_) {
		super.writeToNBT (p_145841_1_);

		// write alarm state
		p_145841_1_.setString ("state", this.state.toString ());
		p_145841_1_.setInteger ("stateTimeout", this.stateTimeout);

		// write tier
		p_145841_1_.setInteger ("tier", this.tier);

		// write connections
		NBTTagList connections = new NBTTagList ();

		for (Location location : this.connections) {
			// create new NBT tag
			NBTTagCompound compound = new NBTTagCompound ();

			// write data
			location.writeToNBT (compound);

			// append tag
			connections.appendTag (compound);
		}

		p_145841_1_.setTag ("connections", connections);

		// write whitelist
		NBTTagList tagList = new NBTTagList ();

		for (Map.Entry<UUID, String> entry : this.whitelist.entrySet ()) {
			// create new NBT tag
			NBTTagCompound compound = new NBTTagCompound ();

			// write UUID
			compound.setString ("uuid", entry.getKey ().toString ());

			// write display name
			compound.setString ("displayName", entry.getValue ());

			// append
			tagList.appendTag (compound);
		}

		// append tag
		p_145841_1_.setTag ("whitelist", tagList);

		// write blacklist
		NBTTagList blacklist = new NBTTagList ();

		for (Map.Entry<UUID, String> entry : this.blacklist.entrySet ()) {
			// create new NBT tag
			NBTTagCompound compound = new NBTTagCompound ();

			// write UUID
			compound.setString ("uuid", entry.getKey ().toString ());

			// write display name
			compound.setString ("displayName", entry.getValue ());

			// append
			tagList.appendTag (compound);
		}
	}
}