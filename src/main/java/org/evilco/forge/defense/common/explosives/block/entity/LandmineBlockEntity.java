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
package org.evilco.forge.defense.common.explosives.block.entity;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import org.evilco.forge.defense.common.explosives.ExplosivesDamageSource;

import java.util.List;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class LandmineBlockEntity extends TileEntity {

	/**
	 * Defines the block entity name.
	 */
	public static final String NAME = "explosives_landmine";

	/**
	 * Blows up the mine.
	 */
	public void explode () {
		// remove block
		this.worldObj.setBlock (this.xCoord, this.yCoord, this.zCoord, Blocks.air);

		// spawn explosion
		this.worldObj.newExplosion (null, this.xCoord, this.yCoord, this.zCoord, 2.5f, true, true);

		// damage all entities in radius
		List<Entity> damageEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity (null, this.getDamageBounds ());

		for (Entity entity : damageEntities) {
			entity.attackEntityFrom (ExplosivesDamageSource.LANDMINE, 24.0f);
		}
	}

	/**
	 * Returns the damage bounds.
	 * @return The bounds.
	 */
	public AxisAlignedBB getDamageBounds () {
		return AxisAlignedBB.getBoundingBox ((this.xCoord - 2), (this.yCoord - 2), (this.zCoord - 2), (this.xCoord + 2), (this.yCoord + 2), (this.zCoord + 2));
	}

	/**
	 * Returns the detection bounds.
	 * @return The bounds.
	 */
	public AxisAlignedBB getDetectionBounds () {
		return AxisAlignedBB.getBoundingBox (this.xCoord, this.yCoord, this.zCoord, (this.xCoord + 1), (this.yCoord + 1), (this.zCoord + 1));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity () {
		super.updateEntity ();

		// skip client update (if any)
		if (this.worldObj.isRemote) return;

		// get entities directly above
		List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity (null, this.getDetectionBounds ());

		// explode if needed
		if (entities.size () > 0) this.explode ();
	}
}
