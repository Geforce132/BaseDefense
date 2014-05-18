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
package org.evilco.defense.client.gui.generic;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class ReturnAwareGuiTextField extends GuiTextField {

	/**
	 * Stores the return listener.
	 */
	protected ReturnListener listener = null;

	/**
	 * Constructs a new ReturnAwareGuiTextField.
	 * @param par1FontRenderer
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param par5
	 */
	public ReturnAwareGuiTextField (FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5, ReturnListener listener) {
		super (par1FontRenderer, par2, par3, par4, par5);

		this.listener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean textboxKeyTyped (char p_146201_1_, int p_146201_2_) {
		// catch return
		if (p_146201_2_ == 28) {
			this.listener.onReturn ();
			return true;
		}

		// change
		boolean value = super.textboxKeyTyped (p_146201_1_, p_146201_2_);

		// notify about change
		this.listener.onChange ();

		// return correct value
		return value;
	}

	/**
	 * Allows listening to the enter key.
	 */
	public static interface ReturnListener {

		/**
		 * Callback for changes.
		 */
		public void onChange ();

		/**
		 * Callback for the return key.
		 */
		public void onReturn ();
	}
}