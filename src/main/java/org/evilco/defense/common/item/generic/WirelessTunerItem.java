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

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.defense.common.Strings;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkClient;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkEntity;
import org.evilco.defense.common.tile.network.ISurveillanceNetworkHub;
import org.evilco.defense.common.tile.network.SurveillanceEntityConnectionException;
import org.evilco.defense.util.Location;

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
	 * Connects two entities.
	 * @param player The player.
	 * @param entity1 The first entity.
	 * @param entity2 The second entity.
	 * @return True if the connection was established successfully.
	 */
	public boolean connectEntities (EntityPlayer player, ISurveillanceNetworkEntity entity1, ISurveillanceNetworkEntity entity2) {
		// connect entities
		if (entity2 instanceof ISurveillanceNetworkHub) {
			// get hub instance
			ISurveillanceNetworkHub hubEntity = ((ISurveillanceNetworkHub) entity2);

			// disallow connection between two hubs
			if (entity1 instanceof ISurveillanceNetworkHub) {
				// notify player
				player.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.hubConnectionUnsupported"));

				// forbid item use
				return false;
			}

			// connect entity
			try {
				((ISurveillanceNetworkClient) entity1).connectHub (hubEntity);

				// notify user
				player.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.connectionSuccessful"));

				// use up some damage value
				return true;
			} catch (SurveillanceEntityConnectionException ex) {
				// notify user
				player.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.unknownConnectionError", ex.getMessage ()));

				// forbid item use
				return false;
			}
		}

		// verify pairing of two entities
		if (entity1 instanceof ISurveillanceNetworkClient) {
			// notify player
			player.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.entityConnectionUnsupported"));

			// forbid item use
			return false;
		}

		// get hub instance
		ISurveillanceNetworkHub hubEntity = ((ISurveillanceNetworkHub) entity1);

		// connect entity
		try {
			((ISurveillanceNetworkClient) entity2).connectHub (hubEntity);

			// notify user
			player.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.connectionSuccessful"));

			// use up some damage value
			return true;
		} catch (SurveillanceEntityConnectionException ex) {
			// notify user
			player.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.unknownConnectionError", new ChatComponentTranslation (ex.getMessage ())));

			// forbid item use
			return false;
		}
	}

	/**
	 * Returns the surveillance entity of the specified item stack.
	 * @param world The world the player is in.
	 * @param par1ItemStack The item stack.
	 * @return The surveillance entity.
	 */
	public ISurveillanceNetworkEntity getSurveillanceEntity (World world, ItemStack par1ItemStack) {
		// check for NBT
		if (!this.hasSurveillanceEntity (par1ItemStack)) return null;

		// find TileEntity
		if (par1ItemStack.getTagCompound ().hasKey ("entity")) {
			// get location
			Location location = Location.readFromNBT (par1ItemStack.getTagCompound ().getCompoundTag ("entity"));

			// get entity
			TileEntity tileEntity = location.getTileEntity (world);

			// verify entity
			if (tileEntity == null || !(tileEntity instanceof ISurveillanceNetworkEntity)) return null;

			// return finished entity
			return ((ISurveillanceNetworkEntity) tileEntity);
		}

		// find Entity
		Entity entity = world.getEntityByID (par1ItemStack.getTagCompound ().getInteger ("entityID"));

		// verify entity
		if (entity == null || !(entity instanceof ISurveillanceNetworkEntity)) return null;

		// return finished entity
		return ((ISurveillanceNetworkEntity) entity);
	}

	/**
	 * Checks whether the supplied item stack does have an entity stored.
	 * @param par1ItemStack The item stack.
	 * @return True if there is an entity attached.
	 */
	public boolean hasSurveillanceEntity (ItemStack par1ItemStack) {
		return (par1ItemStack.getTagCompound () != null && (par1ItemStack.getTagCompound ().hasKey ("entity") || par1ItemStack.getTagCompound ().hasKey ("entityID")));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client version
		if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT) return false;

		// check whether players may edit the block
		if (!par2EntityPlayer.canPlayerEdit (par4, par5, par6, par7, par1ItemStack)) {
			// notify player
			par2EntityPlayer.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.permissionDenied"));

			// abort use
			return false;
		}

		// get location
		Location location = new Location (par4, par5, par6);

		// verify entity
		if (location.getTileEntity (par3World) == null || !(location.getTileEntity (par3World) instanceof ISurveillanceNetworkEntity)) {
			// notify player
			par2EntityPlayer.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.notAnEntity"));

			// abort use
			return false;
		}

		// check current state
		if (!this.hasSurveillanceEntity (par1ItemStack)) {
			// create new NBT tag
			NBTTagCompound itemTagCompound = new NBTTagCompound ();

			// create location NBT
			NBTTagCompound entityTag = new NBTTagCompound ();
			location.writeToNBT (entityTag);

			// append tag
			itemTagCompound.setTag ("entity",entityTag);

			// set tag
			par1ItemStack.setTagCompound (itemTagCompound);

			// notify player
			par2EntityPlayer.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.storedEntity"));

			// use
			return true;
		}

		// get previous entity
		ISurveillanceNetworkEntity previousEntity = this.getSurveillanceEntity (par3World, par1ItemStack);

		// verify entity
		if (previousEntity == null) {
			// notify player
			par2EntityPlayer.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.brokenEntity"));

			// reset NBT
			par1ItemStack.setTagCompound (null);

			// abort use
			return false;
		}

		// connect entities
		if (this.connectEntities (par2EntityPlayer, previousEntity, ((ISurveillanceNetworkEntity) location.getTileEntity (par3World)))) {
			// damage item
			par1ItemStack.damageItem (1, par2EntityPlayer);

			// reset NBT
			par1ItemStack.setTagCompound (null);

			// confirm use
			return true;
		} else {
			// reset NBT
			par1ItemStack.setTagCompound (null);

			// abort use
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean itemInteractionForEntity (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase currentEntity) {
		// skip client version
		if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT) return false;

		// verify entity
		if (!(currentEntity instanceof ISurveillanceNetworkEntity)) {
			// notify player
			par2EntityPlayer.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.notAnEntity"));

			// abort use
			return false;
		}

		// check current state
		if (!this.hasSurveillanceEntity (par1ItemStack)) {
			// create new NBT tag
			NBTTagCompound itemTagCompound = new NBTTagCompound ();

			// append tag
			itemTagCompound.setInteger ("entityID", currentEntity.getEntityId ());

			// set tag
			par1ItemStack.setTagCompound (itemTagCompound);

			// notify player
			par2EntityPlayer.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.storedEntity"));

			// use
			return true;
		}

		// get previous entity
		ISurveillanceNetworkEntity previousEntity = this.getSurveillanceEntity (par2EntityPlayer.getEntityWorld (), par1ItemStack);

		// verify entity
		if (previousEntity == null) {
			// notify player
			par2EntityPlayer.addChatMessage (new ChatComponentTranslation ("defense.surveillance.tuner.brokenEntity"));

			// reset NBT
			par1ItemStack.setTagCompound (null);

			// abort use
			return false;
		}

		// connect entities
		if (this.connectEntities (par2EntityPlayer, previousEntity, ((ISurveillanceNetworkEntity) currentEntity))) {
			// damage item
			par1ItemStack.damageItem (1, par2EntityPlayer);

			// reset NBT
			par1ItemStack.setTagCompound (null);

			// confirm use
			return true;
		} else {
			// reset NBT
			par1ItemStack.setTagCompound (null);

			// abort use
			return false;
		}
	}
}