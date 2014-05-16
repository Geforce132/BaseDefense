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

import net.minecraft.entity.Entity;

import java.util.List;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CameraDetectionPacket implements ISurveillanceNetworkPacket {

	/**
	 * Stores a list of detected entities.
	 */
	protected List<Entity> detectedEntities = null;

	/**
	 * Stores the packet source.
	 */
	protected ISurveillanceNetworkEntity source = null;

	/**
	 * Constructs a new CameraDetectionPacket.
	 * @param entity The source entity.
	 * @param detectedEntities A list of detected entities.
	 */
	public CameraDetectionPacket (ISurveillanceNetworkEntity entity, List<Entity> detectedEntities) {
		this.source = entity;
		this.detectedEntities = detectedEntities;
	}

	/**
	 * Returns the detected entities.
	 * @return The detected entities.
	 */
	public List<Entity> getDetectedEntities () {
		return this.detectedEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ISurveillanceNetworkEntity getSource () {
		return this.source;
	}
}