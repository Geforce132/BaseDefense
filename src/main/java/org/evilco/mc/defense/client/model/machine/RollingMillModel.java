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
package org.evilco.mc.defense.client.model.machine;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class RollingMillModel extends ModelBase {
	protected ModelRenderer base;
	protected ModelRenderer pillar01;
	protected ModelRenderer pillar00;
	protected ModelRenderer pillar03;
	protected ModelRenderer pillar02;
	protected ModelRenderer drum3;
	protected ModelRenderer drum0;
	protected ModelRenderer drum1;
	protected ModelRenderer drum2;
	protected ModelRenderer iron0;
	protected ModelRenderer iron2;
	protected ModelRenderer iron1;

	/**
	 * Constructs a new RollingMillModel instance.
	 */
	public RollingMillModel ()  {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.base = new ModelRenderer (this, 0, 0);
		this.base.addBox (-7F, -2F, -7F, 14, 4, 14);
		this.base.setRotationPoint (0F, 22F, 0F);
		this.base.setTextureSize (64, 64);
		this.base.mirror = true;
		this.setRotation (this.base, 0F, 0F, 0F);

		this.pillar01 = new ModelRenderer (this, 56, 0);
		this.pillar01.addBox (-1F, -6F, -1F, 2, 12, 2);
		this.pillar01.setRotationPoint (-3F, 14F, 6F);
		this.pillar01.setTextureSize (64, 64);
		this.pillar01.mirror = true;
		this.setRotation (this.pillar01, 0F, 0F, 0F);

		this.pillar00 = new ModelRenderer (this, 56, 0);
		this.pillar00.addBox (-1F, -6F, -1F, 2, 12, 2);
		this.pillar00.setRotationPoint (-3F, 14F, -6F);
		this.pillar00.setTextureSize (64, 64);
		this.pillar00.mirror = true;
		this.setRotation (this.pillar00, 0F, 0F, 0F);

		this.pillar02 = new ModelRenderer (this, 56, 0);
		this.pillar02.addBox (-1F, -5F, -1F, 2, 10, 2);
		this.pillar02.setRotationPoint (4F, 15F, -6F);
		this.pillar02.setTextureSize (64, 64);
		this.pillar02.mirror = true;
		this.setRotation (this.pillar02, 0F, 0F, 0F);

		this.pillar03 = new ModelRenderer (this, 56, 0);
		this.pillar03.addBox (-1F, -5F, -1F, 2, 10, 2);
		this.pillar03.setRotationPoint (4F, 15F, 6F);
		this.pillar03.setTextureSize (64, 64);
		this.pillar03.mirror = true;
		this.setRotation (this.pillar02, 0F, 0F, 0F);

		this.drum3 = new ModelRenderer (this, 0, 18);
		this.drum3.addBox (-2F, -2F, -5F, 4, 4, 10);
		this.drum3.setRotationPoint (4F, 16F, 0F);
		this.drum3.setTextureSize (64, 64);
		this.drum3.mirror = true;
		this.setRotation (this.drum3, 0F, 0F, 0F);

		this.drum0 = new ModelRenderer (this, 0, 18);
		this.drum0.addBox (-2F, -2F, -5F, 4, 4, 10);
		this.drum0.setRotationPoint (-3F, 9F, 0F);
		this.drum0.setTextureSize (64, 64);
		this.drum0.mirror = true;
		this.setRotation (this.drum0, 0F, 0F, 0F);

		this.drum1 = new ModelRenderer (this, 0, 18);
		this.drum1.addBox (-2F, -2F, -5F, 4, 4, 10);
		this.drum1.setRotationPoint (-3F, 14F, 0F);
		this.drum1.setTextureSize (64, 64);
		this.drum1.mirror = true;
		this.setRotation (this.drum1, 0F, 0F, 0F);

		this.drum2 = new ModelRenderer (this, 0, 18);
		this.drum2.addBox (-2F, -2F, -5F, 4, 4, 10);
		this.drum2.setRotationPoint (4F, 11F, 0F);
		this.drum2.setTextureSize (64, 64);
		this.drum2.mirror = true;
		this.setRotation (this.drum2, 0F, 0F, 0F);

		this.iron0 = new ModelRenderer (this, 0, 32);
		this.iron0.addBox (-3F, 0F, 0F, 6, 1, 1);
		this.iron0.setRotationPoint (0F, 12.5F, -0.5F);
		this.iron0.setTextureSize (64, 64);
		this.iron0.mirror = true;
		this.setRotation (this.iron0, 0.7853982F, 0F, 0.4712389F);

		this.iron2 = new ModelRenderer (this, 0, 32);
		this.iron2.addBox (0F, 0F, 0F, 5, 1, 1);
		this.iron2.setRotationPoint (2F, 13.5F, -0.5F);
		this.iron2.setTextureSize (64, 64);
		this.iron2.mirror = true;
		this.setRotation (this.iron2, 0.7853982F, 0F, 0F);

		this.iron1 = new ModelRenderer (this, 0, 32);
		this.iron1.addBox (0F, 0F, 0F, 5, 1, 1);
		this.iron1.setRotationPoint (-7F, 11.5F, -0.5F);
		this.iron1.setTextureSize (64, 64);
		this.iron1.mirror = true;
		this.setRotation (this.iron1, 0.7853982F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.base.render (f5);
		this.pillar01.render (f5);
		this.pillar00.render (f5);
		this.pillar02.render (f5);
		this.pillar03.render (f5);
	}

	/**
	 * Renders all drums.
	 * @param rotation The drum rotation.
	 * @see RollingMillModel.render ()
	 */
	public void renderDrums (Entity entity, float f, float f1, float f2, float f3, float f4, float f5, double rotation) {
		setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.drum0.rotateAngleZ = ((float) rotation);
		this.drum1.rotateAngleZ = ((float) rotation);
		this.drum2.rotateAngleZ = ((float) rotation);
		this.drum3.rotateAngleZ = ((float) rotation);

		this.drum3.render (f5);
		this.drum0.render (f5);
		this.drum1.render (f5);
		this.drum2.render (f5);
	}

	/**
	 * Renders the item.
	 * @see RollingMillModel.render ()
	 */
	public void renderItem (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.iron0.render (f5);
		this.iron2.render (f5);
		this.iron1.render (f5);
	}

	/**
	 * Sets the model rotation.
	 * @param model The model.
	 * @param x Rotation around the X-axis.
	 * @param y Rotation around the Y-axis.
	 * @param z Rotation around the Z-axis.
	 */
	private void setRotation (ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
