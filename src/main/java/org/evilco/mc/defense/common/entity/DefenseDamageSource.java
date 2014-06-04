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
package org.evilco.mc.defense.common.entity;

import net.minecraft.util.DamageSource;
import org.evilco.mc.defense.common.DefenseNames;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseDamageSource {
	public static final DamageSource GENERIC_BARBED_WIRE_FENCE = new DamageSource (DefenseNames.TRANSLATION_DAMAGE_SOURCE_GENERIC_BARBED_WIRE_FENCE).setDifficultyScaled ();
	public static final DamageSource GENERIC_SECURITY_BOT = new DamageSource (DefenseNames.TRANSLATION_DAMAGE_SOURCE_GENERIC_SECURITY_BOT).setDifficultyScaled ().setProjectile ();
}