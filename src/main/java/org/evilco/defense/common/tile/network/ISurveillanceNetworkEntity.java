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
package org.evilco.defense.common.tile.network;

import net.minecraft.entity.player.EntityPlayer;
import org.evilco.defense.util.Location;

import java.util.UUID;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ISurveillanceNetworkEntity {

	/**
	 * Returns the entity location.
	 * @return The location.
	 */
	public Location getLocation ();

	/**
	 * Returns the entity owner.
	 * @return The owner UUID.
	 */
	public UUID getOwner ();

	/**
	 * Checks whether the hub is available.
	 * @return True if the hub is available.
	 */
	public boolean isActive ();

	/**
	 * Receives a surveillance message.
	 */
	public void receiveMessage (ISurveillanceNetworkPacket packet);

	/**
	 * Sets a new owner.
	 * @param owner The owner.
	 */
	public void setOwner (UUID owner);

	/**
	 * Sets a new owner.
	 * @param player The owner.
	 */
	public void setOwner (EntityPlayer player);
}