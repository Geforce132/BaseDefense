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
package org.evilco.mc.defense.client.renderer.entity.generic;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.evilco.mc.defense.client.model.generic.SecurityBotModel;

public class SecurityBotRenderer extends RenderLiving {

	/**
	 * Constructs a new SecurityBotRenderer instance.
	 */
	public SecurityBotRenderer () {
		super (new SecurityBotModel (), 0.5f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResourceLocation getEntityTexture (Entity var1) {
		return (new ResourceLocation ("defense", "textures/entities/generic/securityBot.png"));
	}
}