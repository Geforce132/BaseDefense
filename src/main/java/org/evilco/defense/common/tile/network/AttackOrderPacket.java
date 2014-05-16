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

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class AttackOrderPacket implements ISurveillanceNetworkPacket {

	/**
	 * Stores the packet source entity.
	 */
	protected ISurveillanceNetworkEntity source = null;

	/**
	 * Stores the attack target.
	 */
	protected EntityLivingBase target = null;

	/**
	 * Constructs a new AttackOrderPacket.
	 * @param source The packet source.
	 * @param target The attack target.
	 */
	public AttackOrderPacket (ISurveillanceNetworkEntity source, EntityLivingBase target) {
		this.source = source;
		this.target = target;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ISurveillanceNetworkEntity getSource () {
		return this.source;
	}

	/**
	 * Returns the attack target.
	 * @return The target.
	 */
	public EntityLivingBase getTarget () {
		return this.target;
	}
}