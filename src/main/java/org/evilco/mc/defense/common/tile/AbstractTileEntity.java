/*
 * Copyright (C) 2014 Evil-Co <http://wwww.evil-co.org>
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
package org.evilco.mc.defense.common.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractTileEntity extends TileEntity {

	/**
	 * Indicates whether the TileEntity has already been initialized.
	 */
	protected boolean initialized = false;

	/**
	 * Initializes the TileEntity.
	 */
	public void initialize () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity () {
		super.updateEntity ();

		// initialize
		if (!this.initialized && !this.worldObj.isRemote) {
			// set new state
			this.initialized = true;

			// call initialize
			this.initialize ();
		}
	}
}