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
package org.evilco.mc.defense.common.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationUnregisterPacket extends AbstractDefensePacket {

	/**
	 * Stores the list mode.
	 */
	protected boolean blacklist = false;

	/**
	 * Stores the user to remove.
	 */
	protected UUID userID = null;

	/**
	 * Stores the tile entity x coordinate.
	 */
	protected int xCoord = 0;

	/**
	 * Stores the tile entity y coordinate.
	 */
	protected int yCoord = 0;

	/**
	 * Stores the tile entity z coordinate.
	 */
	protected int zCoord = 0;

	/**
	 * Constructs a new DefenseStationUnregisterPacket.
	 */
	public DefenseStationUnregisterPacket (UUID userID, boolean blacklist, int x, int y, int z) {
		super ();

		this.userID = userID;
		this.blacklist = blacklist;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}

	/**
	 * Constructs a new DefenseStationUnregisterPacket.
	 * @param buffer The packet buffer.
	 */
	public DefenseStationUnregisterPacket (ByteBuf buffer) {
		super (buffer);

		this.xCoord = buffer.readInt ();
		this.yCoord = buffer.readInt ();
		this.zCoord = buffer.readInt ();

		// read length
		int length = buffer.readShort ();

		// create buffer
		byte[] buf = new byte[length];

		// read data
		buffer.readBytes (buf);

		// construct UUID
		this.userID = UUID.fromString (new String (buf, Charsets.UTF_8));
	}

	/**
	 * Returns the list mode.
	 * @return The list mode.
	 */
	public boolean isBlacklist () {
		return this.blacklist;
	}

	/**
	 * Returns the user ID to unregister.
	 * @return The uuid.
	 */
	public UUID getUserID () {
		return this.userID;
	}

	/**
	 * Returns the x coordinate.
	 * @return The coordinate.
	 */
	public int getX () {
		return this.xCoord;
	}

	/**
	 * Returns the y coordinate.
	 * @return The coordinate.
	 */
	public int getY () {
		return this.yCoord;
	}

	/**
	 * Returns the z coordinate.
	 * @return The coordinate.
	 */
	public int getZ () {
		return this.zCoord;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf buffer) {
		// write coordinates
		buffer.writeInt (this.xCoord);
		buffer.writeInt (this.yCoord);
		buffer.writeInt (this.zCoord);

		// create buffer
		byte[] buf = this.userID.toString ().getBytes (Charsets.UTF_8);

		// write length
		buffer.writeShort (buf.length);

		// write bytes
		buffer.writeBytes (buf);
	}
}