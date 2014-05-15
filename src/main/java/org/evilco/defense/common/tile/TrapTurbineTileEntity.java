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
package org.evilco.defense.common.tile;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import java.util.List;

/**
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class TrapTurbineTileEntity extends TileEntity {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity () {
		// get current redstone level (skip if a current is applied)
		if (this.worldObj.getStrongestIndirectPower (this.xCoord, this.yCoord, this.zCoord) > 0) return;

		// get entities in the way
		List<Entity> entityList = this.worldObj.getEntitiesWithinAABB (Entity.class, this.getBoundingBox ());

		// iterate over entites
		for (Entity entity : entityList) {
			// TODO: Maybe not bother to move players in creative?

			// get direction vector
			Vec3 directionVector = this.worldObj.getWorldVec3Pool ().getVecFromPool ((entity.posX - this.xCoord), (entity.posY - this.yCoord), (entity.posZ - this.zCoord));

			// calculate force
			double force = (directionVector.lengthVector () * 0.5f);

			// apply force
			Vec3 normal = directionVector.normalize ();
			entity.motionX += (force * normal.xCoord);
			entity.motionZ += (force * normal.zCoord);
		}
	}

	/**
	 * Returns the fan bounding box.
	 * @return
	 */
	public AxisAlignedBB getBoundingBox () {
		return AxisAlignedBB.getBoundingBox (xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 10);
	}
}