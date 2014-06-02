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
package org.evilco.mc.defense.common.item;

import net.minecraft.item.Item;
import org.evilco.mc.defense.common.item.generic.*;
import org.evilco.mc.defense.common.item.machine.RollingMillItem;
import org.evilco.mc.defense.common.item.sensor.CameraItem;
import org.evilco.mc.defense.common.item.trigger.MotionDetectorItem;

/**
 * Stores all modification items.
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseItem {
	public static final Item GENERIC_CHIPSET = new ChipsetItem ();
	public static final Item GENERIC_DEFENSE_STATION = new DefenseStationItem ();
	public static final Item GENERIC_IRON_WIRE = new IronWireItem ();
	public static final Item GENERIC_LENS = new LensItem ();
	public static final Item GENERIC_LENS_FISHEYE = new FisheyeLensItem ();
	public static final Item GENERIC_SANDPAPER = new SandpaperItem ();
	public static final Item GENERIC_SANDPAPER_DIAMOND = new DiamondSandpaperItem ();
	public static final Item GENERIC_WIRELESS_TUNER = new WirelessTunerItem ();

	public static final Item MACHINE_ROLLING_MILL = new RollingMillItem ();

	public static final Item SENSOR_CAMERA = new CameraItem ();

	public static final Item TRIGGER_MOTION_DETECTOR = new MotionDetectorItem ();
}