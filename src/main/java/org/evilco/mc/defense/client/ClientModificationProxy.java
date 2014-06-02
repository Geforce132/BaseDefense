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
package org.evilco.mc.defense.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import org.evilco.mc.defense.client.renderer.item.generic.DefenseStationItemRenderer;
import org.evilco.mc.defense.client.renderer.item.machine.RollingMillItemRenderer;
import org.evilco.mc.defense.client.renderer.item.sensor.CameraItemRenderer;
import org.evilco.mc.defense.client.renderer.item.trigger.MotionDetectorItemRenderer;
import org.evilco.mc.defense.client.renderer.tile.generic.DefenseStationTileEntityRenderer;
import org.evilco.mc.defense.client.renderer.tile.machine.RollingMillTileEntityRenderer;
import org.evilco.mc.defense.client.renderer.tile.sensor.CameraTileEntityRenderer;
import org.evilco.mc.defense.client.renderer.tile.trigger.MotionDetectorTileEntityRenderer;
import org.evilco.mc.defense.common.CommonModificationProxy;
import org.evilco.mc.defense.common.item.DefenseItem;
import org.evilco.mc.defense.common.tile.generic.DefenseStationTileEntity;
import org.evilco.mc.defense.common.tile.machine.RollingMillTileEntity;
import org.evilco.mc.defense.common.tile.sensor.CameraTileEntity;
import org.evilco.mc.defense.common.tile.trigger.MotionDetectorTileEntity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ClientModificationProxy extends CommonModificationProxy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postInitialize () {
		super.postInitialize ();

		this.registerRenderingCallbacks ();
	}

	/**
	 * Registers all rendering callbacks.
	 */
	public void registerRenderingCallbacks () {
		ClientRegistry.bindTileEntitySpecialRenderer (CameraTileEntity.class, new CameraTileEntityRenderer ());
		ClientRegistry.bindTileEntitySpecialRenderer (DefenseStationTileEntity.class, new DefenseStationTileEntityRenderer ());
		ClientRegistry.bindTileEntitySpecialRenderer (MotionDetectorTileEntity.class, new MotionDetectorTileEntityRenderer ());
		ClientRegistry.bindTileEntitySpecialRenderer (RollingMillTileEntity.class, new RollingMillTileEntityRenderer ());

		MinecraftForgeClient.registerItemRenderer (DefenseItem.GENERIC_DEFENSE_STATION, new DefenseStationItemRenderer ());
		MinecraftForgeClient.registerItemRenderer (DefenseItem.MACHINE_ROLLING_MILL, new RollingMillItemRenderer ());
		MinecraftForgeClient.registerItemRenderer (DefenseItem.SENSOR_CAMERA, new CameraItemRenderer ());
		MinecraftForgeClient.registerItemRenderer (DefenseItem.TRIGGER_MOTION_DETECTOR, new MotionDetectorItemRenderer ());
	}
}