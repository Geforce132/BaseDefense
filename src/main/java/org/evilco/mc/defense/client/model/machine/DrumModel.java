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
package org.evilco.mc.defense.client.model.machine;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DrumModel extends ModelBase {
	protected ModelRenderer drum = null;

	/**
	 * Constructs a new DrumModel instance.
	 */
	public DrumModel () {
		super ();

		this.textureWidth = 64;
		this.textureHeight = 64;

		this.drum = new ModelRenderer (this, 0, 18);
		this.drum.addBox (-2F, -2F, -5F, 4, 4, 10);
		this.drum.setRotationPoint (-3F, 9F, 0F);
		this.drum.setTextureSize (64, 64);
		this.drum.mirror = true;
		this.setRotation (this.drum, 0F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.drum.render (f5);
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