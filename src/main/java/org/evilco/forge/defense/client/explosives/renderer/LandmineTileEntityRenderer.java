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
package org.evilco.forge.defense.client.explosives.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class LandmineTileEntityRenderer extends TileEntitySpecialRenderer {

	/**
	 * Stores the model instance.
	 */
	private final IModelCustom model;

	/**
	 * Constructs a new LandmineTileEntityRenderer instance.
	 */
	public LandmineTileEntityRenderer () {
		super ();
		this.model = AdvancedModelLoader.loadModel (new ResourceLocation ("defense:model/explosives/LandMine.obj"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderTileEntityAt (TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		// push matrix and translate
		GL11.glPushMatrix ();
		GL11.glTranslated (p_147500_2_, p_147500_4_, p_147500_6_);

		// bind texture
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation ("minecraft", "textures/blocks/iron_block.png"));

		// render model
		this.model.renderAll ();

		// pop matrix
		GL11.glPopMatrix ();
	}
}
