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
package org.evilco.mc.defense.common.event;

import com.google.common.collect.ImmutableMap;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class CapeHandler {

	/**
	 * Stores all known textures.
	 */
	protected static final Map<String, ResourceLocation> skins = ((new ImmutableMap.Builder<String, ResourceLocation> ())
		.put ("LordLorkin", new ResourceLocation ("defense:textures/capes/team.png"))
	).build ();

	/**
	 * Stores a list of already processed players.
	 */
	protected ArrayList<AbstractClientPlayer> playerList = new ArrayList<AbstractClientPlayer> ();

	/**
	 * Handles pre-rendering.
	 * @param event The event.
	 */
	@SubscribeEvent
	public void onPreRenderSpecials (RenderPlayerEvent.Specials.Pre event) {
		// skip fake players, etc.
		if (!(event.entityPlayer instanceof AbstractClientPlayer)) return;

		// cast
		AbstractClientPlayer player = ((AbstractClientPlayer) event.entityPlayer);

		// verify processing queue
		if (this.playerList.contains (player)) return;

		// check map
		if (!skins.containsKey (player.getDisplayName ())) return;

		// get resource location
		ResourceLocation location = skins.get (player.getDisplayName ());

		// set resource
		try {
			// get resource
			IResource resource = Minecraft.getMinecraft ().getResourceManager ().getResource (location);

			// open stream
			InputStream inputStream = resource.getInputStream ();

			// get image
			BufferedImage image = ImageIO.read (inputStream);

			// set cape
			player.getTextureCape ().setBufferedImage (image);
		} catch (IOException ex) {
			ex.printStackTrace ();
		} finally {
			this.playerList.add (player);
		}
	}
}