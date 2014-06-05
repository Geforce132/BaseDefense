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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.entity.generic.SecurityBotEntity;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SecurityBotItem extends Item {

	/**
	 * Constructs a new SecurityBotItem instance.
	 */
	public SecurityBotItem () {
		super ();

		this.setUnlocalizedName (DefenseNames.ITEM_GENERIC_SECURITY_BOT);
		this.setMaxStackSize (1);
		this.setMaxDamage (0);
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
		this.setHasSubtypes (true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client side execution
		if (par3World.isRemote) return true;

		// check block side
		if (par7 != 1) return false;

		// check permissions
		if (!par2EntityPlayer.canPlayerEdit (par4, par5, par6, par7, par1ItemStack)) return false;

		// create entity
		SecurityBotEntity bot = new SecurityBotEntity (par3World, par4, (par5 + 1), par6);

		// break
		if (par1ItemStack.getItemDamage () == 0) bot.setBroken (true);

		// spawn entity
		par3World.spawnEntityInWorld (bot);

		// finish up
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubItems (Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		super.getSubItems (p_150895_1_, p_150895_2_, p_150895_3_);

		p_150895_3_.add (new ItemStack (this, 1, 1));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUnlocalizedName (ItemStack par1ItemStack) {
		return super.getUnlocalizedName (par1ItemStack) + (par1ItemStack.getItemDamage () == 0 ? ".broken" : "");
	}
}