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
package org.evilco.mc.defense.client.model.generic;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SecurityBotModel extends ModelBase {
	protected ModelRenderer base;
	protected ModelRenderer frontPanel;
	protected ModelRenderer rifle1;
	protected ModelRenderer rifle2;

	/**
	 * Constructs a new SecurityBotModel instance.
	 */
	public SecurityBotModel () {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.base = new ModelRenderer (this, 0, 0);
		this.base.addBox (-6F, -3F, -7F, 12, 6, 14);
		this.base.setRotationPoint (0F, 21F, 0F);
		this.base.setTextureSize (64, 64);
		this.base.mirror = true;
		this.setRotation (this.base, 0F, 0F, 0F);

		this.frontPanel = new ModelRenderer (this, 26, 20);
		this.frontPanel.addBox (-6F, -14F, 0F, 12, 14, 1);
		this.frontPanel.setRotationPoint (0F, 19F, -7F);
		this.frontPanel.setTextureSize (64, 64);
		this.frontPanel.mirror = true;
		this.setRotation (this.frontPanel, -0.7853982F, 0F, 0F);

		this.rifle1 = new ModelRenderer (this, 0, 20);
		this.rifle1.addBox (-1F, -1F, -11F, 2, 2, 11);
		this.rifle1.setRotationPoint (-7F, 10F, 3F);
		this.rifle1.setTextureSize (64, 64);
		this.rifle1.mirror = true;
		this.setRotation (this.rifle1, 0F, 0F, 0F);

		this.rifle2 = new ModelRenderer (this, 0, 20);
		this.rifle2.addBox (-1F, -1F, -11F, 2, 2, 11);
		this.rifle2.setRotationPoint (7F, 10F, 3F);
		this.rifle2.setTextureSize (64, 64);
		this.rifle2.mirror = true;
		this.setRotation (this.rifle2, 0F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.base.render (f5);
		this.frontPanel.render (f5);
		this.rifle1.render (f5);
		this.rifle2.render (f5);
	}

	/**
	 * Sets a model's rotation.
	 * @param model The model.
	 * @param x The rotation around the X-axis.
	 * @param y The rotation around the Y-axis.
	 * @param z The rotation around the Z-axis.
	 */
	private void setRotation (ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
