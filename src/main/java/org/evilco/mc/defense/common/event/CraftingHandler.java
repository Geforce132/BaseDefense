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
		// get crafting matrix
		for (int i = 0; i < event.craftMatrix.getSizeInventory (); i++) {
			// get stack
			ItemStack stack = event.craftMatrix.getStackInSlot (i);

			// skip empty stacks
			if (stack == null) continue;

			// check item
			// if (!(stack.getItem () instanceof IReusableItem) && !(stack.getItem () instanceof ItemBucket)) continue;

			// add item back to grid
			event.craftMatrix.setInventorySlotContents (i, new ItemStack (stack.getItem (), stack.stackSize, (stack.getItemDamage () + 1)));
		}
	}
}