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
package org.evilco.mc.defense.common;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseNames {

	// MOD START
	public static final String PREFIX = "defense.";
	// MOD END

	// BLOCK START
	public static final String BLOCK_GENERIC_BARBED_WIRE_FENCE = PREFIX + "generic.barbedWireFence";
	public static final String BLOCK_GENERIC_DEFENSE_STATION = PREFIX + "generic.defenseStation";

	public static final String BLOCK_MACHINE_ROLLING_MILL = PREFIX + "machine.rollingMill";

	public static final String BLOCK_SENSOR_CAMERA = PREFIX + "sensor.camera";

	public static final String BLOCK_TRIGGER_MOTION_DETECTOR = PREFIX + "trigger.motionDetector";
	// BLOCK END

	// CREATIVE START
	public static final String CREATIVE_GENERIC = PREFIX + "generic";
	// CREATIVE END

	// ITEM START
	public static final String ITEM_GENERIC_CHIPSET = PREFIX + "generic.chipset";
	public static final String ITEM_GENERIC_DEFENSE_STATION = PREFIX + "generic.defenseStation";
	public static final String ITEM_GENERIC_IRON_WIRE = PREFIX + "generic.ironWire";
	public static final String ITEM_GENERIC_LENS = PREFIX + "generic.lens";
	public static final String ITEM_GENERIC_LENS_FISHEYE = PREFIX + "generic.lens.fisheye";
	public static final String ITEM_GENERIC_SANDPAPER = PREFIX + "generic.sandpaper";
	public static final String ITEM_GENERIC_SANDPAPER_DIAMOND = ITEM_GENERIC_SANDPAPER + ".diamond";
	public static final String ITEM_GENERIC_WIRELESS_TUNER = PREFIX + "generic.wirelessTuner";

	public static final String ITEM_MACHINE_DRUM = PREFIX + "machine.drum";
	public static final String ITEM_MACHINE_ROLLING_MILL = PREFIX + "machine.rollingMill";

	public static final String ITEM_SENSOR_CAMERA = PREFIX + "sensor.camera";

	public static final String ITEM_TRIGGER_MOTION_DETECTOR = PREFIX + "trigger.motionDetector";
	// ITEM END

	// REGISTRATION START
	public static final String REGISTRATION_BLOCK_GENERIC_BARBED_WIRE_FENCE = "barbed_wire_fence";
	public static final String REGISTRATION_BLOCK_GENERIC_DEFENSE_STATION = "defense_station_block";
	public static final String REGISTRATION_BLOCK_MACHINE_ROLLING_MILL = "rolling_mill_block";
	public static final String REGISTRATION_BLOCK_SENSOR_CAMERA = "camera_block";
	public static final String REGISTRATION_BLOCK_TRIGGER_MOTION_DETECTOR = "motion_detector_block";

	public static final String REGISTRATION_ITEM_GENERIC_CHIPSET = "chipset";
	public static final String REGISTRATION_ITEM_GENERIC_DEFENSE_STATION = "defense_station";
	public static final String REGISTRATION_ITEM_GENERIC_IRON_WIRE = "iron_wire";
	public static final String REGISTRATION_ITEM_GENERIC_LENS = "lens";
	public static final String REGISTRATION_ITEM_GENERIC_LENS_FISHEYE = "lens_fisheye";
	public static final String REGISTRATION_ITEM_GENERIC_SANDPAPER = "sandpaper";
	public static final String REGISTRATION_ITEM_GENERIC_SANDPAPER_DIAMOND = "sandpaper_diamond";
	public static final String REGISTRATION_ITEM_GENERIC_WIRELESS_TUNER = "wireless_tuner";
	public static final String REGISTRATION_ITEM_MACHINE_DRUM = "drum";
	public static final String REGISTRATION_ITEM_MACHINE_ROLLING_MILL = "rolling_mill";
	public static final String REGISTRATION_ITEM_SENSOR_CAMERA = "camera";
	public static final String REGISTRATION_ITEM_TRIGGER_MOTION_DETECTOR = "motion_detector";

	public static final String REGISTRATION_TILE_ENTITY_GENERIC_DEFENSE_STATION = "defenseGenericDefenseStation";
	public static final String REGISTRATION_TILE_ENTITY_MACHINE_ROLLING_MILL = "defenseMachineRollingMill";
	public static final String REGISTRATION_TILE_ENTITY_SENSOR_CAMERA = "defenseSensorCamera";
	public static final String REGISTRATION_TILE_ENTITY_TRIGGER_MOTION_DETECTOR = "defenseTriggerMotionDetector";
	// REGISTRATION END

	// TRANSLATION START
	public static final String TRANSLATION_DAMAGE_SOURCE_GENERIC_BARBED_WIRE_FENCE = PREFIX + "entity.damageSource.barbedWireFence";

	public static final String TRANSLATION_GENERIC_DEFENSE_STATION_PERMISSION_DENIED = PREFIX + "generic.defenseStation.permissionDenied";

	public static final String TRANSLATION_GENERIC_ITEM_LENS_QUALITY = PREFIX + "generic.item.lensQuality";

	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_CANNOT_CONNECT = PREFIX + "generic.wirelessTuner.cannotConnect";
	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_CONNECTION_SUCCESS = PREFIX + "generic.wirelessTuner.connectionSuccess";
	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_DATA_STORED = PREFIX + "generic.wirelessTuner.dataStored";
	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_DISCONNECT_SUCCESS = PREFIX + "generic.wirelessTuner.disconnectSuccess";
	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_INVALID_ENTITY = PREFIX + "generic.wirelessTuner.invalidEntity";
	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_PERMISSION_DENIED = PREFIX + "generic.wirelessTuner.permissionDenied";
	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_UNKNOWN_ERROR = PREFIX + "generic.wirelessTuner.unknownError";
	public static final String TRANSLATION_GENERIC_WIRELESS_TUNER_UNSUPPORTED_CONNECTION = PREFIX + "generic.wirelessTuner.unsupportedConnection";
	// TRANSLATION END
}