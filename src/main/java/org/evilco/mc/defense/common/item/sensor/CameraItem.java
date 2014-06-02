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
package org.evilco.mc.defense.common.item.sensor;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.block.DefenseBlock;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;
import org.evilco.mc.defense.common.tile.sensor.CameraTileEntity;
import org.evilco.mc.defense.common.util.Location;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CameraItem extends Item {

	/**
	 * Constructs a new CameraItem instance.
	 */
	public CameraItem () {
		super ();

		this.setHasSubtypes (true);
		this.setUnlocalizedName (DefenseNames.ITEM_SENSOR_CAMERA);
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInformation (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation (par1ItemStack, par2EntityPlayer, par3List, par4);

		par3List.add (String.format (LanguageRegistry.instance ().getStringLocalization (DefenseNames.TRANSLATION_GENERIC_ITEM_LENS_QUALITY), par1ItemStack.getItemDamage ()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubItems (Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		super.getSubItems (p_150895_1_, p_150895_2_, p_150895_3_);

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
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client execution
		if (par3World.isRemote) return true;

		// skip bottom/top
		if (par7 <= 1) return false;

		// create block location
		Location location = new Location (par4, par5, par6);

		// move location
		location.moveTowardsDirection (ForgeDirection.getOrientation (par7), 1);

		// calculate rotation
		int metadata = MathHelper.floor_double (((double) (par2EntityPlayer.rotationYaw * 4.0f) / 360f + 2.5D)) & 3;

		// set block
		location.setBlock (par3World, DefenseBlock.SENSOR_CAMERA, metadata);

		// set lens quality
		((CameraTileEntity) location.getTileEntity (par3World)).setLensQuality (par1ItemStack.getItemDamage ());

		// remove item from stack
		if (!par2EntityPlayer.capabilities.isCreativeMode) par1ItemStack.stackSize--;

		return true;
	}
}