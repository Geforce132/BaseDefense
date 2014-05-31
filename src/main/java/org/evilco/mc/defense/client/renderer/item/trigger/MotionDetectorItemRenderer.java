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
package org.evilco.mc.defense.client.renderer.item.trigger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.evilco.mc.defense.client.model.trigger.MotionDetectorModel;
import org.lwjgl.opengl.GL11;

public class MotionDetectorItemRenderer implements IItemRenderer {

	/**
	 * Stores the model.
	 */
	protected static final MotionDetectorModel model = new MotionDetectorModel ();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean handleRenderType (ItemStack item, ItemRenderType type) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldUseRenderHelper (ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderItem (ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix ();

		// render model
		if (type == ItemRenderType.INVENTORY)
			this.renderInventoryItem (item, data);
		else if (type == ItemRenderType.ENTITY)
			this.renderDroppedItem (item, data);
		else
			this.renderEquippedItem (type, item, data);

		GL11.glPopMatrix ();
	}

	/**
	 * Renders a dropped item.
	 * @param item The item.
	 * @param data The data.
	 */
	public void renderDroppedItem (ItemStack item, Object[] data) {
		// correct position
		GL11.glTranslatef (0.0f, 1.0f, 0.0f);
		GL11.glScalef (0.8f, 0.8f, 0.8f);
		GL11.glRotatef (180.0f, 1.0f, 0.0f, 0.0f);

		// bind texture
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation ("defense", "textures/blocks/trigger/motionDetector.png"));

		// render model
		model.render (null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
	}

	/**
	 * Renders an inventory item.
	 * @param type The type.
	 * @param item The item.
	 * @param data The data.
	 */
	public void renderEquippedItem (ItemRenderType type, ItemStack item, Object[] data) {
		// move
		GL11.glRotatef (180.0f, 1.0f, 0.0f, 0.0f);
		GL11.glScalef (1.9f, 1.9f, 1.9f);
		GL11.glTranslatef (0.25f, -1.25f, 0.15f);

		// rotate
		GL11.glRotatef (-45.0f, 1.0f, 0.0f, 0.0f);

		// first person fixes
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			// fix rotation
			GL11.glRotatef (-30.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef (30.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef (0.0f, 0.0f, 1.0f, 0.0f);

			// fix position
			GL11.glTranslatef (-0.5f, -0.0f, -0.4f);

			// scale model down
			GL11.glScalef (0.95f, 0.95f, 0.95f);
		}

		// bind texture
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation ("defense", "textures/blocks/trigger/motionDetector.png"));

		// render model
		model.render (((Entity) data[1]), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
	}

	/**
	 * Renders an inventory item.
	 * @param item The item.
	 * @param data The data.
	 */
	public void renderInventoryItem (ItemStack item, Object[] data) {
		// correct position
		GL11.glTranslatef (-0.6f, 1.25f, 0.0f);
		GL11.glScalef (1.8f, 1.8f, 1.8f);
		GL11.glRotatef (180.0f, 1.0f, 0.0f, 0.0f);

		// bind texture
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation ("defense", "textures/blocks/trigger/motionDetector.png"));

		// render model
		model.render (null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
	}
}