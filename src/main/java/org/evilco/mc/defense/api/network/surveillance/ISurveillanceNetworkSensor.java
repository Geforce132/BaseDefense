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

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.entity.Entity;
import org.evilco.mc.defense.common.util.Location;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ISurveillanceNetworkSensor {

	/**
	 * Checks whether a sensor may identify the entity.
	 * @param entity The entity.
	 * @param lastKnownLocation The last known location.
	 * @return True if the sensor can identify users.
	 */
	public boolean canIdentify (Entity entity, Location lastKnownLocation);

	/**
	 * Checks whether a sensor may identify the entity type.
	 * @param entity The type.
	 * @param lastKnownLocation The last known location.
	 * @return True if the sensor can declare the entity type.
	 */
	public boolean canDeclareType (Entity entity, Location lastKnownLocation);

	/**
	 * Checks whether the sensor is active.
	 * @return True if the sensor is active.
	 */
	public boolean getActive ();

	/**
	 * Sets the sensor state.
	 * @param b The state.
	 */
	public void setActive (boolean b);

	/**
	 * Tries to identify an entity.
	 */
	public void tryIdentify ();

	/**
	 * Tries to declare a type.
	 */
	public void tryDeclareType ();
}