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

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Defines all possible packet types.
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum DefensePacketType {
	DEFENSE_STATION_REGISTER_USER (0, DefenseStationRegisterUserPacket.class),
	DEFENSE_STATION_REGISTER_USER_ERROR (1, DefenseStationRegisterUserErrorPacket.class),
	DEFENSE_STATION_REGISTER_USER_SUCCESS (2, DefenseStationRegisterUserSuccessPacket.class),
	DEFENSE_STATION_UNREGISTER_USER (3, DefenseStationUnregisterPacket.class);

	/**
	 * Static Initialization.
	 */
	static {
		// create map builder
		ImmutableMap.Builder<Integer, DefensePacketType> mapBuilder = new ImmutableMap.Builder<Integer, DefensePacketType> ();
		ImmutableMap.Builder<Class<? extends AbstractDefensePacket>, Integer> reverseMapBuilder = new ImmutableMap.Builder<Class<? extends AbstractDefensePacket>, Integer> ();

		// add items
		for (DefensePacketType type : values ()) {
			mapBuilder.put (type.packetID, type);
			reverseMapBuilder.put (type.packetType, type.packetID);
		}

		// build maps
		packetMap = mapBuilder.build ();
		reversePacketMap = reverseMapBuilder.build ();
	}

	/**
	 * Stores a lookup map for packet types.
	 */
	protected static final Map<Integer, DefensePacketType> packetMap;

	/**
	 * Stores a reverse lookup map for packet types.
	 */
	protected static final Map<Class<? extends AbstractDefensePacket>, Integer> reversePacketMap;

	/**
	 * Stores the packetID.
	 */
	public final int packetID;

	/**
	 * Stores the packet type.
	 */
	public final Class<? extends AbstractDefensePacket> packetType;

	/**
	 * Constructs a new DefensePacketType.
	 * @param packetType The packet type.
	 */
	private DefensePacketType (int packetID, Class<? extends AbstractDefensePacket> packetType) {
		this.packetID = packetID;
		this.packetType = packetType;
	}

	/**
	 * Gets a packet based on it's packet identifier.
	 * @param packetID The packet identifier.
	 * @return The packet class (if any).
	 */
	public static DefensePacketType valueOf (int packetID) {
		return packetMap.get (packetID);
	}

	/**
	 * Gets a packet identifier based on it's type.
	 * @param packetType The packet type.
	 * @return The packet identifier.
	 */
	public static int valueOf (Class<? extends AbstractDefensePacket> packetType) {
		return reversePacketMap.get (packetType);
	}
}