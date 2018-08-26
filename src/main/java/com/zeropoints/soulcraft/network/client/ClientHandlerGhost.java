package com.zeropoints.soulcraft.network.client;

import com.zeropoints.soulcraft.capabilities.ghost.Ghost;
import com.zeropoints.soulcraft.network.common.PacketGhost;

import net.minecraft.client.entity.EntityPlayerSP;

public class ClientHandlerGhost extends ClientMessageHandler<PacketGhost> {
	@Override
	public void run(EntityPlayerSP player, PacketGhost message) {
		Ghost.getCapability(player).setSettings(message.settings);
	}
}