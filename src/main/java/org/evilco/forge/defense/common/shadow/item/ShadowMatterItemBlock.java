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
package org.evilco.forge.defense.common.shadow.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.evilco.forge.defense.common.shadow.ShadowString;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowMatterItemBlock extends ItemBlock {

	/**
	 * Constructs a new ShadowMatterItemBlock instance.
	 * @param p_i45328_1_ The parent block.
	 */
	public ShadowMatterItemBlock (Block p_i45328_1_) {
		super (p_i45328_1_);

		this.setUnlocalizedName (ShadowString.BLOCK_NAME_SHADOW_MATTER);
		this.setHasSubtypes (true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMetadata (int p_77647_1_) {
		return p_77647_1_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUnlocalizedName (ItemStack p_77667_1_) {
		return super.getUnlocalizedName (p_77667_1_) + "." + p_77667_1_.getMetadata ();
	}
}
