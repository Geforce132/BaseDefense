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
package org.evilco.forge.defense.common.explosives.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class PrimedChemicalExplosiveEntity extends PrimedExplosiveEntity {

	/**
	 * Defines the amount of particles spawned within the explosion range.
	 */
	public static final int PARTICLE_COUNT = 150;

	/**
	 * Defines the explosion range.
	 */
	public static final int RANGE = 5;

	/**
	 * Constructs a new PrimedChemicalExplosiveEntity instance.
	 * @param p_i1582_1_ The world.
	 * @param fuse The fuse.
	 */
	public PrimedChemicalExplosiveEntity (World p_i1582_1_, short fuse) {
		super (p_i1582_1_, fuse);
	}

	/**
	 * Returns the explosion range.
	 * @return The range.
	 */
	public AxisAlignedBB getExplosionRange () {
		return AxisAlignedBB.getBoundingBox ((this.posX - RANGE), (this.posY - RANGE), (this.posZ - RANGE), (this.posX + RANGE), (this.posY + RANGE), (this.posZ + RANGE));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void explode () {
		super.explode ();

		// spawn particles
		for (int i = 0; i < PARTICLE_COUNT; i++) {
			// calculate a point
			int x = (this.worldObj.rand.nextInt ((RANGE * 2)) - RANGE);
			int y = (this.worldObj.rand.nextInt ((RANGE * 2)) - RANGE);
			int z = (this.worldObj.rand.nextInt ((RANGE * 2)) - RANGE);

			// spawn particle
			this.worldObj.spawnParticle ("spell", (this.posX + x), (this.posY + y), (this.posZ + z), 0, 0.5f, 0);
		}

		// get all entities in range
		List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB (EntityLivingBase.class, this.getExplosionRange ());

		// apply potion effect
		for (EntityLivingBase entity : entities) {
			entity.addPotionEffect (new PotionEffect (Potion.poison.getId (), 600));
		}
	}
}
