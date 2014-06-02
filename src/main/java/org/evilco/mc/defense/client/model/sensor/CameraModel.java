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
package org.evilco.mc.defense.client.model.sensor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CameraModel extends ModelBase {
	protected ModelRenderer base;
	protected ModelRenderer lens;
	protected ModelRenderer mount;

	/**
	 * Constructs a new CameraModel instance.
	 */
	public CameraModel () {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.base = new ModelRenderer (this, 0, 0);
		this.base.addBox (-3F, -3F, -3F, 6, 6, 8);
		this.base.setRotationPoint (0F, 18F, 0F);
		this.base.setTextureSize (64, 64);
		this.base.mirror = true;
		this.setRotation (this.base, 0F, 0F, 0F);

		this.lens = new ModelRenderer (this, 28, 0);
		this.lens.addBox (-1F, -1F, -6F, 2, 2, 2);
		this.lens.setRotationPoint (0F, 18F, 1F);
		this.lens.setTextureSize (64, 64);
		this.lens.mirror = true;
		this.setRotation (lens, 0F, 0F, 0F);

		this.mount = new ModelRenderer (this, 0, 14);
		this.mount.addBox (-1F, -1F, 0F, 2, 2, 8);
		this.mount.setRotationPoint (0F, 22F, 0F);
		this.mount.setTextureSize (64, 64);
		this.mount.mirror = true;
		this.setRotation (this.mount, 0F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render (Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		super.render (par1Entity, par2, par3, par4, par5, par6, par7);
		this.render (par1Entity, par2, par3, par4, par5, par6, par7, 0.0f);
	}

	/**
	 * Renders the camera.
	 * @see net.minecraft.client.model.ModelBase.render ()
	 */
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5, double cameraAngle) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		// apply angle
		this.base.rotateAngleY = ((float) cameraAngle);
		this.lens.rotateAngleY = ((float) cameraAngle);

		this.base.render (f5);
		this.lens.render (f5);
		this.mount.render (f5);
	}

	/**
	 * Sets the model rotation.
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
