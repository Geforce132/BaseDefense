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
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.evilco.forge.defense.common.shadow.ShadowString;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowResistancePotion extends Potion {

	/**
	 * Defines the icon sheet location.
	 */
	private static final ResourceLocation ICON_SHEET_LOCATION = new ResourceLocation ("defense", "textures/gui/potion.png");

	/**
	 * Constructs a new ShadowResistancePotion.
	 * @param p_i1573_1_ The potion identifier.
	 */
	public ShadowResistancePotion (int p_i1573_1_) {
		super (p_i1573_1_, false, 0x090B3A);

		this.setPotionName (ShadowString.POTION_NAME_SHADOW_RESISTANCE);
		this.setIconIndex (0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasStatusIcon () {
		// FIXME: This is a dirty workaround - Thanks forge!
		Minecraft.getMinecraft ().renderEngine.bindTexture (ICON_SHEET_LOCATION);

		// continue per usual
		return true;
	}
}
