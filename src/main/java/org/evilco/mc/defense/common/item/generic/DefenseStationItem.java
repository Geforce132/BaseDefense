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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.block.DefenseBlock;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;
import org.evilco.mc.defense.common.tile.generic.DefenseStationTileEntity;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationItem extends Item {

	/**
	 * Constructs a new DefenseStationItem.
	 */
	public DefenseStationItem () {
		super ();

		this.setUnlocalizedName (DefenseNames.ITEM_GENERIC_DEFENSE_STATION);
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
		this.setHasSubtypes (true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubItems (Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		super.getSubItems (p_150895_1_, p_150895_2_, p_150895_3_);

		for (int i = 1; i < 3; i++) {
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
			case 0: break;
			case 1: name += ".advanced"; break;
			case 2: name += ".expert"; break;
			default: name += ".unknown"; break;
		}

		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client execution
		if (par3World.isRemote) return true;

		// check whether players may edit the block
		if (!par2EntityPlayer.canPlayerEdit (par4, par5, par6, par7, par1ItemStack)) return false;

		// check position
		if (par7 != 1) return false;

		// check for air blocks
		if (!par3World.isAirBlock (par4, (par5 + 1), par6) || !par3World.isAirBlock (par4, (par5 + 2), par6)) return false;

		// calculate rotation
		int metadata = MathHelper.floor_double (((double) (par2EntityPlayer.rotationYaw * 4.0f) / 360f + 2.5D)) & 3;

		// set block
		par3World.setBlock (par4, (par5 + 1), par6, DefenseBlock.GENERIC_DEFENSE_STATION, metadata, 2);
		if (!par2EntityPlayer.capabilities.isCreativeMode) par1ItemStack.stackSize--;

		// update tile entity
		DefenseStationTileEntity tileEntity = ((DefenseStationTileEntity) par3World.getTileEntity(par4, (par5 + 1), par6));

		// add player to whitelist
		tileEntity.addWhitelist (par2EntityPlayer);

		// set tier
		tileEntity.setTier (par1ItemStack.getItemDamage ());

		// confirm item use
		return true;
	}
}