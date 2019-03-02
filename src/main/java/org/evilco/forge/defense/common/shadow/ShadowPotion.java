/*
 * Copyright 2014 Johannes Donath <johannesd@evil-co.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.evilco.forge.defense.common.shadow;

import org.evilco.forge.defense.common.shadow.potion.ShadowResistancePotion;
import org.evilco.forge.defense.module.ShadowModule;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowPotion {
	private ShadowPotion () { }

	public static ShadowResistancePotion SHADOW_RESISTANCE = null;

	/**
	 * Initializes the container.
	 * @param module The module.
	 */
	public static void initialize (ShadowModule module) {
		SHADOW_RESISTANCE = new ShadowResistancePotion (module.getPotionShadowResistanceID());
	}
}
