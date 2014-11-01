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
package org.evilco.forge.defense;

import cpw.mods.fml.common.Mod;
import lombok.Getter;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
@Mod (modid = DefenseModification.MOD_ID, useMetadata = true, canBeDeactivated = false)
public class DefenseModification {

	/**
	 * Defines the modification identifier.
	 */
	public static final String MOD_ID = "Defense";

	/**
	 * Stores the modification instance
	 */
	@Mod.Instance (MOD_ID)
	@Getter
	private static DefenseModification instance = null;
}
