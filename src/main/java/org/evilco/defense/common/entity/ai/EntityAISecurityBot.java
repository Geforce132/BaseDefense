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
package org.evilco.defense.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import org.evilco.defense.common.entity.SecurityBotEntity;
import org.evilco.defense.common.tile.network.AttackOrderRequestPacket;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class EntityAISecurityBot extends EntityAIBase {

	/**
	 * Stores the tickets until we can attack again ...
	 */
	protected int attackTick = 0;

	/**
	 * Stores the current path entity.
	 */
	protected PathEntity currentPathEntity = null;

	/**
	 * Stores the parent entity.
	 */
	protected SecurityBotEntity entity = null;

	/**
	 * Indicates whether the bot is waiting for response.
	 */
	public boolean waitingForResponse = false;

	/**
	 * Constructs a new EntityAISecurityBot.
	 * @param entity The parent entity.
	 */
	public EntityAISecurityBot (SecurityBotEntity entity) {
		this.entity = entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean continueExecuting () {
		return this.entity.isEntityAlive ();
	}

	/**
	 * Notifies the entity about an alert.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 */
	public void notifyAlert (double x, double y, double z) {
		this.entity.getNavigator ().tryMoveToXYZ (x, y, z, 0.0f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetTask () {
		super.resetTask ();

		this.currentPathEntity = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldExecute () {
		return (this.entity.isEntityAlive () && this.entity.worldObj != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTask () {
		// do attack stuffz
		if (this.entity.getAttackTarget () != null) {
			// verify whether the last entity died
			if (!this.entity.getAttackTarget ().isEntityAlive ()) this.entity.setAttackTarget (null);

			// check whether we need to move the entity to be able to attack it
			if (this.entity.getEntitySenses().canSee(this.entity.getAttackTarget ()) && this.entity.getDistanceSqToEntity (this.entity.getAttackTarget ()) <= SecurityBotEntity.MAXIMUM_GUN_DISTANCE) {
				// attack entity (whatever it is ... I don't really care but it must die!!!!

				// tick down until next attack
				this.attackTick = Math.max(this.attackTick - 1, 0);

				// execute attack
				if (this.attackTick <= 0)
					this.entity.attackEntityAsMob (this.entity.getAttackTarget ());
			} else
				this.entity.getNavigator ().tryMoveToEntityLiving (this.entity.getAttackTarget (), SecurityBotEntity.MOVEMENT_SPEED);
		} else if (!this.waitingForResponse) {
			// find entities around
			List<EntityLivingBase> entityList = this.entity.worldObj.getEntitiesWithinAABB (EntityLivingBase.class, this.entity.getBoundingBox ().expand (SecurityBotEntity.MAXIMUM_GUN_DISTANCE, SecurityBotEntity.MAXIMUM_GUN_DISTANCE, SecurityBotEntity.MAXIMUM_GUN_DISTANCE));

			// skip empty results
			if (entityList.size () == 0) return;

			// sort list
			Collections.sort (entityList, new EntityComparator ());

			// send a list of entities to hub to ask for more information
			AttackOrderRequestPacket packet = new AttackOrderRequestPacket (this.entity, entityList);

			// send packet
			this.entity.sendPacket (packet);

			// set waiting
			this.waitingForResponse = true;
		}
	}

	/**
	 * Allows sorting entities based on their distance.
	 */
	public class EntityComparator implements Comparator<Entity> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare (Entity o1, Entity o2) {
			// prefer players
			if (o1 instanceof EntityPlayer && !(o2 instanceof EntityPlayer)) return 1;
			if (o2 instanceof EntityPlayer && !(o1 instanceof EntityPlayer)) return -1;

			// check distance
			double distance0 = entity.getDistanceSqToEntity (o1);
			double distance1 = entity.getDistanceSqToEntity (o2);

			return (distance0 < distance1 ? -1 : (distance0 > distance1 ? 1 : 0));
		}
	}
}