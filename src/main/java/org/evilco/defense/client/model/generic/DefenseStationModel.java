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
package org.evilco.defense.client.model.generic;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DefenseStationModel extends ModelBase {
	//fields
	ModelRenderer base;
	ModelRenderer slot1;
	ModelRenderer slot2;
	ModelRenderer slot3;
	ModelRenderer slot4;

	public DefenseStationModel () {
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer (this, 0, 0);
		base.addBox (-7F, -8F, -7F, 14, 32, 14);
		base.setRotationPoint (0F, 0F, 0F);
		base.setTextureSize (64, 64);
		base.mirror = true;
		setRotation (base, 0F, 0F, 0F);
		slot1 = new ModelRenderer (this, 0, 46);
		slot1.addBox (-6F, -1F, -1F, 12, 2, 1);
		slot1.setRotationPoint (0F, 4F, -7F);
		slot1.setTextureSize (64, 64);
		slot1.mirror = true;
		setRotation (slot1, 0F, 0F, 0F);
		slot2 = new ModelRenderer (this, 0, 46);
		slot2.addBox (-6F, -1F, -1F, 12, 2, 1);
		slot2.setRotationPoint (0F, 8F, -7F);
		slot2.setTextureSize (64, 64);
		slot2.mirror = true;
		setRotation (slot2, 0F, 0F, 0F);
		slot3 = new ModelRenderer (this, 0, 46);
		slot3.addBox (-6F, -1F, -1F, 12, 2, 1);
		slot3.setRotationPoint (0F, 12F, -7F);
		slot3.setTextureSize (64, 64);
		slot3.mirror = true;
		setRotation (slot3, 0F, 0F, 0F);
		slot4 = new ModelRenderer (this, 0, 46);
		slot4.addBox (-6F, -1F, -1F, 12, 2, 1);
		slot4.setRotationPoint (0F, 16F, -7F);
		slot4.setTextureSize (64, 64);
		slot4.mirror = true;
		setRotation (slot4, 0F, 0F, 0F);
	}

	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		setRotationAngles (entity, f, f1, f2, f3, f4, f5);
		base.render (f5);
		slot1.render (f5);
		slot2.render (f5);
		slot3.render (f5);
		slot4.render (f5);
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
