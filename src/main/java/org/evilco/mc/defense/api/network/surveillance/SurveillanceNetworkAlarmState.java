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
package org.evilco.mc.defense.api.network.surveillance;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Provides globally valid alarm states of the network.
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum SurveillanceNetworkAlarmState {
	OFFLINE (-1), // No actions at all
	GREEN (0), // Security bots waiting for instructions, Cameras are active
	YELLOW (1), // Intruder could not be found, Security bots are patrolling nearby to investigate, Cameras active
	RED (2); // Intruder detected, Security Bots have been requested to investigate, Cameras active

	/**
	 * Stores the state reverse map.
	 */
	private static final Map<Integer, SurveillanceNetworkAlarmState> reverseMap;

	/**
	 * Stores the state number.
	 */
	public final int stateNumber;

	/**
	 * Static Initialization.
	 */
	static {
		// create map builder
		ImmutableMap.Builder<Integer, SurveillanceNetworkAlarmState> reverseBuilder = new ImmutableMap.Builder<Integer, SurveillanceNetworkAlarmState> ();

		// iterate over elements
		for (SurveillanceNetworkAlarmState state : values ()) {
			reverseBuilder.put (state.stateNumber, state);
		}

		// build map
		reverseMap = reverseBuilder.build ();
	}

	/**
	 * Constructs a new SurveillanceNetworkAlarmState instance.
	 * @param stateNumber The state number.
	 */
	private SurveillanceNetworkAlarmState (int stateNumber) {
		this.stateNumber = stateNumber;
	}

	/**
	 * Returns the next higher state.
	 * @return The higher state.
	 */
	public SurveillanceNetworkAlarmState getNextState () {
		return valueOf ((this.stateNumber + 1));
	}

	/**
	 * Returns the next lower state.
	 * @return The lower state.
	 */
	public SurveillanceNetworkAlarmState getLowerState () {
		if (this.stateNumber == 0) return null;
		return reverseMap.get ((this.stateNumber - 1));
	}

	/**
	 * Returns a state based on it's number.
	 * @param stateNumber The state number.
	 * @return The state.
	 */
	public static SurveillanceNetworkAlarmState valueOf (int stateNumber) {
		if (!reverseMap.containsKey (stateNumber)) return null;
		return reverseMap.get (stateNumber);
	}
}