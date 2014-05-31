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
package org.evilco.mc.defense.common.block.generic;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.evilco.defense.common.DefenseCreativeTabs;
import org.evilco.mc.defense.common.DefenseNames;
import org.evilco.mc.defense.common.gui.creative.DefenseCreativeTab;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class BarbedWirePaneBlock extends BlockPane {

	/**
	 * Constructs a new BarbedWirePanelBlock instance.
	 */
	public BarbedWirePaneBlock () {
		super ("defense:generic/barbedWire", "defense:generic/barbedWire", Material.iron, true);

		this.setBlockName (DefenseNames.BLOCK_GENERIC_BARBED_WIRE_FENCE);
		this.setCreativeTab (DefenseCreativeTab.GENERIC); // TODO: Move block to generic tab
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEntityCollidedWithBlock (World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
		super.onEntityCollidedWithBlock (p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);

		p_149670_5_.attackEntityFrom (DamageSource.generic, 1.0f);
	}
}