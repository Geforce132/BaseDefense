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
package org.evilco.mc.defense.common.block;

import net.minecraft.block.Block;
import org.evilco.mc.defense.common.block.generic.BarbedWirePaneBlock;
import org.evilco.mc.defense.common.block.generic.DefenseStationBlock;
import org.evilco.mc.defense.common.block.machine.RollingMillBlock;
import org.evilco.mc.defense.common.block.trigger.MotionDetectorBlock;

/**
 * Stores all modification related blocks.
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseBlock {
	public static final Block GENERIC_BARBED_WIRE_PANE = new BarbedWirePaneBlock ();
	public static final Block GENERIC_DEFENSE_STATION = new DefenseStationBlock ();

	public static final Block MACHINE_ROLLING_MILL = new RollingMillBlock ();

	public static final Block TRIGGER_MOTION_DETECTOR = new MotionDetectorBlock ();
}