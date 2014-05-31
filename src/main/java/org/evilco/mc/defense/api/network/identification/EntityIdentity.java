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

import java.util.UUID;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class EntityIdentity {

	/**
	 * Stores the persistent identifier.
	 */
	protected UUID persistentIdentifier = null;

	/**
	 * Stores the display name.
	 */
	protected String displayName = null;

	/**
	 * Constructs a new EntityIdentity instance.
	 * @param persistentIdentifier The entity persistent identifier.
	 * @param displayName The display name.
	 */
	public EntityIdentity (UUID persistentIdentifier, String displayName) {
		this.persistentIdentifier = persistentIdentifier;
		this.displayName = displayName;
	}

	/**
	 * Returns the persistent identifier.
	 * @return The identifier.
	 */
	public UUID getPersistentIdentifier () {
		return this.persistentIdentifier;
	}

	/**
	 * Returns the display name.
	 * @return The display name.
	 */
	public String getDisplayName () {
		return this.displayName;
	}
}