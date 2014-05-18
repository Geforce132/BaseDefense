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

import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.evilco.defense.DefenseMod;
import org.evilco.defense.common.packet.DefenseStationRegisterUserPacket;
import org.evilco.defense.common.packet.DefenseStationUnregisterPacket;
import org.evilco.defense.common.tile.generic.DefenseStationTileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class DefenseStationGui extends GuiScreen implements ReturnAwareGuiTextField.ReturnListener {

	/**
	 * Defines the dialog width.
	 */
	protected static final int WIDTH = 176;

	/**
	 * Defines the dialog height.
	 */
	protected static final int HEIGHT = 196;

	/**
	 * Stores the defense station tile entity.
	 */
	protected DefenseStationTileEntity tileEntity = null;

	/**
	 * Defines the list offset.
	 */
	protected int listOffset = 0;

	/**
	 * Indicates whether the input has an error.
	 */
	protected boolean inputError = false;

	/**
	 * Stores the gui text field.
	 */
	protected GuiTextField inputField;

	/**
	 * Constructs a new DefenseStationGui.
	 * @param tileEntity The parent tile entity.
	 */
	public DefenseStationGui (DefenseStationTileEntity tileEntity) {
		super ();

		this.tileEntity = tileEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initGui () {
		super.initGui ();

		// calculate start location
		int x = ((this.width - WIDTH) / 2);
		int y = ((this.height - HEIGHT) / 2);

		// create input field
		this.inputField = new ReturnAwareGuiTextField (this.fontRendererObj, (x + 8), (y + 179), 161, 10, this);
		this.inputField.setFocused (true);
		this.inputField.setCanLoseFocus (false);
		this.inputField.setEnableBackgroundDrawing (false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawScreen (int par1, int par2, float par3) {
		// draw background
		this.drawDefaultBackground ();

		// calculate start location
		int x = ((this.width - WIDTH) / 2);
		int y = ((this.height - HEIGHT) / 2);

		// draw gui name
		this.fontRendererObj.drawString (LanguageRegistry.instance ().getStringLocalization ("defense.generic.defenseStation.title"), x, (y - 12), 0xFFFFFF);

		// draw known users
		Iterator<Map.Entry<UUID, String>> knownUserIterator = this.tileEntity.getKnownUsers ().entrySet ().iterator ();

		// forward to correct position
		for (int i = 1; i < this.listOffset; i++) knownUserIterator.next ();

		// initialize draw offset
		int drawOffset = 0;

		while (knownUserIterator.hasNext ()) {
			// get user
			Map.Entry<UUID, String> knownUser = knownUserIterator.next ();

			// render name
			this.fontRendererObj.drawString (knownUser.getValue (), (x + 10), (y + 10 + (drawOffset * 12)), 0xFFFFFF);

			// increment the draw offset
			drawOffset++;
		}

		// draw text field
		this.inputField.drawTextBox ();
	}


	@Override
	public void drawDefaultBackground () {
		super.drawDefaultBackground ();

		// set OpenGL color
		GL11.glColor4f (1.0f, 1.0f, 1.0f, 1.0f);

		// bind texture
		this.mc.renderEngine.bindTexture (new ResourceLocation ("defense", "textures/gui/defenseStationBackground.png"));

		// calculate start position
		int x = ((this.width - WIDTH) / 2);
		int y = ((this.height - HEIGHT) / 2);

		// draw texture
		this.drawTexturedModalRect (x, y, 0, 0, WIDTH, HEIGHT);
	}

	/**
	 * Sets the error.
	 * @param error The error.
	 */
	public void setError (boolean error) {
		if (error)
			this.inputField.setTextColor (0xFF0000);
		else
			this.inputField.setTextColor (0xFFFFFF);

		// store error
		this.inputError = error;
	}

	/***
	 * {@inheritDoc}
	 */
	@Override
	protected void keyTyped (char par1, int par2) {
		super.keyTyped (par1, par2);

		this.inputField.textboxKeyTyped (par1, par2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void mouseClicked (int par1, int par2, int par3) {
		super.mouseClicked (par1, par2, par3);

		// pass event to input field
		this.inputField.mouseClicked (par1, par2, par3);

		// calculate start location
		int x = ((this.width - WIDTH) / 2) + 10;
		int y = ((this.height - HEIGHT) / 2) + 10;

		// ignore events outside of the box
		if (par1 < (x + 10) || par1 > (x + 168) || par2 < y || par2 > (y + 169)) return;

		int offsetY = 0;
		Iterator<Map.Entry<UUID, String>> iterator = this.tileEntity.getKnownUsers ().entrySet ().iterator ();

		while (iterator.hasNext ()) {
			int correctY = (y + (offsetY * 12));

			// skip if out of bounds
			if (correctY >= (y + 169)) break;

			// get selected user
			Map.Entry<UUID, String> element = iterator.next ();

			// verify position
			if (par2 >= correctY && par2 <= (correctY + 8)) {
				// send packet to server
				DefenseMod.instance.channels.get (Side.CLIENT).attr (FMLOutboundHandler.FML_MESSAGETARGET).set (FMLOutboundHandler.OutboundTarget.TOSERVER);
				DefenseMod.instance.channels.get (Side.CLIENT).writeOutbound (new DefenseStationUnregisterPacket (element.getKey (), this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord));
				break;
			}

			offsetY++;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onChange () {
		if (this.inputError) this.setError (false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onReturn () {
		// get text
		String text = this.inputField.getText ();

		// send packet to server
		DefenseMod.instance.channels.get (Side.CLIENT).attr (FMLOutboundHandler.FML_MESSAGETARGET).set (FMLOutboundHandler.OutboundTarget.TOSERVER);
		DefenseMod.instance.channels.get (Side.CLIENT).writeOutbound (new DefenseStationRegisterUserPacket (text, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord));
	}

	/**
	 * Resets the input.
	 */
	public void reset () {
		this.inputField.setText ("");
	}
}