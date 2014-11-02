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

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.evilco.forge.defense.common.explosives.block.ExplosivesBlock;
import org.evilco.forge.defense.common.explosives.entity.PrimedExplosiveEntity;
import org.lwjgl.opengl.GL11;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class PrimedExplosiveEntityRenderer extends Render {

	/**
	 * Stores the block renderer.
	 */
	private RenderBlocks blockRenderer = new RenderBlocks ();

	/**
	 * Stores the block.
	 */
	private final Block block;

	/**
	 * Constructs a new PrimedExplosiveEntityRenderer instance.
	 * @param block The block.
	 */
	public PrimedExplosiveEntityRenderer (Block block) {
		super ();

		this.block = block;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doRender (Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix ();

		// cast entity
		PrimedExplosiveEntity entity = ((PrimedExplosiveEntity) p_76986_1_);

		// translate
		GL11.glTranslatef (((float) p_76986_2_), ((float) p_76986_4_), ((float) p_76986_6_));

		// scale on last few ticks
		if ((entity.getFuse () - p_76986_9_ + 1.0f) < 10.0f) {
			float f2 = 1.0F - ((float) entity.getFuse () - p_76986_9_ + 1.0f) / 10.0f;

			if (f2 < 0.0F) f2 = 0.0f;
			if (f2 > 1.0F) f2 = 1.0f;

			f2 *= f2;
			f2 *= f2;
			float f3 = 1.0f + f2 * 0.3f;
			GL11.glScalef (f3, f3, f3);
		}

		// render
		if ((entity.getFuse () / 5 % 2) == 0) {
			// setup OpenGL
			GL11.glDisable (GL11.GL_TEXTURE_2D);
			GL11.glDisable (GL11.GL_LIGHTING);
			GL11.glEnable (GL11.GL_BLEND);
			GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			GL11.glColor4f (1.0f, 1.0f, 1.0f, 1.0f);

			// render entity
			this.blockRenderer.renderBlockAsItem (this.block, 0, 1.0F);

			// reset OpenGL
			GL11.glColor4f (1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable (GL11.GL_BLEND);
			GL11.glEnable (GL11.GL_LIGHTING);
			GL11.glEnable (GL11.GL_TEXTURE_2D);
		} else {
			this.bindEntityTexture (p_76986_1_);
			this.blockRenderer.renderBlockAsItem (this.block, 0, p_76986_1_.getBrightness (p_76986_9_));
		}

		// pop matrix
		GL11.glPopMatrix ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResourceLocation getEntityTexture (Entity p_110775_1_) {
		return TextureMap.locationBlocksTexture;
	}
}
