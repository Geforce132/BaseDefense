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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUnlocalizedName (ItemStack par1ItemStack) {
		return super.getUnlocalizedName (par1ItemStack) + par1ItemStack.getItemDamage ();
	}
}