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
package org.evilco.defense.common.item.surveillance;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.defense.common.Strings;
import org.evilco.defense.common.entity.SecurityBotEntity;
import org.evilco.defense.util.Location;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SecurityBotItem extends Item {

	/**
	 * Constructs a new SecurityBotItem.
	 */
	public SecurityBotItem () {
		super ();

		this.setMaxStackSize (1);
		this.setMaxDamage (0);

		this.setCreativeTab (DefenseCreativeTabs.SURVEILLANCE);
		this.setUnlocalizedName (Strings.ITEM_SURVEILLANCE_SECURITY_BOT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client version
		if (par3World.isRemote) return true;

		// security bots may only be placed on the ground
		if (par7 != 1) return false;

		// verify permissions
		if (!par2EntityPlayer.canPlayerEdit (par4, (par5 + 1), par6, par7, par1ItemStack)) return false;

		// spawn bot entity
		SecurityBotEntity entity = new SecurityBotEntity (par3World);
		entity.setLocationAndAngles (par4, (par5 + 1), par6, 0.0f, 0.0f);
		entity.setOriginalLocation (new Location (par4, (par5 + 1), par6));
		entity.setOwner (par2EntityPlayer);

		par3World.spawnEntityInWorld (entity);

		// remove item
		if (!par2EntityPlayer.capabilities.isCreativeMode) par1ItemStack.stackSize--;

		// done!
		return true;
	}
}