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
package org.evilco.defense.common.item.generic;

import net.minecraft.item.Item;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.defense.common.Strings;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class GenericLensItem extends Item {

	public GenericLensItem () {
		super ();

		this.setMaxStackSize (16);
		this.setMaxDamage (0);

		this.setCreativeTab (DefenseCreativeTabs.SURVEILLANCE);
		this.setUnlocalizedName (Strings.ITEM_GENERIC_LENS);

		this.setTextureName ("defense:generic/lens");
	}
}