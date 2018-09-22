package com.zeropoints.ensoulomancy.network.client;

import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHandlerGhost extends ClientMessageHandler<PacketGhost> {
	@Override
	@SideOnly(Side.CLIENT)
	public void run(EntityPlayerSP player, PacketGhost message) {
		Ghost.getCapability(player).setSettings(message.settings);
	}
}