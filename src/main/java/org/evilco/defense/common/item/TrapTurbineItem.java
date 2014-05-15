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
package org.evilco.defense.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseBlock;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.defense.common.Strings;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class TrapTurbineItem extends Item {

	/**
	 * Constructs a new TrapTurbineItem.
	 */
	public TrapTurbineItem () {
		super ();

		this.setMaxStackSize (1);
		this.setMaxDamage (0);

		this.setCreativeTab (DefenseCreativeTabs.TRAP);
		this.setUnlocalizedName (Strings.ITEM_TRAP_TURBINE);

		this.setTextureName ("defense:trapTurbine");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par7 != 1) return false;

		// check whether players may edit the block
		if (!par2EntityPlayer.canPlayerEdit (par4, par5, par6, par7, par1ItemStack) || !par2EntityPlayer.canPlayerEdit (par4, par5 + 1, par6, par7, par1ItemStack)) return false;

		// find surface
		Block surface = par3World.getBlock (par4, par5, par6);

		// verify location
		if (surface == null || !par3World.isAirBlock (par4, par5 + 1, par6) || !par3World.isAirBlock (par4, par5 + 2, par6)) return false;

		// calculate metadata
		int metadata = (MathHelper.floor_double (((double) (par2EntityPlayer.rotationYaw * 4.0f) / 360.0f) + 2.5d) & 3);

		// set block
		par3World.setBlock (par4, par5 + 1, par6, DefenseBlock.TURBINE, (metadata - 1), 2);

		// remove from stack
		if (!par2EntityPlayer.capabilities.isCreativeMode) --par1ItemStack.stackSize;

		// done!
		return true;
	}
}