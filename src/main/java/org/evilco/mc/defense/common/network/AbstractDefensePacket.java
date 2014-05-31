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

import io.netty.buffer.ByteBuf;

public abstract class AbstractDefensePacket {

	/**
	 * Empty constructor.
	 */
	protected AbstractDefensePacket () { }

	/**
	 * Constructs a new AbstractDefensePacket based on it's serialized version.
	 * @param buffer The The packet buffer.
	 */
	public AbstractDefensePacket (ByteBuf buffer) { }

	/**
	 * Writes all packet data into a stream.
	 * @param buffer The packet buffer.
	 */
	public abstract void write (ByteBuf buffer);
}