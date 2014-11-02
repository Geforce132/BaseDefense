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
package org.evilco.forge.defense.common.shadow.fluid;

import net.minecraftforge.fluids.Fluid;
import org.evilco.forge.defense.common.shadow.ShadowString;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ShadowFluid extends Fluid {

	/**
	 * Defines the fluid density.
	 */
	public static final int DENSITY = 1000;

	/**
	 * Defines the fluid luminosity.
	 */
	public static final int LUMINOSITY = 4;

	/**
	 * Defines the fluid viscosity.
	 */
	public static final int VISCOSITY = 3000;

	/**
	 * Constructs a new ShadowFluid instance.
	 */
	public ShadowFluid () {
		super (ShadowString.FLUID_NAME_SHADOW);

		this.setDensity (DENSITY);
		this.setLuminosity (LUMINOSITY);
		this.setViscosity (VISCOSITY);
	}
}
