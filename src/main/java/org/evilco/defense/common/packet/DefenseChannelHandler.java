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
package org.evilco.defense.common.packet;

import com.google.common.base.Preconditions;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import org.evilco.defense.DefenseMod;
import org.evilco.defense.client.gui.generic.DefenseStationGui;
import org.evilco.defense.common.tile.generic.DefenseStationTileEntity;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Handles mod related packets.
 * @author 		Johannes Donath <johannesd@evil-co.com>
 * @copyright		Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@ChannelHandler.Sharable
public class DefenseChannelHandler extends MessageToMessageCodec<FMLProxyPacket, AbstractDefensePacket> {

	/**
	 * Defines the channel name.
	 */
	public static final String CHANNEL_NAME = "baseDefense";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void encode (ChannelHandlerContext ctx, AbstractDefensePacket msg, List<Object> out) throws Exception {
		ByteBuf target = Unpooled.buffer ();

		// find packet identifer
		int packetID = DefensePacketType.valueOf (msg.getClass ());

		// write packetID
		target.writeInt (packetID);

		// create packet buffer
		ByteBuf packetBuffer = target.alloc ().buffer ();

		// write packet
		msg.write (packetBuffer);

		// write packet data
		target.writeBytes (packetBuffer);

		// add output
		out.add (new FMLProxyPacket (target.copy (), CHANNEL_NAME));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decode (ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
		ByteBuf source = msg.payload ();

		// read packetID
		int packetID = source.readInt ();

		// find packet type
		DefensePacketType packetType = DefensePacketType.valueOf (packetID);

		// verify type
		Preconditions.checkNotNull (packetType, "packetType");

		// get class
		Class<? extends AbstractDefensePacket> packetClass = packetType.packetType;

		// find constructor
		Constructor<? extends AbstractDefensePacket> packetConstructor = packetClass.getConstructor (ByteBuf.class);

		// construct packet
		AbstractDefensePacket packet = packetConstructor.newInstance (source);

		// handle packet
		// FIXME: WTF Forge?! Do you even know how to netty?!
		if (packet instanceof DefenseStationRegisterUserPacket) {
			// verify side
			if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT) throw new Exception ("Cannot receive server related packet.");

			// cast packet
			DefenseStationRegisterUserPacket userPacket = ((DefenseStationRegisterUserPacket) packet);

			// get net handler
			NetHandlerPlayServer netHandler = ((NetHandlerPlayServer) ctx.channel ().attr (NetworkRegistry.NET_HANDLER).get ());
			EntityPlayer player = netHandler.playerEntity;

			// get TE
			TileEntity tileEntity = player.getEntityWorld ().getTileEntity (userPacket.getX (), userPacket.getY (), userPacket.getZ ());

			// check TE type
			if (!(tileEntity instanceof DefenseStationTileEntity)) {
				// forcefully close screen
				player.closeScreen ();

				// stop execution (we will not parse the packet)
				return;
			}

			// cast TE
			DefenseStationTileEntity defenseStationTileEntity = ((DefenseStationTileEntity) tileEntity);

			// verify owner
			if (defenseStationTileEntity.getOwner () == null || !defenseStationTileEntity.getOwner ().equals (player.getPersistentID ())) {
				// forcefully close screen
				player.closeScreen ();

				// stop execution (we will not parse the packet)
				return;
			}

			// find user
			EntityPlayerMP entity = FMLCommonHandler.instance ().getMinecraftServerInstance ().getConfigurationManager ().getPlayerForUsername (userPacket.getUsername ()); // FIXME: Allow offline users to be added ...

			// verify entity
			if (entity == null) {
				// notify client
				DefenseMod.instance.channels.get (Side.SERVER).attr (FMLOutboundHandler.FML_MESSAGETARGET).set (FMLOutboundHandler.OutboundTarget.PLAYER);
				DefenseMod.instance.channels.get (Side.SERVER).attr (FMLOutboundHandler.FML_MESSAGETARGETARGS).set (player);
				DefenseMod.instance.channels.get (Side.SERVER).writeAndFlush (new DefenseStationRegisterUserErrorPacket ());

				// stop parsing here
				return;
			}

			// add user
			defenseStationTileEntity.getKnownUsers ().put (entity.getPersistentID (), entity.getDisplayName ());

			// force entity update
			player.getEntityWorld ().markTileEntityChunkModified (defenseStationTileEntity.xCoord, defenseStationTileEntity.yCoord, defenseStationTileEntity.zCoord, defenseStationTileEntity);
			player.getEntityWorld ().markBlockForUpdate (defenseStationTileEntity.xCoord, defenseStationTileEntity.yCoord, defenseStationTileEntity.zCoord);

			// send success packet
			DefenseMod.instance.channels.get (Side.SERVER).attr (FMLOutboundHandler.FML_MESSAGETARGET).set (FMLOutboundHandler.OutboundTarget.PLAYER);
			DefenseMod.instance.channels.get (Side.SERVER).attr (FMLOutboundHandler.FML_MESSAGETARGETARGS).set (player);
			DefenseMod.instance.channels.get (Side.SERVER).writeAndFlush (new DefenseStationRegisterUserSuccessPacket ());
		} else if (packet instanceof DefenseStationRegisterUserErrorPacket) {
			// verify side
			if (FMLCommonHandler.instance ().getEffectiveSide () != Side.CLIENT) throw new Exception ("Cannot receive client related packet.");

			// find current screen
			GuiScreen screen = Minecraft.getMinecraft ().currentScreen;

			// check screen (ignore lagging packets)
			if (!(screen instanceof DefenseStationGui)) return;

			// set color
			((DefenseStationGui) screen).setError (true);
		} else if (packet instanceof DefenseStationRegisterUserSuccessPacket) {
			// verify side
			if (FMLCommonHandler.instance ().getEffectiveSide () != Side.CLIENT) throw new Exception ("Cannot receive client related packet.");

			// find current screen
			GuiScreen screen = Minecraft.getMinecraft ().currentScreen;

			// check screen (ignore lagging packets)
			if (!(screen instanceof DefenseStationGui)) return;

			// set color
			((DefenseStationGui) screen).reset ();
		}

		// construct new instance
		out.add (packet);
	}
}