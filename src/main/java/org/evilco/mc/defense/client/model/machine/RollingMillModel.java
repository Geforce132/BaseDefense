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
	//fields
	ModelRenderer Base;
	ModelRenderer Pillar01;
	ModelRenderer Pillar00;
	ModelRenderer Pillar03;
	ModelRenderer Pillar02;
	ModelRenderer Drum3;
	ModelRenderer Drum0;
	ModelRenderer Drum1;
	ModelRenderer Drum2;
	ModelRenderer Iron0;
	ModelRenderer Iron2;
	ModelRenderer Iron1;

	/**
	 * Constructs a new RollingMillModel instance.
	 */
	public RollingMillModel ()  {
		textureWidth = 64;
		textureHeight = 64;

		Base = new ModelRenderer (this, 0, 0);
		Base.addBox (-7F, -2F, -7F, 14, 4, 14);
		Base.setRotationPoint (0F, 22F, 0F);
		Base.setTextureSize (64, 64);
		Base.mirror = true;
		setRotation (Base, 0F, 0F, 0F);
		Pillar01 = new ModelRenderer (this, 56, 0);
		Pillar01.addBox (-1F, -6F, -1F, 2, 12, 2);
		Pillar01.setRotationPoint (-3F, 14F, 6F);
		Pillar01.setTextureSize (64, 64);
		Pillar01.mirror = true;
		setRotation (Pillar01, 0F, 0F, 0F);
		Pillar00 = new ModelRenderer (this, 56, 0);
		Pillar00.addBox (-1F, -6F, -1F, 2, 12, 2);
		Pillar00.setRotationPoint (-3F, 14F, -6F);
		Pillar00.setTextureSize (64, 64);
		Pillar00.mirror = true;
		setRotation (Pillar00, 0F, 0F, 0F);
		Pillar02 = new ModelRenderer (this, 56, 0);
		Pillar02.addBox (-1F, -5F, -1F, 2, 10, 2);
		Pillar02.setRotationPoint (4F, 15F, -6F);
		Pillar02.setTextureSize (64, 64);
		Pillar02.mirror = true;
		setRotation (Pillar02, 0F, 0F, 0F);
		Pillar03 = new ModelRenderer (this, 56, 0);
		Pillar03.addBox (-1F, -5F, -1F, 2, 10, 2);
		Pillar03.setRotationPoint (4F, 15F, 6F);
		Pillar03.setTextureSize (64, 64);
		Pillar03.mirror = true;
		setRotation (Pillar02, 0F, 0F, 0F);
		Drum3 = new ModelRenderer (this, 0, 18);
		Drum3.addBox (-2F, -2F, -5F, 4, 4, 10);
		Drum3.setRotationPoint (4F, 16F, 0F);
		Drum3.setTextureSize (64, 64);
		Drum3.mirror = true;
		setRotation (Drum3, 0F, 0F, 0F);
		Drum0 = new ModelRenderer (this, 0, 18);
		Drum0.addBox (-2F, -2F, -5F, 4, 4, 10);
		Drum0.setRotationPoint (-3F, 9F, 0F);
		Drum0.setTextureSize (64, 64);
		Drum0.mirror = true;
		setRotation (Drum0, 0F, 0F, 0F);
		Drum1 = new ModelRenderer (this, 0, 18);
		Drum1.addBox (-2F, -2F, -5F, 4, 4, 10);
		Drum1.setRotationPoint (-3F, 14F, 0F);
		Drum1.setTextureSize (64, 64);
		Drum1.mirror = true;
		setRotation (Drum1, 0F, 0F, 0F);
		Drum2 = new ModelRenderer (this, 0, 18);
		Drum2.addBox (-2F, -2F, -5F, 4, 4, 10);
		Drum2.setRotationPoint (4F, 11F, 0F);
		Drum2.setTextureSize (64, 64);
		Drum2.mirror = true;
		setRotation (Drum2, 0F, 0F, 0F);
		Iron0 = new ModelRenderer (this, 0, 32);
		Iron0.addBox (-3F, 0F, 0F, 6, 1, 1);
		Iron0.setRotationPoint (0F, 12.5F, -0.5F);
		Iron0.setTextureSize (64, 64);
		Iron0.mirror = true;
		setRotation (Iron0, 0.7853982F, 0F, 0.4712389F);
		Iron2 = new ModelRenderer (this, 0, 32);
		Iron2.addBox (0F, 0F, 0F, 5, 1, 1);
		Iron2.setRotationPoint (2F, 13.5F, -0.5F);
		Iron2.setTextureSize (64, 64);
		Iron2.mirror = true;
		setRotation (Iron2, 0.7853982F, 0F, 0F);
		Iron1 = new ModelRenderer (this, 0, 32);
		Iron1.addBox (0F, 0F, 0F, 5, 1, 1);
		Iron1.setRotationPoint (-7F, 11.5F, -0.5F);
		Iron1.setTextureSize (64, 64);
		Iron1.mirror = true;
		setRotation (Iron1, 0.7853982F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		setRotationAngles (f, f1, f2, f3, f4, f5, entity);
		Base.render (f5);
		Pillar01.render (f5);
		Pillar00.render (f5);
		Pillar02.render (f5);
		Pillar03.render (f5);
	}

	/**
	 * Renders all drums.
	 * @param rotation The drum rotation.
	 * @see RollingMillModel.render ()
	 */
	public void renderDrums (Entity entity, float f, float f1, float f2, float f3, float f4, float f5, double rotation) {
		setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.Drum0.rotateAngleZ = ((float) rotation);
		this.Drum1.rotateAngleZ = ((float) rotation);
		this.Drum2.rotateAngleZ = ((float) rotation);
		this.Drum3.rotateAngleZ = ((float) rotation);

		Drum3.render (f5);
		Drum0.render (f5);
		Drum1.render (f5);
		Drum2.render (f5);
	}

	/**
	 * Renders the item.
	 * @see RollingMillModel.render ()
	 */
	public void renderItem (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		Iron0.render (f5);
		Iron2.render (f5);
		Iron1.render (f5);
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
