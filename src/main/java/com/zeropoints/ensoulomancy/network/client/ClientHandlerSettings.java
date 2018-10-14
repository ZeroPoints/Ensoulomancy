package com.zeropoints.ensoulomancy.network.client;


import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
import com.zeropoints.ensoulomancy.network.common.PacketSettings;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHandlerSettings extends ClientMessageHandler<PacketSettings> {
    @Override
    @SideOnly(Side.CLIENT)
    public void run(EntityPlayerSP player, PacketSettings message) {
    	

		if (message.ghostSettings != null) {
			// Save some settings here
			IGhost ghost = Ghost.getCapability(player);
			ghost.setSettings(message.ghostSettings);
		}
    }
}