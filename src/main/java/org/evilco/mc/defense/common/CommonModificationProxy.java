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
package org.evilco.mc.defense.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import org.evilco.mc.defense.DefenseModification;
import org.evilco.mc.defense.common.block.DefenseBlock;
import org.evilco.mc.defense.IModificationProxy;
import org.evilco.mc.defense.common.event.CapeHandler;
import org.evilco.mc.defense.common.event.CraftingHandler;
import org.evilco.mc.defense.common.gui.DefenseGuiHandler;
import org.evilco.mc.defense.common.item.DefenseItem;
import org.evilco.mc.defense.common.network.DefenseChannelHandler;
import org.evilco.mc.defense.common.tile.generic.DefenseStationTileEntity;
import org.evilco.mc.defense.common.tile.trigger.MotionDetectorTileEntity;

import java.util.EnumMap;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CommonModificationProxy implements IModificationProxy {

	/**
	 * Defines the network channel name.
	 */
	public static final String CHANNEL_NAME = "defense";

	/**
	 * Stores the network configuration.
	 */
	protected EnumMap<Side, FMLEmbeddedChannel> channels = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		this.registerBlocks ();
		this.registerEntities ();
		this.registerItems ();
		this.registerTileEntities ();

		this.registerRecipes ();
		this.registerEvents ();
		this.registerDungeonLoot ();

		// register GUI handler
		NetworkRegistry.INSTANCE.registerGuiHandler (DefenseModification.getInstance (), new DefenseGuiHandler ());

		// register network channel
		this.channels = NetworkRegistry.INSTANCE.newChannel (CHANNEL_NAME, new DefenseChannelHandler ());
	}

	/**
	 * Returns all registered network channels.
	 * @return The channel map.
	 */
	@Override
	public EnumMap<Side, FMLEmbeddedChannel> getChannels () {
		return this.channels;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preInitialize () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postInitialize () { }

	/**
	 * Registers all modification blocks.
	 */
	public void registerBlocks () {
		GameRegistry.registerBlock (DefenseBlock.GENERIC_BARBED_WIRE_PANE, DefenseNames.REGISTRATION_BLOCK_GENERIC_BARBED_WIRE_FENCE);
		GameRegistry.registerBlock (DefenseBlock.GENERIC_DEFENSE_STATION, DefenseNames.REGISTRATION_BLOCK_GENERIC_DEFENSE_STATION);
		GameRegistry.registerBlock (DefenseBlock.TRIGGER_MOTION_DETECTOR, DefenseNames.REGISTRATION_BLOCK_TRIGGER_MOTION_DETECTOR);
	}

	/**
	 * Registers all dungeon loot items.
	 */
	public void registerDungeonLoot () {
		ChestGenHooks.getInfo (ChestGenHooks.DUNGEON_CHEST).addItem (new WeightedRandomChestContent (new ItemStack (DefenseItem.GENERIC_SANDPAPER), 1, 1, 50));
		ChestGenHooks.getInfo (ChestGenHooks.MINESHAFT_CORRIDOR).addItem (new WeightedRandomChestContent (new ItemStack (DefenseItem.GENERIC_SANDPAPER), 1, 1, 50));
		ChestGenHooks.getInfo (ChestGenHooks.STRONGHOLD_CORRIDOR).addItem (new WeightedRandomChestContent (new ItemStack (DefenseItem.GENERIC_SANDPAPER_DIAMOND), 1, 1, 10));
		ChestGenHooks.getInfo (ChestGenHooks.VILLAGE_BLACKSMITH).addItem (new WeightedRandomChestContent (new ItemStack (DefenseItem.GENERIC_SANDPAPER), 1, 1, 70));
		ChestGenHooks.getInfo (ChestGenHooks.VILLAGE_BLACKSMITH).addItem (new WeightedRandomChestContent (new ItemStack (DefenseItem.GENERIC_SANDPAPER_DIAMOND), 1, 1, 2));
		ChestGenHooks.getInfo (ChestGenHooks.VILLAGE_BLACKSMITH).addItem (new WeightedRandomChestContent (new ItemStack (DefenseBlock.GENERIC_BARBED_WIRE_PANE), 1, 1, 50));

		// add easter egg
		ItemStack easterEgg = new ItemStack (Items.egg);
		easterEgg.setStackDisplayName ("Easter Egg");

		ChestGenHooks.getInfo (ChestGenHooks.DUNGEON_CHEST).addItem (new WeightedRandomChestContent (easterEgg, 1, 1, 5));
		ChestGenHooks.getInfo (ChestGenHooks.MINESHAFT_CORRIDOR).addItem (new WeightedRandomChestContent (easterEgg, 1, 1, 5));
		ChestGenHooks.getInfo (ChestGenHooks.STRONGHOLD_CORRIDOR).addItem (new WeightedRandomChestContent (easterEgg, 1, 1, 5));
	}

	/**
	 * Registers all modification entities.
	 */
	public void registerEntities () {

	}

	/**
	 * Registers all modification event handlers.
	 */
	public void registerEvents () {
		MinecraftForge.EVENT_BUS.register (new CapeHandler ());
	}

	/**
	 * Registers all modification items.
	 */
	public void registerItems () {
		GameRegistry.registerItem (DefenseItem.GENERIC_DEFENSE_STATION, DefenseNames.REGISTRATION_ITEM_GENERIC_DEFENSE_STATION);
		GameRegistry.registerItem (DefenseItem.GENERIC_SANDPAPER, DefenseNames.REGISTRATION_ITEM_GENERIC_SANDPAPER);
		GameRegistry.registerItem (DefenseItem.GENERIC_SANDPAPER_DIAMOND, DefenseNames.REGISTRATION_ITEM_GENERIC_SANDPAPER_DIAMOND);
		GameRegistry.registerItem (DefenseItem.GENERIC_LENS, DefenseNames.REGISTRATION_ITEM_GENERIC_LENS);
		GameRegistry.registerItem (DefenseItem.GENERIC_LENS_FISHEYE, DefenseNames.REGISTRATION_ITEM_GENERIC_LENS_FISHEYE);
		GameRegistry.registerItem (DefenseItem.GENERIC_WIRELESS_TUNER, DefenseNames.REGISTRATION_ITEM_GENERIC_WIRELESS_TUNER);

		GameRegistry.registerItem (DefenseItem.TRIGGER_MOTION_DETECTOR, DefenseNames.REGISTRATION_ITEM_TRIGGER_MOTION_DETECTOR);
	}

	/**
	 * Registers all modification recipes.
	 */
	public void registerRecipes () {
		// sand + paper -> sandpaper
		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1),
			Items.paper,
			Blocks.sand
		);

		// sand + paper + diamond -> diamond sandpaper
		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_SANDPAPER_DIAMOND, 1),
			Items.paper,
			Blocks.sand,
			Items.diamond
		);

		// sandpaper + diamond -> diamond sandpaper
		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_SANDPAPER_DIAMOND, 1),
			DefenseItem.GENERIC_SANDPAPER,
			Items.diamond
		);

		// sandpaper + glass -> lens
		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 1),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			Blocks.glass_pane
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 10),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 1)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 20),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 10)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 30),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 20)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 40),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 30)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 50),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 40)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 80),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 50),
			Items.water_bucket
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 99),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS, 1, 80),
			Items.water_bucket
		);

		// sandpaper + glass -> fisheye lens
		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 1),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			Blocks.glass
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 10),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 1)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 20),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 10)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 30),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 20)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 40),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 30)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 50),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 40)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 60),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 50)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 70),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 60)
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 80),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 70),
			Items.water_bucket
		);

		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 100),
			new ItemStack (DefenseItem.GENERIC_SANDPAPER, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack (DefenseItem.GENERIC_LENS_FISHEYE, 1, 80),
			Items.water_bucket
		);
	}

	/**
	 * Registers all modification tile entities.
	 */
	public void registerTileEntities () {
		GameRegistry.registerTileEntity (DefenseStationTileEntity.class, DefenseNames.REGISTRATION_TILE_ENTITY_GENERIC_DEFENSE_STATION);
		GameRegistry.registerTileEntity (MotionDetectorTileEntity.class, DefenseNames.REGISTRATION_TILE_ENTITY_TRIGGER_MOTION_DETECTOR);
	}
}