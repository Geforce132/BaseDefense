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
package org.evilco.mc.defense.api.network.identification;

import net.minecraft.entity.Entity;
import org.evilco.mc.defense.common.util.Location;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DetectedEntity {

	/**
	 * Stores the detected entity reference.
	 */
	protected Entity entity = null;

	/**
	 * Stores the entity identity.
	 */
	protected EntityIdentity identity = null;

	/**
	 * Stores the last known location.
	 */
	protected Location lastKnownLocation = null;

	/**
	 * Stores the known entity type.
	 */
	protected EntityType type = EntityType.UNKNOWN;

	/**
	 * Constructs a new DetectedEntity instance.
	 * @param entity The entity.
	 * @param identity The identity.
	 * @param lastKnownLocation The last known location.
	 * @param type The entity type
	 */
	public DetectedEntity (Entity entity, Location lastKnownLocation, EntityType type, EntityIdentity identity) {
		this.entity = entity;
		this.identity = identity;
		this.lastKnownLocation = lastKnownLocation;
		this.type = type;
	}

	/**
	 * Constructs a new DetectedEntity instance.
	 * @param entity The entity.
	 * @param lastKnownLocation The last known location.
	 */
	public DetectedEntity (Entity entity, Location lastKnownLocation) {
		this (entity, lastKnownLocation, null, null);
	}

	/**
	 * Returns the detected entity.
	 * @return The entity.
	 */
	public Entity getEntity () {
		return this.entity;
	}

	/**
	 * Returns the detected identity (if known).
	 * @return The identity.
	 */
	public EntityIdentity getIdentity () {
		return this.identity;
	}

	/**
	 * Returns the last known location.
	 * @return The location.
	 */
	public Location getLastKnownLocation () {
		return this.lastKnownLocation;
	}

	/**
	 * Returns the entity type (if known).
	 * @return The type.
	 */
	public EntityType getType () {
		return this.type;
	}

	/**
	 * Sets
	 * @param identity
	 */
	public void setIdentity (EntityIdentity identity) {
		this.identity = identity;
	}

	/**
	 * Sets the entity type.
	 * @param type The entity type.
	 */
	public void setType (EntityType type) {
		this.type = type;
	}
}