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
package org.evilco.mc.defense.common.item.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.block.DefenseBlock;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class RollingMillItem extends Item {

	/**
	 * Constructs a new RollingMillItem instance.
	 */
	public RollingMillItem () {
		super ();

		this.setMaxDamage (0);
		this.setMaxStackSize (1);
		this.setUnlocalizedName (DefenseNames.ITEM_MACHINE_ROLLING_MILL);
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client execution
		if (par3World.isRemote) return true;

		// check position
		if (par7 != 1) return false;

		// check permissions
		if (!par2EntityPlayer.canPlayerEdit (par4, (par5 + 1), par6, par7, par1ItemStack)) return false;

		// calculate rotation
		int metadata = MathHelper.floor_double (((double) (par2EntityPlayer.rotationYaw * 4.0f) / 360f + 2.5D)) & 3;

		// set block
		par3World.setBlock (par4, (par5 + 1), par6, DefenseBlock.MACHINE_ROLLING_MILL, metadata, 2);
		return true;
	}
}