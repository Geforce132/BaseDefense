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
package org.evilco.defense.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TrapTurbineModel extends ModelBase {
	//fields
	ModelRenderer Base;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer LowerFan0;
	ModelRenderer LowerFan1;
	ModelRenderer UpperFan1;
	ModelRenderer UpperFan0;

	public TrapTurbineModel () {
		textureWidth = 64;
		textureHeight = 64;

		Base = new ModelRenderer (this, 0, 0);
		Base.addBox (0F, 0F, 0F, 14, 31, 14);
		Base.setRotationPoint (-7F, -7F, -7F);
		Base.setTextureSize (64, 64);
		Base.mirror = true;
		setRotation (Base, 0F, 0F, 0F);
		Shape1 = new ModelRenderer (this, 56, 0);
		Shape1.addBox (0F, 0F, 0F, 1, 32, 1);
		Shape1.setRotationPoint (-8F, -8F, -8F);
		Shape1.setTextureSize (64, 64);
		Shape1.mirror = true;
		setRotation (Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer (this, 0, 45);
		Shape2.addBox (0F, 0F, 0F, 14, 1, 1);
		Shape2.setRotationPoint (-7F, -8F, -8F);
		Shape2.setTextureSize (64, 64);
		Shape2.mirror = true;
		setRotation (Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer (this, 56, 0);
		Shape3.addBox (0F, 0F, 0F, 1, 32, 1);
		Shape3.setRotationPoint (7F, -8F, -8F);
		Shape3.setTextureSize (64, 64);
		Shape3.mirror = true;
		setRotation (Shape3, 0F, 0F, 0F);
		LowerFan0 = new ModelRenderer (this, 56, 0);
		LowerFan0.addBox (-1F, -5F, 0F, 2, 12, 1);
		LowerFan0.setRotationPoint (0F, 15F, -8F);
		LowerFan0.setTextureSize (64, 64);
		LowerFan0.mirror = true;
		setRotation (LowerFan0, 0F, 0F, 0F);
		LowerFan1 = new ModelRenderer (this, 0, 45);
		LowerFan1.addBox (-6F, 0F, 0F, 12, 1, 1);
		LowerFan1.setRotationPoint (0F, 15F, -8F);
		LowerFan1.setTextureSize (64, 64);
		LowerFan1.mirror = true;
		setRotation (LowerFan1, 0F, 0F, 0F);
		UpperFan1 = new ModelRenderer (this, -1, 45);
		UpperFan1.addBox (-6F, 0F, 0F, 12, 1, 1);
		UpperFan1.setRotationPoint (0F, -1F, -8F);
		UpperFan1.setTextureSize (64, 64);
		UpperFan1.mirror = true;
		setRotation (UpperFan1, 0F, 0F, 0F);
		UpperFan0 = new ModelRenderer (this, 56, 0);
		UpperFan0.addBox (-1F, -6F, 0F, 2, 12, 1);
		UpperFan0.setRotationPoint (0F, 0F, -8F);
		UpperFan0.setTextureSize (64, 64);
		UpperFan0.mirror = true;
		setRotation (UpperFan0, 0F, 0F, 0F);
	}

	public void render (Entity entity, float fanRotation) {
		super.render (entity, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.062f);
		setRotationAngles (entity, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.062f);

		// rotate fan
		this.UpperFan0.rotateAngleZ = fanRotation;
		this.UpperFan1.rotateAngleZ = fanRotation;
		this.LowerFan0.rotateAngleZ = fanRotation;
		this.LowerFan1.rotateAngleZ = fanRotation;

		// render
		Base.render (0.062f);
		Shape1.render (0.062f);
		Shape2.render (0.062f);
		Shape3.render (0.062f);
		LowerFan0.render (0.062f);
		LowerFan1.render (0.062f);
		UpperFan1.render (0.062f);
		UpperFan0.render (0.062f);
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
