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
package org.evilco.mc.defense.common.item.trigger;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.block.DefenseBlock;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;
import org.evilco.mc.defense.common.tile.trigger.MotionDetectorTileEntity;
import org.evilco.mc.defense.common.util.Location;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MotionDetectorItem extends Item {

	/**
	 * Constructs a new MotionDetectorItem instance.
	 */
	public MotionDetectorItem () {
		super ();

		this.setUnlocalizedName (DefenseNames.ITEM_TRIGGER_MOTION_DETECTOR);
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

		// check whether players may edit the block
		if (!par2EntityPlayer.canPlayerEdit (par4, par5, par6, par7, par1ItemStack)) return false;

		// place block
		Location blockLocation = new Location (par4, par5, par6);

		// get direction
		ForgeDirection direction = ForgeDirection.getOrientation (par7);

		// update location
		blockLocation.xCoord += direction.offsetX;
		blockLocation.yCoord += direction.offsetY;
		blockLocation.zCoord += direction.offsetZ;

		// check current block type
		if (!par3World.isAirBlock (((int) blockLocation.xCoord), ((int) blockLocation.yCoord), ((int) blockLocation.zCoord))) return false;

		// calculate rotation
		int metadata = MathHelper.floor_double (((double) (par2EntityPlayer.rotationYaw * 4.0f) / 360f + 2.5D)) & 3;

		// set block
		par3World.setBlock (((int) blockLocation.xCoord), ((int) blockLocation.yCoord), ((int) blockLocation.zCoord), DefenseBlock.TRIGGER_MOTION_DETECTOR, metadata, 2);

		// set lens quality
		TileEntity tileEntity = blockLocation.getTileEntity (par3World);
		((MotionDetectorTileEntity) tileEntity).setLensQuality (par1ItemStack.getItemDamage ());

		// confirm item use
		return true;
	}
}