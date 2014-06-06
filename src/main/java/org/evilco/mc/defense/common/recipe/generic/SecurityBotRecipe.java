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
package org.evilco.mc.defense.common.recipe.generic;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import org.evilco.mc.defense.common.item.generic.SecurityBotItem;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SecurityBotRecipe extends ShapelessRecipes {

	/**
	 * Constructs a new SecurityBotRecipe.
	 * @param par1ItemStack The recipe result.
	 * @param par2List The list of ingredients.
	 */
	public SecurityBotRecipe (ItemStack par1ItemStack, List par2List) {
		super (par1ItemStack, par2List);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack getCraftingResult (InventoryCrafting par1InventoryCrafting) {
		// get result
		ItemStack result = super.getCraftingResult (par1InventoryCrafting);

		// modify
		if (result.getItemDamage () == 0) {
			// find security bot
			for (int i = 0; i < par1InventoryCrafting.getSizeInventory (); i++) {
				// get stack
				ItemStack stack = par1InventoryCrafting.getStackInSlot (i);

				// check type
				if (!(stack.getItem () instanceof SecurityBotItem)) continue;

				// fix tier
				result.setItemDamage ((stack.hasTagCompound () && stack.getTagCompound ().hasKey ("tier") ? stack.getTagCompound ().getInteger ("tier") : 1));

				// break out
				break;
			}
		}

		// return correct result
		return result;
	}
}