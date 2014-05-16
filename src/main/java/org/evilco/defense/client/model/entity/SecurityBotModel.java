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
package org.evilco.defense.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SecurityBotModel extends ModelBase {
	//fields
	ModelRenderer Base;
	ModelRenderer FrontPanel;
	ModelRenderer Rifle1;
	ModelRenderer Rifle2;

	public SecurityBotModel () {
		textureWidth = 64;
		textureHeight = 64;

		Base = new ModelRenderer (this, 0, 0);
		Base.addBox (-6F, -3F, -7F, 12, 6, 14);
		Base.setRotationPoint (0F, 21F, 0F);
		Base.setTextureSize (64, 64);
		Base.mirror = true;
		setRotation (Base, 0F, 0F, 0F);
		FrontPanel = new ModelRenderer (this, 26, 20);
		FrontPanel.addBox (-6F, -14F, 0F, 12, 14, 1);
		FrontPanel.setRotationPoint (0F, 19F, -7F);
		FrontPanel.setTextureSize (64, 64);
		FrontPanel.mirror = true;
		setRotation (FrontPanel, -0.7853982F, 0F, 0F);
		Rifle1 = new ModelRenderer (this, 0, 20);
		Rifle1.addBox (-1F, -1F, -11F, 2, 2, 11);
		Rifle1.setRotationPoint (-7F, 10F, 3F);
		Rifle1.setTextureSize (64, 64);
		Rifle1.mirror = true;
		setRotation (Rifle1, 0F, 0F, 0F);
		Rifle2 = new ModelRenderer (this, 0, 20);
		Rifle2.addBox (-1F, -1F, -11F, 2, 2, 11);
		Rifle2.setRotationPoint (7F, 10F, 3F);
		Rifle2.setTextureSize (64, 64);
		Rifle2.mirror = true;
		setRotation (Rifle2, 0F, 0F, 0F);
	}

	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		setRotationAngles (entity, f, f1, f2, f3, f4, f5);
		Base.render (f5);
		FrontPanel.render (f5);
		Rifle1.render (f5);
		Rifle2.render (f5);
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
