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
package org.evilco.forge.defense.common.explosives.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.evilco.forge.defense.api.explosive.IExplosiveContainer;
import org.evilco.forge.defense.common.DefenseCreativeTab;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class LandmineBlock extends Block implements IExplosiveContainer, ITileEntityProvider {

	/**
	 * Defines the block name.
	 */
	public static final String NAME = "landmine";

	/**
	 * Constructs a new LandmineBlock instance.
	 */
	protected LandmineBlock () {
		super (Material.tnt);

		this.setCreativeTab (DefenseCreativeTab.EXPLOSIVES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TileEntity createNewTileEntity (World p_149915_1_, int p_149915_2_) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spawnExplositve (World world, double x, double y, double z) {
		// cancel on client side
		if (world.isRemote) return;
	}
}
