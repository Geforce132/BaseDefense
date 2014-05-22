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
package org.evilco.defense.common;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.evilco.defense.DefenseMod;
import org.evilco.defense.common.entity.SecurityBotEntity;
import org.evilco.defense.common.tile.generic.DefenseStationTileEntity;
import org.evilco.defense.common.tile.surveillance.SurveillanceCameraTileEntity;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CommonProxy {


	/**
	 * Registers all modification blocks.
	 */
	public void registerBlocks () {
		GameRegistry.registerBlock (DefenseBlock.DEFENSE_STATION, "defense_station_block");
		GameRegistry.registerBlock (DefenseBlock.BARBED_WIRE_FENCE, "barbed_wire_fence");

		GameRegistry.registerBlock (DefenseBlock.SURVEILLANCE_CAMERA, "surveillance_camera_block");
	}

	/**
	 * Registers all modification entities.
	 */
	public void registerEntities () {
		// get unique identifiers
		int securityBotID = EntityRegistry.findGlobalUniqueEntityId (); // TODO: This will probably get removed in future versions of MC

		// register security bot
		EntityRegistry.registerGlobalEntityID (SecurityBotEntity.class, "securityBot", securityBotID);
		EntityRegistry.registerModEntity (SecurityBotEntity.class, "securityBot", securityBotID, DefenseMod.instance, 64, 1, true);
		// EntityList.entityEggs.put (securityBotID, new EntityList.EntityEggInfo (securityBotID, 16777215, 16711680));
	}

	/**
	 * Registers all modification items.
	 */
	public void registerItems () {
		GameRegistry.registerItem (DefenseItem.GENERIC_AI_PROCESSOR, "ai_processor");
		GameRegistry.registerItem (DefenseItem.GENERIC_DEFENSE_STATION, "defense_station");
		GameRegistry.registerItem (DefenseItem.GENERIC_LENS, "lens");
		GameRegistry.registerItem (DefenseItem.GENERIC_WIRELESS_TUNER, "wireless_tuner");

		GameRegistry.registerItem (DefenseItem.SURVEILLANCE_CAMERA, "surveillance_camera");
		GameRegistry.registerItem (DefenseItem.SURVEILLANCE_SECURITY_BOT, "surveillance_security_bot");
	}

	/**
	 * Registers all recipes.
	 */
	public void registerRecipes () {
		// lenses
		GameRegistry.addShapedRecipe (
			new ItemStack (DefenseItem.GENERIC_LENS, 3),
			"X X",
			" X ",
			"   ",
			'X', Blocks.glass
		);

		// camera
		GameRegistry.addShapedRecipe (
			new ItemStack (DefenseItem.SURVEILLANCE_CAMERA),
			"XXX",
			"XYZ",
			"XXX",
			'X', Blocks.iron_block,
			'Y', DefenseItem.GENERIC_WIRELESS_TUNER,
			'Z', DefenseItem.GENERIC_LENS
		);

		// camera (mob enabled)
		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.SURVEILLANCE_CAMERA, 1, 1),
			new ItemStack (DefenseItem.SURVEILLANCE_CAMERA, 1, 0),
			new ItemStack (Items.bone)
		);

		// camera (normal)
		GameRegistry.addShapelessRecipe (
			new ItemStack (DefenseItem.SURVEILLANCE_CAMERA, 1, 0),
			new ItemStack (DefenseItem.SURVEILLANCE_CAMERA, 1, 1)
		);

		// wireless tuner
		GameRegistry.addShapedRecipe (
			new ItemStack (DefenseItem.GENERIC_WIRELESS_TUNER),
			" Y ",
			"XZX",
			"XXX",
			'Y', Items.stick,
			'X', Items.iron_ingot,
			'Z', Items.ender_pearl
		);

		// ai processor
		GameRegistry.addShapedRecipe (
			new ItemStack (DefenseItem.GENERIC_AI_PROCESSOR),
			"ZXZ",
			"XYX",
			"ZXZ",
			'X', Items.iron_ingot,
			'Y', Items.redstone,
			'Z', Items.stick
		);

		// security bot
		GameRegistry.addShapedRecipe (
			new ItemStack (DefenseItem.SURVEILLANCE_SECURITY_BOT),
			"XWX",
			"XYX",
			"XZX",
			'W', DefenseItem.GENERIC_WIRELESS_TUNER,
			'X', Blocks.iron_block,
			'Y', DefenseItem.GENERIC_AI_PROCESSOR,
			'Z', Items.minecart
		);

		// defense station
		GameRegistry.addShapedRecipe (
			new ItemStack (DefenseItem.GENERIC_DEFENSE_STATION),
			"XZX",
			"XYX",
			"XXX",
			'X', Blocks.iron_block,
			'Y', DefenseItem.GENERIC_AI_PROCESSOR,
			'Z', DefenseItem.GENERIC_WIRELESS_TUNER
		);
	}

	/**
	 * Registers all renderers.
	 */
	public void registerRenderers () { }

	/**
	 * Registers all tile entities.
	 */
	public void registerTileEntities () {
		GameRegistry.registerTileEntity (DefenseStationTileEntity.class, "defenseGenericDefenseStation");

		GameRegistry.registerTileEntity (SurveillanceCameraTileEntity.class, "defenseSurveillanceCamera");
	}
}