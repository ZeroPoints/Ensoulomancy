package com.zeropoints.soulcraft.network.client;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.zeropoints.soulcraft.api.DefaultSettings;
import com.zeropoints.soulcraft.api.morphs.MorphManager;
import com.zeropoints.soulcraft.api.morphs.MorphSettings;
import com.zeropoints.soulcraft.capabilities.ghost.Ghost;
import com.zeropoints.soulcraft.capabilities.ghost.IGhost;
import com.zeropoints.soulcraft.network.common.PacketSettings;
import net.minecraft.client.entity.EntityPlayerSP;

public class ClientHandlerSettings extends ClientMessageHandler<PacketSettings> {
    @Override
    public void run(EntityPlayerSP player, PacketSettings message) {
    	if (message.morphSettings != null) {
    		MorphManager.INSTANCE.setActiveSettings(message.morphSettings);    		
    	}
		if (message.ghostSettings != null) {
			// Save some settings here
			IGhost ghost = Ghost.getCapability(player);
			ghost.setSettings(message.ghostSettings);
		}
    }
}