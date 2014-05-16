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
package org.evilco.defense.common.tile.generic;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.evilco.defense.common.tile.network.*;
import org.evilco.defense.util.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationTileEntity extends TileEntity implements ISurveillanceNetworkHub {

	/**
	 * Indicates whether the entity is active.
	 */
	protected boolean isActive = true;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connectEntity (ISurveillanceNetworkClient entity) throws SurveillanceEntityConnectionException {
		// TODO validate owners
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnectEntity (ISurveillanceNetworkClient entity) { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Location getLocation () {
		return (new Location (this.xCoord, this.yCoord, this.zCoord));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActive () {
		return this.isActive;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveMessage (ISurveillanceNetworkPacket packet) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onChunkUnload () {
		super.onChunkUnload ();

		// disable hub
		this.isActive = false;
	}

	@Override
	public void updateEntity () {
		super.updateEntity ();


	}
}