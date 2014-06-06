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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import org.evilco.mc.defense.common.item.generic.LensItem;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class LensRecipe extends ShapedRecipes {

	/**
	 * Constructs a new LensRecipe instance.
	 * @param par1
	 * @param par2
	 * @param par3ArrayOfItemStack
	 * @param par4ItemStack
	 */
	public LensRecipe (int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack) {
		super (par1, par2, par3ArrayOfItemStack, par4ItemStack);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack getCraftingResult (InventoryCrafting var1) {
		// get original result
		ItemStack result = super.getCraftingResult (var1);

		// modify
		for (int i = 0; i < var1.getSizeInventory (); i++) {
			// get stack
			ItemStack itemStack = var1.getStackInSlot (i);

			// verify
			if (itemStack == null || !(itemStack.getItem () instanceof LensItem)) continue;

			// modify data
			result.setItemDamage (itemStack.getItemDamage ());

			// cancel further execution
			break;
		}

		// return result
		return result;
	}
}