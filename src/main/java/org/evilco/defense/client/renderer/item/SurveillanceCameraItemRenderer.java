package org.evilco.defense.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.evilco.defense.client.model.surveillance.SurveillanceCameraModel;
import org.evilco.defense.common.tile.surveillance.SurveillanceCameraTileEntity;
import org.lwjgl.opengl.GL11;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SurveillanceCameraItemRenderer implements IItemRenderer {

	/**
	 * Stores the camera model.
	 */
	protected static final SurveillanceCameraModel model = new SurveillanceCameraModel ();

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
		GL11.glTranslatef (0.0f, 1.5f, 0.0f);
		GL11.glScalef (1.2f, 1.2f, 1.2f);
		GL11.glRotatef (180.0f, 1.0f, 0.0f, 0.0f);

		// bind texture
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation ("defense", "textures/blocks/surveillance/camera" + (item.getItemDamage () == 1 ? "Mob" : "") + ".png"));

		// render model
		model.render (null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.062f, 0.0f);
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
		GL11.glRotatef (-170.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef (-20.0f, 1.0f, 0.0f, 0.0f);
		GL11.glTranslatef (-0.8f, -1.6f, 0.15f);

		// rotate
		GL11.glRotatef (45.0f, -0.5f, 0.0f, 0.9f);

		// scale
		GL11.glScalef (1.8f, 1.8f, 1.8f);

		// first person fixes
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			// fix rotation
			GL11.glRotatef (-30.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef (30.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef (0.0f, 0.0f, 1.0f, 0.0f);

			// fix position
			GL11.glTranslatef (-0.5f, -0.25f, -0.4f);

			// scale model down
			GL11.glScalef (0.95f, 0.95f, 0.95f);
		}

		// bind texture
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation ("defense", "textures/blocks/surveillance/camera" + (item.getItemDamage () == 1 ? "Mob" : "") + ".png"));

		// render model
		model.render (((Entity) data[1]), 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.062f, 0.0f);
	}

	/**
	 * Renders an inventory item.
	 * @param item The item.
	 * @param data The data.
	 */
	public void renderInventoryItem (ItemStack item, Object[] data) {
		// correct position
		GL11.glTranslatef (-0.2f, 2.0f, 0.0f);
		GL11.glScalef (1.8f, 1.8f, 1.8f);
		GL11.glRotatef (180.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef (15.0f, 0.0f, 1.0f, 0.0f);

		// bind texture
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation ("defense", "textures/blocks/surveillance/camera" + (item.getItemDamage () == 1 ? "Mob" : "") + ".png"));

		// render model
		model.render (null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.062f, 0.0f);
	}
}