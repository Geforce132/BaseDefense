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
package org.evilco.mc.defense.common.item.generic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SandpaperItem extends Item {

	/**
	 * Constructs a new SandpaperItem instance.
	 */
	public SandpaperItem () {
		super ();

		this.setMaxDamage (4);
		this.setMaxStackSize (1);
		this.setUnlocalizedName (DefenseNames.ITEM_GENERIC_SANDPAPER);
		this.setTextureName ("defense:generic/sandpaper");
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean doesContainerItemLeaveCraftingGrid (ItemStack par1ItemStack) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemStack getContainerItem (ItemStack itemStack) {
		return (this.isDamageable () ? new ItemStack (itemStack.getItem (), 1, itemStack.getItemDamage () + 1) : itemStack);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasContainerItem () {
		return true;
	}
}