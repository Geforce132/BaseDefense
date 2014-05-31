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
public class LensItem extends Item {

	/**
	 * Constructs a new LensItem instance.
	 */
	public LensItem () {
		super ();

		this.setMaxDamage (0);
		this.setMaxStackSize (16);
		this.setUnlocalizedName (DefenseNames.ITEM_GENERIC_LENS);
		this.setTextureName ("defense:generic/lens");
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getHasSubtypes () {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubItems (Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		p_150895_3_.add (new ItemStack (this, 1, 1));

		for (int i = 10; i <= 50; i += 10) {
			p_150895_3_.add (new ItemStack (this, 1, i));
		}

		p_150895_3_.add (new ItemStack (this, 1, 80));
		p_150895_3_.add (new ItemStack (this, 1, 99));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUnlocalizedName (ItemStack par1ItemStack) {
		return super.getUnlocalizedName (par1ItemStack) + par1ItemStack.getItemDamage ();
	}
}