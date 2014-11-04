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

import cpw.mods.fml.common.registry.GameRegistry;
import lombok.Getter;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import org.evilco.forge.defense.common.shadow.ShadowBlock;
import org.evilco.forge.defense.common.shadow.ShadowFluids;
import org.evilco.forge.defense.common.shadow.ShadowPotion;
import org.evilco.forge.defense.common.shadow.block.ShadowFluidBlock;
import org.evilco.forge.defense.common.shadow.block.ShadowMatterBlock;
import org.evilco.forge.defense.common.shadow.dimension.ShadowWorldProvider;
import org.evilco.forge.defense.common.shadow.item.ShadowMatterItemBlock;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowModule extends AbstractModule {

	/**
	 * Stores the shadow resistance potion ID.
	 */
	@Getter
	private int potionShadowResistanceID = 128;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadConfiguration (Configuration configuration) {
		super.loadConfiguration (configuration);

		this.potionShadowResistanceID = configuration.getInt ("shadowResistance", "potionID", this.potionShadowResistanceID, 0, 256, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBlocks () {
		super.registerBlocks ();

		GameRegistry.registerBlock (ShadowBlock.SHADOW_MATTER, ShadowMatterItemBlock.class, ShadowMatterBlock.NAME);
		GameRegistry.registerBlock (ShadowBlock.SHADOW_FLUID, ShadowFluidBlock.NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerDimensions (Configuration configuration) {
		super.registerDimensions (configuration);

		// find dimension IDs
		int shadowDimensionID = configuration.getInt ("shadowID", "dimension", DimensionManager.getNextFreeDimId (), 0, 1024, "");

		// update provider
		// TODO: Fix this bullshit
		ShadowWorldProvider.DIMENSION_ID = shadowDimensionID;

		// register shadow realm
		DimensionManager.registerProviderType (shadowDimensionID, ShadowWorldProvider.class, false);
		DimensionManager.registerDimension (shadowDimensionID, ShadowWorldProvider.DIMENSION_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerFluids () {
		super.registerFluids ();

		FluidRegistry.registerFluid (ShadowFluids.SHADOW);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerPotions () {
		super.registerPotions ();

		ShadowPotion.initialize (this);
	}
}
