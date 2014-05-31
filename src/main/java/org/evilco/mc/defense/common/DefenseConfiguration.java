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
package org.evilco.mc.defense.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefenseConfiguration extends Configuration {

	/**
	 * Defines the identification timeout used for defense stations.
	 */
	protected int stateTimeout;

	/**
	 * Constructs a new DefenseConfiguration instance.
	 * @param file The configuration file reference.
	 */
	public DefenseConfiguration (File file) {
		super (file);

		// load configuration
		this.load ();

		// read variables
		Property property = this.get ("defenseStation", "stateTimeout", 1200);
		property.comment = "Defines the amount of time the system has to identify an entity before it goes back to it's normal state in ticks (20 ticks = 1 second).";
		this.stateTimeout = property.getInt ();

		// write data
		this.save ();
	}

	/**
	 * Returns the state timeout.
	 * @return The amount of ticks until the system goes back to it's normal state.
	 */
	public int getStateTimeout () {
		return this.stateTimeout;
	}
}