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
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseBlock;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.defense.common.Strings;
import org.evilco.defense.common.tile.surveillance.SurveillanceCameraTileEntity;
import org.evilco.defense.util.Location;

import java.util.List;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SurveillanceCameraItem extends Item {

	/**
	 * Constructs a new SurveillanceCameraItem.
	 */
	public SurveillanceCameraItem () {
		super ();

		this.setMaxStackSize (16);
		this.setMaxDamage (0);

		this.setCreativeTab (DefenseCreativeTabs.SURVEILLANCE);
		this.setUnlocalizedName (Strings.ITEM_SURVEILLANCE_CAMERA);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubItems (Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		p_150895_3_.add (new ItemStack (this, 1, 0));
		p_150895_3_.add (new ItemStack (this, 1, 1));
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
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client
		if (par3World.isRemote) return true;

		// verify position
		if (par7 <= 1) return false;

		// can edit block?
		if (!par2EntityPlayer.canPlayerEdit (par4, par5, par6, par7, par1ItemStack)) return false;

		// place block
		Location blockLocation = new Location (par4, par5, par6);

		switch (par7) {
			case 5:
				blockLocation.xCoord++;
				break;
			case 3:
				blockLocation.zCoord++;
				break;
			case 4:
				blockLocation.xCoord--;
				break;
			case 2:
				blockLocation.zCoord--;
				break;
		}

		// check current block type
		if (!par3World.isAirBlock (((int) blockLocation.xCoord), ((int) blockLocation.yCoord), ((int) blockLocation.zCoord))) return false;

		// set block
		par3World.setBlock (((int) blockLocation.xCoord), ((int) blockLocation.yCoord), ((int) blockLocation.zCoord), DefenseBlock.SURVEILLANCE_CAMERA, ((par7 - 2) + (par1ItemStack.getItemDamage () == 1 ? 10 : 0)), 2);
		par1ItemStack.stackSize--;

		// update tile entity
		SurveillanceCameraTileEntity tileEntity = ((SurveillanceCameraTileEntity) par3World.getTileEntity (((int) blockLocation.xCoord), ((int) blockLocation.yCoord), ((int) blockLocation.zCoord)));

		// set owner
		tileEntity.setOwner (par2EntityPlayer);

		// set camera tier
		tileEntity.setScanningMobs ((par1ItemStack.getItemDamage () == 1));

		// use item
		return true;
	}
}