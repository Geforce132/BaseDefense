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
	ModelRenderer Base;
	ModelRenderer Extension;

	/**
	 * Constructs a new MovementSensorModel instance.
	 */
	public MotionDetectorModel () {
		textureWidth = 32;
		textureHeight = 32;

		Base = new ModelRenderer (this, 0, 6);
		Base.addBox (-2F, -1F, -1F, 4, 2, 1);
		Base.setRotationPoint (0F, 16F, 8F);
		Base.setTextureSize (32, 32);
		Base.mirror = true;
		setRotation (Base, 0F, 0F, 0F);
		Extension = new ModelRenderer (this, 0, 0);
		Extension.addBox (-4F, -2F, -2F, 8, 4, 2);
		Extension.setRotationPoint (0F, 16F, 7F);
		Extension.setTextureSize (32, 32);
		Extension.mirror = true;
		setRotation (Extension, 0F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		setRotationAngles (f, f1, f2, f3, f4, f5, entity);
		Base.render (f5);
		Extension.render (f5);
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
