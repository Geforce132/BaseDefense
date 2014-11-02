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
package org.evilco.forge.defense.common.shadow.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.evilco.forge.defense.common.shadow.ShadowString;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowResistancePotion extends Potion {

	/**
	 * Defines the potion icon sheet location.
	 */
	public static final ResourceLocation ICON_SHEET = new ResourceLocation ("defense", "textures/gui/potion.png");

	/**
	 * Constructs a new ShadowResistancePotion instance.
	 * @param p_i1573_1_ The potion.
	 */
	public ShadowResistancePotion (int p_i1573_1_) {
		super (p_i1573_1_, false, 0x0C0346);

		this.setPotionName (ShadowString.POTION_NAME_SHADOW_RESISTANCE);
		this.setIconIndex (0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasStatusIcon () {
		// FIXME: Dirty workaround - Thanks forge!
		Minecraft.getMinecraft ().renderEngine.bindTexture (ICON_SHEET);

		// continue
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performEffect (EntityLivingBase p_76394_1_, int p_76394_2_) {
		super.performEffect (p_76394_1_, p_76394_2_);

		// TODO: Protect the entity from any dark effects
	}
}
