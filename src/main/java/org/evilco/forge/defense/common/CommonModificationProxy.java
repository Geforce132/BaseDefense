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
package org.evilco.forge.defense.common;

import org.evilco.forge.defense.IModificationProxy;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class CommonModificationProxy implements IModificationProxy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preInitialize () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		this.registerTileEntities ();
		this.registerItems ();
		this.registerBlocks ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postInitialize () { }

	/**
	 * Registers modification blocks.
	 */
	protected final void registerBlocks () { }

	/**
	 * Registers modification items.
	 */
	protected final void registerItems () { }

	/**
	 * Registers modification tile entities.
	 */
	protected final void registerTileEntities () { }
}
