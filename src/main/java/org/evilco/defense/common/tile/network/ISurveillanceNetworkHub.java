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
package org.evilco.defense.common.tile.network;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ISurveillanceNetworkHub extends ISurveillanceNetworkEntity {

	/**
	 * Notifies the hub about a connecting entity.
	 * @param entity The entity.
	 * @throws SurveillanceEntityConnectionException
	 */
	public void connectEntity (ISurveillanceNetworkClient entity) throws SurveillanceEntityConnectionException;

	/**
	 * Notifies the hub about a disconnecting entity.
	 * @param entity
	 */
	public void disconnectEntity (ISurveillanceNetworkClient entity);
}