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
package org.evilco.defense.client.renderer.generic;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.evilco.defense.client.model.generic.DefenseStationModel;
import org.evilco.defense.common.tile.generic.DefenseStationTileEntity;
import org.lwjgl.opengl.GL11;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseStationTileEntitySpecialRenderer extends TileEntitySpecialRenderer {

	/**
	 * Stores the defense station model.
	 */
	protected DefenseStationModel model = new DefenseStationModel ();

	@Override
	public void renderTileEntityAt (TileEntity var1, double var2, double var4, double var6, float var8) {
		GL11.glPushMatrix ();

		GL11.glTranslatef (((float) var2), ((float) var4), ((float) var6));
		GL11.glTranslatef (0.5f, 1.5f, 0.5f);
		GL11.glRotatef (180.0f, 1.0f, 0.0f, 0.0f);

		GL11.glRotated (((DefenseStationTileEntity) var1).getRotationAngle (), 0f, 1f, 0f);

		GL11.glPushMatrix ();
		this.bindTexture (new ResourceLocation ("defense", "textures/models/generic/defenseStation.png"));
		model.render (null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.062f);
		GL11.glPopMatrix ();

		GL11.glPopMatrix ();
	}
}