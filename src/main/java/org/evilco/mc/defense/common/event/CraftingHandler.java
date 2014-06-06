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
package org.evilco.mc.defense.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.ItemStack;
import org.evilco.mc.defense.common.item.DefenseItem;

/**
 * Stores all modification related blocks.
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CraftingHandler {

	/**
	 * Handles item crafting.
	 * @param event The event.
	 */
	@SubscribeEvent
	public void onItemCrafted (PlayerEvent.ItemCraftedEvent event) {
		// motion detector (copy damage)
		if (event.crafting.getItem ().equals (DefenseItem.TRIGGER_MOTION_DETECTOR) || event.crafting.getItem ().equals (DefenseItem.SENSOR_CAMERA)) event.crafting.setItemDamage (event.craftMatrix.getStackInSlot (5).getItemDamage ());

		// security bot (broken to fixed)
		if (event.crafting.getItem ().equals (DefenseItem.GENERIC_SECURITY_BOT) && event.crafting.getItemDamage () != 0) {
			// iterate over all elements
			for (int i = 0; i < event.craftMatrix.getSizeInventory (); i++) {
				// get stack
				ItemStack stack = event.craftMatrix.getStackInSlot (i);

				// skip items other than the security bot
				if (!stack.getItem ().equals (DefenseItem.GENERIC_SECURITY_BOT) || stack.getItemDamage () != 0) continue;

				// copy data
				event.crafting.setItemDamage (((stack.getTagCompound () == null || !stack.getTagCompound ().hasKey ("tier")) ? 1 : stack.getTagCompound ().getInteger ("tier")));

				// skip further execution
				break;
			}
		}
	}
}