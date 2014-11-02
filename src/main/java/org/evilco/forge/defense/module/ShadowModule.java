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
import net.minecraftforge.fluids.FluidRegistry;
import org.evilco.forge.defense.common.shadow.ShadowBlock;
import org.evilco.forge.defense.common.shadow.ShadowFluids;
import org.evilco.forge.defense.common.shadow.block.ShadowMatterBlock;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBlocks () {
		super.registerBlocks ();

		GameRegistry.registerBlock (ShadowBlock.SHADOW_MATTER, ShadowMatterBlock.NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerFluids () {
		super.registerFluids ();

		FluidRegistry.registerFluid (ShadowFluids.SHADOW);
	}
}
