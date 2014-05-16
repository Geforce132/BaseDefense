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
package org.evilco.defense.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderBiped;
import org.evilco.defense.client.renderer.entity.SecurityBotRenderer;
import org.evilco.defense.client.renderer.generic.DefenseStationTileEntitySpecialRenderer;
import org.evilco.defense.client.renderer.surveillance.SurveillanceCameraTileEntitySpecialRenderer;
import org.evilco.defense.common.CommonProxy;
import org.evilco.defense.common.entity.SecurityBotEntity;
import org.evilco.defense.common.tile.generic.DefenseStationTileEntity;
import org.evilco.defense.common.tile.surveillance.SurveillanceCameraTileEntity;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ClientProxy extends CommonProxy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerRenderers () {
		super.registerRenderers ();

		// register TE renderers
		ClientRegistry.bindTileEntitySpecialRenderer (DefenseStationTileEntity.class, new DefenseStationTileEntitySpecialRenderer ());
		ClientRegistry.bindTileEntitySpecialRenderer (SurveillanceCameraTileEntity.class, new SurveillanceCameraTileEntitySpecialRenderer ());

		// register entity renderers
		RenderingRegistry.registerEntityRenderingHandler (SecurityBotEntity.class, new SecurityBotRenderer ());
	}
}