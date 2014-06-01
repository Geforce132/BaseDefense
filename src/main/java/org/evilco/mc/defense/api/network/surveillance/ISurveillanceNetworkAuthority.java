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
package org.evilco.mc.defense.api.network.surveillance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.evilco.mc.defense.api.network.identification.DetectedEntity;

import java.util.Map;
import java.util.UUID;

/**
 * Defines required methods for network entities.
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ISurveillanceNetworkAuthority extends ISurveillanceNetworkEntity {

	/**
	 * Adds a user to the blacklist.
	 * @param playerID The user identifier.
	 * @param displayName The display name.
	 */
	public void addBlacklist (UUID playerID, String displayName);

	/**
	 * Adds a user to the blacklist.
	 * @param player The player identifier.
	 */
	public void addBlacklist (EntityPlayer player);

	/**
	 * Adds a user to the whitelist.
	 * @param playerID The user identifier.
	 * @param displayName The display name.
	 */
	public void addWhitelist (UUID playerID, String displayName);

	/**
	 * Adds a user to the whitelist.
	 * @param player The user.
	 */
	public void addWhitelist (EntityPlayer player);

	/**
	 * Returns the surveillance network alarm state.
	 * @return The alarm state.
	 */
	public SurveillanceNetworkAlarmState getAlarmState ();

	/**
	 * Returns the amount of ticks left until the alarm level is decreased.
	 * @return The alarm timeout.
	 */
	public int getAlarmTimeout ();

	/**
	 * Returns the complete list of blacklisted users.
	 * Please note: Modifications to this map may lead into weird behaviour.
	 * @return The blacklist map.
	 */
	public Map<UUID, String> getBlacklist ();

	/**
	 * Returns a detected entity instance.
	 * @param hashCode The hash code of the original entity.
	 * @return A detected entity (null if none can be found).
	 */
	public DetectedEntity getDetectedEntity (int hashCode);

	/**
	 * Returns a detected entity instance.
	 * @param entity The original entity.
	 * @return A detected entity (null if none can be found).
	 */
	public DetectedEntity getDetectedEntity (Entity entity);

	/**
	 * Returns the complete list of whitelisted users.
	 * Please note: Modifications to this map may lead into weird behaviour.
	 * @return The whitelist map.
	 */
	public Map<UUID, String> getWhitelist ();

	/**
	 * Checks whether the specified user is blacklisted.
	 * @param uuid The user identifier.
	 * @return True if the user is blacklisted.
	 */
	public boolean isBlacklisted (UUID uuid);

	/**
	 * Checks whether the specified user is blacklisted.
	 * @param player The user.
	 * @return True if the user is blacklisted.
	 */
	public boolean isBlacklisted (EntityPlayer player);

	/**
	 * Checks whether the specified intruder is already known.
	 * @param hashCode The hash code of the original entity.
	 * @return True if the intruder is already known.
	 */
	public boolean isKnownIntruder (int hashCode);

	/**
	 * Checks whether the specified intruder is already known.
	 * @param entity The original entity.
	 * @return True if the intruder is already known.
	 */
	public boolean isKnownIntruder (Entity entity);

	/**
	 * Checks whether the specified entity is a valid attack target.
	 * @param entity The entity.
	 * @return True if the entity is a valid target.
	 */
	public boolean isValidAttackTarget (EntityLivingBase entity);

	/**
	 * Checks whether the specified user is whitelisted.
	 * @param uuid The user identifier.
	 * @return True if the user is whitelisted.
	 */
	public boolean isWhitelisted (UUID uuid);

	/**
	 * Checks whether the specified user is whitelisted.
	 * @param player The user.
	 * @return True if the user is whitelisted.
	 */
	public boolean isWhitelisted (EntityPlayer player);

	/**
	 * Notifies the authority about a possible intruder.
	 * @param entity The surveillance entity.
	 * @param location The detection location.
	 */
	public void notifyIntruder (ISurveillanceNetworkEntity entity, Entity intruder);

	/**
	 * Removes a user from the blacklist.
	 * @param uuid The user identifier.
	 */
	public void removeBlacklist (UUID uuid);

	/**
	 * Removes a user from the blacklist.
	 * @param player The user.
	 */
	public void removeBlacklist (EntityPlayer player);

	/**
	 * Removes a user from the whitelist.
	 * @param uuid The user identifier.
	 */
	public void removeWhitelist (UUID uuid);

	/**
	 * Removes a user from the whitelist.
	 * @param player The user.
	 */
	public void removeWhitelist (EntityPlayer player);

	/**
	 * Sets a new alarm state.
	 * @param alarmState The alarm state.
	 */
	public void setAlarmState (SurveillanceNetworkAlarmState alarmState);
}