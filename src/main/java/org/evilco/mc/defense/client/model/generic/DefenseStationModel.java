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
package org.evilco.mc.defense.client.model.generic;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationModel extends ModelBase {
	protected ModelRenderer base;
	protected ModelRenderer slot1;
	protected ModelRenderer slot2;
	protected ModelRenderer slot3;
	protected ModelRenderer slot4;

	/**
	 * Constructs a new DefenseStationModel instance.
	 */
	public DefenseStationModel () {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.base = new ModelRenderer (this, 0, 0);
		this.base.addBox (-7F, -8F, -7F, 14, 32, 14);
		this.base.setRotationPoint (0F, 0F, 0F);
		this.base.setTextureSize (64, 64);
		this.base.mirror = true;
		this.setRotation (this.base, 0F, 0F, 0F);

		this.slot1 = new ModelRenderer (this, 0, 46);
		this.slot1.addBox (-6F, -1F, -1F, 12, 2, 1);
		this.slot1.setRotationPoint (0F, 4F, -7F);
		this.slot1.setTextureSize (64, 64);
		this.slot1.mirror = true;
		this.setRotation (this.slot1, 0F, 0F, 0F);

		this.slot2 = new ModelRenderer (this, 0, 46);
		this.slot2.addBox (-6F, -1F, -1F, 12, 2, 1);
		this.slot2.setRotationPoint (0F, 8F, -7F);
		this.slot2.setTextureSize (64, 64);
		this.slot2.mirror = true;
		this.setRotation (this.slot2, 0F, 0F, 0F);

		this.slot3 = new ModelRenderer (this, 0, 46);
		this.slot3.addBox (-6F, -1F, -1F, 12, 2, 1);
		this.slot3.setRotationPoint (0F, 12F, -7F);
		this.slot3.setTextureSize (64, 64);
		this.slot3.mirror = true;
		this.setRotation (this.slot3, 0F, 0F, 0F);

		this.slot4 = new ModelRenderer (this, 0, 46);
		this.slot4.addBox (-6F, -1F, -1F, 12, 2, 1);
		this.slot4.setRotationPoint (0F, 16F, -7F);
		this.slot4.setTextureSize (64, 64);
		this.slot4.mirror = true;
		this.setRotation (this.slot4, 0F, 0F, 0F);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles (f, f1, f2, f3, f4, f5, entity);

		this.base.render (f5);
		this.slot1.render (f5);
		this.slot2.render (f5);
		this.slot3.render (f5);
		this.slot4.render (f5);
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
