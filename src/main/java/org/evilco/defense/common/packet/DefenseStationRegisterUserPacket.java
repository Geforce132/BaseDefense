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

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

/**
 * Allows sending updates about new registered users.
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationRegisterUserPacket extends AbstractDefensePacket {

	/**
	 * Stores the username to register.
	 */
	protected String username = null;

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
	 * Constructs a new DefenseStationRegisterUserPacket.
	 * @param username The username.
	 */
	public DefenseStationRegisterUserPacket (String username, int x, int y, int z) {
		super ();

		this.username = username;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}

	/**
	 * Constructs a new DefenseStationRegisterUserPacket from a serialized packet buffer.
	 * @param buffer The packet buffer.
	 */
	public DefenseStationRegisterUserPacket (ByteBuf buffer) {
		super (buffer);

		// read coordinates
		this.xCoord = buffer.readInt ();
		this.yCoord = buffer.readInt ();
		this.zCoord = buffer.readInt ();

		// read length
		int length = buffer.readShort ();

		// create buffer
		byte[] usernameRaw = new byte[length];

		// read buffer
		buffer.readBytes (usernameRaw);

		// convert string
		this.username = new String (usernameRaw, Charsets.UTF_8);
	}

	/**
	 * Returns the username stored in this packet.
	 * @return The username.
	 */
	public String getUsername () {
		return this.username;
	}

	/**
	 * Returns the X coordinate.
	 * @return The x coordinate.
	 */
	public int getX () {
		return this.xCoord;
	}

	/**
	 * Returns the Y coordinate.
	 * @return The y coordinate.
	 */
	public int getY () {
		return this.yCoord;
	}

	/**
	 * Returns the Z coordinate.
	 * @return The z coordinate.
	 */
	public int getZ () {
		return this.zCoord;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ByteBuf buffer) {
		// convert string to byte[]
		byte[] usernameRaw = this.username.getBytes (Charsets.UTF_8);

		// write coordinates
		buffer.writeInt (this.xCoord);
		buffer.writeInt (this.yCoord);
		buffer.writeInt (this.zCoord);

		// write length
		buffer.writeShort (usernameRaw.length);

		// write data
		buffer.writeBytes (usernameRaw);
	}
}