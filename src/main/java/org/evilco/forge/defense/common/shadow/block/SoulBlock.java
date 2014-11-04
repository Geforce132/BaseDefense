/*
 * Copyright 2014 Johannes Donath <johannesd@evil-co.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.evilco.forge.defense.common.shadow.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.evilco.forge.defense.common.DefenseCreativeTab;
import org.evilco.forge.defense.common.shadow.ShadowString;

import java.util.List;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class SoulBlock extends Block {

	/**
	 * Defines the block name.
	 */
	public static final String NAME = "soul_block";

	/**
	 * Stores the regular block texture.
	 */
	private IIcon iconRegular;

	/**
	 * Stores the smooth block texture.
	 */
	private IIcon iconSmooth;

	/**
	 * Stores the brick block texture.
	 */
	private IIcon iconBrick;

	/**
	 * Stores the fancy brick block texture.
	 */
	private IIcon iconBrickFancy;

	/**
	 * Constructs a new SoulBlock instance.
	 */
	public SoulBlock () {
		super (Material.rock);

		this.setBlockName (ShadowString.BLOCK_NAME_SOUL);
		this.setCreativeTab (DefenseCreativeTab.SHADOW);
		this.setHardness (1.5f);
		this.setHarvestLevel ("pickaxe", 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int damageDropped (int p_149692_1_) {
		if (p_149692_1_ == 1) return 0;
		return p_149692_1_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getSubBlocks (Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		super.getSubBlocks (p_149666_1_, p_149666_2_, p_149666_3_);

		p_149666_3_.add (new ItemStack (p_149666_1_, 1, 1));
		p_149666_3_.add (new ItemStack (p_149666_1_, 1, 2));
		p_149666_3_.add (new ItemStack (p_149666_1_, 1, 3));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IIcon getIcon (int p_149691_1_, int p_149691_2_) {
		switch (p_149691_2_) {
			case 0:
			default:
				return this.iconRegular;
			case 1:
				return this.iconSmooth;
			case 2:
				return this.iconBrick;
			case 3:
				return this.iconBrickFancy;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBlockIcons (IIconRegister p_149651_1_) {
		super.registerBlockIcons (p_149651_1_);

		this.iconRegular = p_149651_1_.registerIcon ("defense:shadow/soulStone");
		this.iconSmooth = p_149651_1_.registerIcon ("defense:shadow/soulSmoothStone");
		this.iconBrick = p_149651_1_.registerIcon ("defense:shadow/soulBrick");
		this.iconBrickFancy = p_149651_1_.registerIcon ("defense:shadow/soulFancyBrick");
	}
}
