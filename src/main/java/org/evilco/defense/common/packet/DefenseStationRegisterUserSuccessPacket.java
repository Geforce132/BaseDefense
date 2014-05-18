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
package org.evilco.defense.common.packet;

import io.netty.buffer.ByteBuf;

/**
 * Handles mod related packets.
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationRegisterUserSuccessPacket extends AbstractDefensePacket {

	/**
	 * Constructs a new DefenseStationRegisterUserSuccessPacket.
	 */
	protected DefenseStationRegisterUserSuccessPacket () {
		super ();
	}

	/**
	 * Constructs a new DefenseeStationRegisterUserSuccessPacket from a serialized buffer.
	 * @param buffer The packet buffer.
	 */
	public DefenseStationRegisterUserSuccessPacket (ByteBuf buffer) {
		super (buffer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf buffer) { }
}