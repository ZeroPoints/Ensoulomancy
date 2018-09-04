package com.zeropoints.ensoulomancy.network.client;

import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;

import net.minecraft.client.entity.EntityPlayerSP;

public class ClientHandlerGhost extends ClientMessageHandler<PacketGhost> {
	@Override
	public void run(EntityPlayerSP player, PacketGhost message) {
		Ghost.getCapability(player).setSettings(message.settings);
	}
}