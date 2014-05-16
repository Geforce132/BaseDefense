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
package org.evilco.defense.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SecurityBotEntity extends EntityCreature {

	/**
	 * Constructs a new SecurityBotEntity.
	 * @param par1World The world to spawn the entity in.
	 */
	public SecurityBotEntity (World par1World) {
		super (par1World);

		this.tasks.addTask (1, new EntityAIAttackOnCollide (this, EntityPlayer.class, 1.2d, false));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void applyEntityAttributes () {
		super.applyEntityAttributes ();

		this.getEntityAttribute (SharedMonsterAttributes.maxHealth).setBaseValue (200.0d);
		this.getEntityAttribute (SharedMonsterAttributes.followRange).setBaseValue (32.0d);
		this.getEntityAttribute (SharedMonsterAttributes.knockbackResistance).setBaseValue (20.0d); // insanely high value ... no knockback whatsoever
		this.getEntityAttribute (SharedMonsterAttributes.movementSpeed).setBaseValue (0.25d);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isAIEnabled () {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDeath (DamageSource par1DamageSource) {
		super.onDeath (par1DamageSource);

		// TODO: Drop bot as item.
	}
}