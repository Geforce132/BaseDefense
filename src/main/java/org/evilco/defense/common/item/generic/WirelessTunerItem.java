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
package org.evilco.defense.common.item.generic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.defense.common.Strings;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkClient;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkEntity;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkHub;
import org.evilco.defense.common.tile.network.SurveillanceEntityConnectionException;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class WirelessTunerItem extends Item {

	/**
	 * Constructs a new WirelessTunerItem.
	 */
	public WirelessTunerItem () {
		super ();

		this.setMaxStackSize (1);
		this.setMaxDamage (100);

		this.setCreativeTab (DefenseCreativeTabs.SURVEILLANCE);
		this.setUnlocalizedName (Strings.ITEM_GENERIC_WIRELESS_TUNER);

		this.setTextureName ("defense:generic/wirelessTuner");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par7 != 1) return false;

		// check whether players may edit the block
		if (!par2EntityPlayer.canPlayerEdit (par4, par5, par6, par7, par1ItemStack)) return false;

		// search tile entity
		TileEntity currentEntity = par3World.getTileEntity (par4, par5, par6);

		// verify entity
		if (currentEntity == null || !(currentEntity instanceof ISurveillanceNetworkEntity)) {
			// notify player
			// TODO!!

			// forbid item use
			return false;
		}

		// store entity
		if (!par1ItemStack.getTagCompound ().hasKey ("entity")) {
			// create location
			NBTTagCompound location = new NBTTagCompound ();

			// add location
			location.setInteger ("x", par4);
			location.setInteger ("y", par5);
			location.setInteger ("z", par6);

			// store location
			par1ItemStack.getTagCompound ().setTag ("entity", location);

			// use up some damage value
			return true;
		} else {
			// get entity
			NBTTagCompound location = par1ItemStack.getTagCompound ().getCompoundTag ("entity");
			TileEntity previousEntity = par3World.getTileEntity (location.getInteger ("x"), location.getInteger ("y"), location.getInteger ("z"));

			// verify entities
			if (previousEntity == null || !(previousEntity instanceof ISurveillanceNetworkEntity)) {
				// notify player
				// TODO!!

				// forbid item use
				return false;
			}

			// connect entities
			if (currentEntity instanceof ISurveillanceNetworkHub) {
				// get hub instance
				ISurveillanceNetworkHub hubEntity = ((ISurveillanceNetworkHub) currentEntity);

				// disallow connection between two hubs
				if (previousEntity instanceof ISurveillanceNetworkHub) {
					// notify player
					// TODO!!

					// forbid item use
					return false;
				}

				// connect entity
				try {
					((ISurveillanceNetworkClient) previousEntity).connectHub (hubEntity);

					// use up some damage value
					return true;
				} catch (SurveillanceEntityConnectionException ex) {
					// notify user
					// TODO!!

					// forbid item use
					return false;
				}
			}

			// verify pairing of two entities
			if (previousEntity instanceof ISurveillanceNetworkClient) {
				// notify player
				// TODO!!

				// forbid item use
				return false;
			}

			// get hub instance
			ISurveillanceNetworkHub hubEntity = ((ISurveillanceNetworkHub) previousEntity);

			// connect entity
			try {
				((ISurveillanceNetworkClient) currentEntity).connectHub (hubEntity);

				// use up some damage value
				return true;
			} catch (SurveillanceEntityConnectionException ex) {
				// notify user
				// TODO!!

				// forbid item use
				return false;
			}
		}
	}
}