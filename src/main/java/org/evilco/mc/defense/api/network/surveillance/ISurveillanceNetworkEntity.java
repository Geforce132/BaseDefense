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

import net.minecraft.entity.player.EntityPlayer;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkException;
import org.evilco.mc.defense.common.util.Location;

/**
 * Defines required methods for network entities.
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ISurveillanceNetworkEntity {

	/**
	 * Checks whether the entity may be connected.
	 * @return True if the entity may connect.
	 */
	public boolean canConnect (ISurveillanceNetworkEntity entity);

	/**
	 * Checks whether the player may modify the connection.
	 * @param player The player.
	 * @return True if the player may modify the connection.
	 */
	public boolean canModifyConnection (EntityPlayer player);

	/**
	 * Connects two entities.
	 * @param entity The network entity.
	 * @param simulate True if a simulation of the connection is requested rather than actually establishing a connection.
	 * @param notifyPeer True if the peer should be notified about the connection.
	 * @throws SurveillanceNetworkException
	 */
	public void connect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException;

	/**
	 * Disconnects two entities.
	 * @param entity The network entity.
	 * @param simulate True if a simulation of the connection is requested rather than actually disconnecting.
	 * @param notifyPeer True if the peer should be notified about the disconnect.
	 * @throws SurveillanceNetworkException
	 */
	public void disconnect (ISurveillanceNetworkEntity entity, boolean simulate, boolean notifyPeer) throws SurveillanceNetworkException;

	/**
	 * Returns the entity world location.
	 * @return The location.
	 */
	public Location getLocation ();

	/**
	 * Invalidates a connection (temporary disconnects a client due to chunk or world unloads).
	 * @param entity The network entity.
	 * @throws SurveillanceNetworkException
	 */
	public void notifyInvalidation (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException;

	/**
	 * Re-Validates a connection (connects an entity after invalidation).
	 * @param entity The network entity.
	 * @throws SurveillanceNetworkException
	 */
	public void notifyConnection (ISurveillanceNetworkEntity entity) throws SurveillanceNetworkException;

	/**
	 * Checks whether the network entity does have at least one connection.
	 * @return True if there is at least one connection.
	 */
	public boolean isConnected ();

	/**
	 * Checks whether the entity is connected to
	 * @param entity The network entity.
	 * @return True if there is at least one connection.
	 */
	public boolean isConnected (ISurveillanceNetworkEntity entity);

}