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

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import org.evilco.forge.defense.common.shadow.ShadowFluids;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowFluidBlock extends BlockFluidClassic {

	/**
	 * Defines the block name.
	 */
	public static final String NAME = "shadow_fluid";

	/**
	 * Stores the flowing fluid texture.
	 */
	private IIcon iconFlowing;

	/**
	 * Stores the still fluid texture.
	 */
	private IIcon iconStill;

	/**
	 * Constructs a new ShadowFluidBlock instance.
	 */
	public ShadowFluidBlock () {
		super (ShadowFluids.SHADOW, Material.water);

		// set block properties
		this.setHardness (100.0f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canDisplace (IBlockAccess world, int x, int y, int z) {
		if (world.getBlock (x, y, z).getMaterial ().isLiquid ()) return false;
		return super.canDisplace (world, x, y, z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean displaceIfPossible (World world, int x, int y, int z) {
		if (world.getBlock (x, y, z).getMaterial ().isLiquid ()) return false;
		return super.displaceIfPossible (world, x, y, z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IIcon getIcon (int p_149691_1_, int p_149691_2_) {
		return ((p_149691_1_ == 0 || p_149691_1_ == 1) ? this.iconStill : this.iconFlowing);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerIcons (IIconRegister p_149651_1_) {
		super.registerIcons (p_149651_1_);

		this.iconFlowing = p_149651_1_.registerIcon ("defense:shadow/shadowFlowing");
		this.iconStill = p_149651_1_.registerIcon ("defense:shadow/shadowStill");
	}
}
