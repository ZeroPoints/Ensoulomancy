package com.zeropoints.ensoulomancy.network.server;

import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;

import net.minecraft.entity.player.EntityPlayerMP;

public class ServerHandlerGhost extends ServerMessageHandler<PacketGhost> {
	@Override
	public void run(EntityPlayerMP player, PacketGhost message) {
		IGhost ghost = Ghost.getCapability(player);
		if (ghost != null) {
			ghost.setSettings(message.settings);
			
			if (message.settings.isGhost) {
				ghost.becomeGhost(player);
			}
			else {
				ghost.deGhost(player);
			}
		}
	}
}