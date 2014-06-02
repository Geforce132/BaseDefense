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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ChipsetItem extends Item {

	/**
	 * Stores all chipset icons.
	 */
	protected IIcon[] icons = new IIcon[6];

	/**
	 * Constructs a new CircuitBoardItem instance.
	 */
	public ChipsetItem () {
		super ();

		this.setUnlocalizedName (DefenseNames.ITEM_GENERIC_CHIPSET);
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
		this.setHasSubtypes (true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IIcon getIconFromDamage (int par1) {
		return this.icons[par1];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubItems (Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		super.getSubItems (p_150895_1_, p_150895_2_, p_150895_3_);

		for (int i = 1; i < 6; i++) {
			p_150895_3_.add (new ItemStack (this, 1, i));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUnlocalizedName (ItemStack par1ItemStack) {
		String name = super.getUnlocalizedName (par1ItemStack);

		switch (par1ItemStack.getItemDamage ()) {
			case 0: name += ".simple"; break;
			case 1: name += ".advanced"; break;
			case 2: name += ".expert"; break;
			case 3: name += ".intelligence"; break;
			case 4: name += ".recognition"; break;
			case 5: name += ".faceRecognition"; break;
			default: name += ".unknown"; break;
		}

		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerIcons (IIconRegister par1IconRegister) {
		super.registerIcons (par1IconRegister);

		this.icons[0] = par1IconRegister.registerIcon ("defense:generic/chipsetSimple");
		this.icons[1] = par1IconRegister.registerIcon ("defense:generic/chipsetAdvanced");
		this.icons[2] = par1IconRegister.registerIcon ("defense:generic/chipsetExpert");

		this.icons[3] = par1IconRegister.registerIcon ("defense:generic/chipsetIntelligence");
		this.icons[4] = par1IconRegister.registerIcon ("defense:generic/chipsetRecognition");
		this.icons[5] = par1IconRegister.registerIcon ("defense:generic/chipsetFaceRecognition");
	}
}