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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkAuthority;
import org.evilco.mc.defense.api.network.surveillance.ISurveillanceNetworkEntity;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkConnectionExistsException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkPermissionException;
import org.evilco.mc.defense.api.network.surveillance.exception.SurveillanceNetworkUnsupportedEntityException;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;
import org.evilco.mc.defense.common.item.DefenseItem;
import org.evilco.mc.defense.common.util.Location;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class WirelessTunerItem extends Item {

	/**
	 * Constructs a new WirelessTunerItem instance.
	 */
	public WirelessTunerItem () {
		super ();

		this.setMaxStackSize (1);
		this.setMaxDamage (20);
		this.setUnlocalizedName (DefenseNames.ITEM_GENERIC_WIRELESS_TUNER);
		this.setTextureName ("defense:generic/wirelessTuner");
		this.setCreativeTab (DefenseCreativeTab.GENERIC);
	}

	/**
	 * Connects two entities.
	 * @param entity1 The first entity.
	 * @param entity2 The second entity.
	 */
	protected void connect (EntityPlayer entityPlayer, ItemStack itemStack, ISurveillanceNetworkEntity entity1, ISurveillanceNetworkEntity entity2) {
		// verify connection
		if (!entity1.canConnect (entity2)) {
			// notify player
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_CANNOT_CONNECT));

			// skip further execution
			return;
		}

		// connect entities
		try {
			entity1.connect (entity2, false, true);

			// delete NBT
			itemStack.setTagCompound (null);

			// damage item
			if (!entityPlayer.capabilities.isCreativeMode) itemStack.damageItem (1, entityPlayer);

			// notify player
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_CONNECTION_SUCCESS));
		} catch (SurveillanceNetworkConnectionExistsException ex) {
			// Unused
		} catch (SurveillanceNetworkPermissionException ex) {
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_PERMISSION_DENIED));
		} catch (SurveillanceNetworkUnsupportedEntityException ex) {
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_UNSUPPORTED_CONNECTION));
		} catch (SurveillanceNetworkException ex) {
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_UNKNOWN_ERROR));
			ex.printStackTrace ();
		}
	}

	/**
	 * Disconnects two entities.
	 * @param entityPlayer The player.
	 * @param itemStack The item stack.
	 * @param entity The entity.
	 */
	protected void disconnect (EntityPlayer entityPlayer, ItemStack itemStack, ISurveillanceNetworkEntity entity) {
		try {
			// disconnect
			entity.disconnect (null, false, true);

			// damage item
			if (!entityPlayer.capabilities.isCreativeMode) itemStack.damageItem (1, entityPlayer);

			// notify player
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_DISCONNECT_SUCCESS));
		} catch (SurveillanceNetworkPermissionException ex) {
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_PERMISSION_DENIED));
		} catch (SurveillanceNetworkException ex) {
			entityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_UNKNOWN_ERROR));
			ex.printStackTrace ();
		}
	}

	/**
	 * Returns a stored entity.
	 * @param itemStack The item stack.
	 * @param world The world.
	 * @return The entity.
	 */
	public ISurveillanceNetworkEntity getStoredEntity (ItemStack itemStack, World world) {
		// check for empty tag
		if (itemStack.getTagCompound () == null) return null;

		// check type
		if (!itemStack.getTagCompound ().hasKey ("entityID")) {
			// read location
			Location location = Location.readFromNBT (itemStack.getTagCompound ());

			// get TileEntity
			TileEntity tileEntity = location.getTileEntity (world);

			// verify
			if (!(tileEntity instanceof ISurveillanceNetworkEntity)) {
				// reset NBT
				itemStack.setTagCompound (null);

				// reset empty
				return null;
			}

			// return correct TE
			return ((ISurveillanceNetworkEntity) tileEntity);
		}

		// get Entity
		Entity entity = world.getEntityByID (itemStack.getTagCompound ().getInteger ("entityID"));

		// verify
		if (!(entity instanceof ISurveillanceNetworkEntity)) {
			// reset NBT
			itemStack.setTagCompound (null);

			// reset empty
			return null;
		}

		// return correct entity
		return ((ISurveillanceNetworkEntity) entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onItemUse (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// skip client execution
		if (par3World.isRemote) return true;

		// get TileEntity
		TileEntity entity = par3World.getTileEntity (par4, par5, par6);

		// check type
		if (!(entity instanceof ISurveillanceNetworkEntity)) {
			// notify player
			par2EntityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_INVALID_ENTITY));

			// skip further execution
			return true;
		}

		// cast entity
		ISurveillanceNetworkEntity entity1 = ((ISurveillanceNetworkEntity) entity);

		// check for previous connections
		if (entity1.isConnected () && !(entity1 instanceof ISurveillanceNetworkAuthority)) {
			// disconnect
			this.disconnect (par2EntityPlayer, par1ItemStack, entity1);

			// skip execution
			return true;
		}

		// get previous entity
		ISurveillanceNetworkEntity entity2 = this.getStoredEntity (par1ItemStack, par3World);

		// store data if none found
		if (entity2 == null) {
			// create NBT tag
			NBTTagCompound compound = new NBTTagCompound ();

			// create location
			Location location = new Location (par4, par5, par6);

			// write to NBT
			location.writeToNBT (compound);

			// store in NBT
			par1ItemStack.setTagCompound (compound);

			// damage item
			if (!par2EntityPlayer.capabilities.isCreativeMode) par1ItemStack.damageItem (1, par2EntityPlayer);

			// notify player
			par2EntityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_DATA_STORED));

			// skip execution
			return true;
		}

		// connect
		this.connect (par2EntityPlayer, par1ItemStack, entity1, entity2);

		// ok!
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean itemInteractionForEntity (ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {
		// skip client execution
		if (par2EntityPlayer.getEntityWorld ().isRemote) return true;

		// check type
		if (!(par3EntityLivingBase instanceof ISurveillanceNetworkEntity)) {
			// notify player
			par2EntityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_INVALID_ENTITY));

			// skip further execution
			return true;
		}

		// check sneak - click
		if (par2EntityPlayer.isSneaking ()) {
			// kill entity
			par3EntityLivingBase.setDead ();

			// drop item
			par3EntityLivingBase.dropItem (DefenseItem.GENERIC_SECURITY_BOT, 1);

			// damage item
			par1ItemStack.damageItem (1, par2EntityPlayer);

			// abort further execution
			return true;
		}

		// cast entity
		ISurveillanceNetworkEntity entity1 = ((ISurveillanceNetworkEntity) par3EntityLivingBase);

		// check for previous connections
		if (entity1.isConnected () && !(entity1 instanceof ISurveillanceNetworkAuthority)) {
			// disconnect
			this.disconnect (par2EntityPlayer, par1ItemStack, entity1);

			// skip execution
			return true;
		}

		// get stored entity
		ISurveillanceNetworkEntity entity2 = this.getStoredEntity (par1ItemStack, par2EntityPlayer.getEntityWorld ());

		// store entity
		if (entity2 == null) {
			// create NBT Tag
			NBTTagCompound compound = new NBTTagCompound ();

			// store entityID
			compound.setInteger ("entityID", par3EntityLivingBase.getEntityId ());

			// store entity
			par1ItemStack.setTagCompound (compound);

			// damage item
			if (!par2EntityPlayer.capabilities.isCreativeMode) par1ItemStack.damageItem (1, par2EntityPlayer);

			// notify player
			par2EntityPlayer.addChatComponentMessage (new ChatComponentTranslation (DefenseNames.TRANSLATION_GENERIC_WIRELESS_TUNER_DATA_STORED));

			// skip execution
			return true;
		}

		// connect
		this.connect (par2EntityPlayer, par1ItemStack, entity1, entity2);

		// ok!
		return true;
	}
}