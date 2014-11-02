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

import net.minecraftforge.common.config.Configuration;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public abstract class AbstractModule implements IModule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadConfiguration (Configuration configuration) { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBlocks () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBlockEntities () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerCraftingRecipes () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerDimensions () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerEntities () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerFluids () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerItems () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerPotions () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerRenderers () { }
}
