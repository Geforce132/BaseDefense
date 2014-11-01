/*
 * Copyright 2014 Johannes Donath <johannesd@evil-co.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.evilco.forge.defense.module;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import org.evilco.forge.defense.client.explosives.renderer.LandmineTileEntityRenderer;
import org.evilco.forge.defense.common.explosives.block.ExplosivesBlock;
import org.evilco.forge.defense.common.explosives.block.LandmineBlock;
import org.evilco.forge.defense.common.explosives.block.entity.LandmineBlockEntity;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ExplosivesModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBlocks () {
		super.registerBlocks ();

		GameRegistry.registerBlock (ExplosivesBlock.LANDMINE, LandmineBlock.NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBlockEntities () {
		super.registerBlockEntities ();

		GameRegistry.registerTileEntity (LandmineBlockEntity.class, LandmineBlockEntity.NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerRenderers () {
		super.registerRenderers ();

		ClientRegistry.bindTileEntitySpecialRenderer (LandmineBlockEntity.class, new LandmineTileEntityRenderer ());
	}
}
