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

import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public abstract class PrimedExplosiveEntity extends Entity {

	/**
	 * Stores the fuse.
	 */
	@Getter
	@Setter
	private short fuse = 0;

	/**
	 * Constructs a new PrimedExplosiveEntity instance.
	 * @param p_i1582_1_ The world.
	 * @param fuse The explosive fuse.
	 */
	public PrimedExplosiveEntity (World p_i1582_1_, short fuse) {
		super (p_i1582_1_);
		this.fuse = fuse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getShadowSize () {
		return 0.0f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void entityInit () {
		// initialize motion
		float f = (float)(Math.random() * Math.PI * 2.0D);

		this.motionX = (Math.sin (f) * 0.02f * -1);
		this.motionY = 0.20000000298023224d;
		this.motionZ = (Math.sin (f) * 0.02f * -1);
	}

	/**
	 * Lets the explosive go kaboom.
	 */
	public void explode () {
		// mark entity as dead
		this.setDead ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUpdate () {
		super.onUpdate ();

		// apply forces
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033d;
		this.moveEntity (this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863d;
		this.motionY *= 0.9800000190734863d;
		this.motionZ *= 0.9800000190734863d;

		if (this.onGround) {
			this.motionX *= 0.699999988079071d;
			this.motionZ *= 0.699999988079071d;
			this.motionY *= -0.5d;
		}

		// decrease fuse and go kaboom
		if (this.fuse-- < 0) this.explode ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readEntityFromNBT (NBTTagCompound p_70037_1_) {
		this.fuse = p_70037_1_.getShort ("Fuse");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeEntityToNBT (NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort ("Fuse", this.fuse);
	}
}
