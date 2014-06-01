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
package org.evilco.mc.defense.client.model.trigger;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MotionDetectorModel extends ModelBase {
	//fields
	ModelRenderer base;
	ModelRenderer extension;

	/**
	 * Constructs a new MovementSensorModel instance.
	 */
	public MotionDetectorModel () {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.base = new ModelRenderer (this, 0, 6);
		this.base.addBox (-2F, -1F, -1F, 4, 2, 1);
		this.base.setRotationPoint (0F, 16F, 8F);
		this.base.setTextureSize (32, 32);
		this.base.mirror = true;
		this.setRotation (this.base, 0F, 0F, 0F);

		this.extension = new ModelRenderer (this, 0, 0);
		this.extension.addBox (-4F, -2F, -2F, 8, 4, 2);
		this.extension.setRotationPoint (0F, 16F, 7F);
		this.extension.setTextureSize (32, 32);
		this.extension.mirror = true;
		this.setRotation (this.extension, 0F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.base.render (f5);
		this.extension.render (f5);
	}

	/**
	 * {@inheritDoc}
	 */
	private void setRotation (ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
