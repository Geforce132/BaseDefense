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
package org.evilco.defense.client.model.surveillance;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SurveillanceCameraModel extends ModelBase {
	//fields
	ModelRenderer base;
	ModelRenderer lens;
	ModelRenderer mount;

	public SurveillanceCameraModel () {
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer (this, 0, 0);
		base.addBox (-3F, -3F, -3F, 6, 6, 8);
		base.setRotationPoint (0F, 18F, 0F);
		base.setTextureSize (64, 64);
		base.mirror = true;
		setRotation (base, 0F, 0F, 0F);
		lens = new ModelRenderer (this, 28, 0);
		lens.addBox (-1F, -1F, -6F, 2, 2, 2);
		lens.setRotationPoint (0F, 18F, 1F);
		lens.setTextureSize (64, 64);
		lens.mirror = true;
		setRotation (lens, 0F, 0F, 0F);
		mount = new ModelRenderer (this, 0, 14);
		mount.addBox (-1F, -1F, 0F, 2, 2, 8);
		mount.setRotationPoint (0F, 22F, 0F);
		mount.setTextureSize (64, 64);
		mount.mirror = true;
		setRotation (mount, 0F, 0F, 0F);
	}

	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float cameraAngle) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		setRotationAngles (entity, f, f1, f2, f3, f4, f5);

		// apply angle
		this.base.rotateAngleY = cameraAngle;
		this.lens.rotateAngleY = cameraAngle;

		base.render (f5);
		lens.render (f5);
		mount.render (f5);
	}

	private void setRotation (ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.setRotationAngles (f, f1, f2, f3, f4, f5, entity);
	}

}
