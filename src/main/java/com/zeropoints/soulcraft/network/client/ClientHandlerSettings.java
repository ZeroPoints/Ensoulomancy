package com.zeropoints.soulcraft.network.client;

import com.zeropoints.soulcraft.api.morphs.MorphManager;
import com.zeropoints.soulcraft.network.common.PacketSettings;
import net.minecraft.client.entity.EntityPlayerSP;

public class ClientHandlerSettings extends ClientMessageHandler<PacketSettings> {
    @Override
    public void run(EntityPlayerSP player, PacketSettings message) {
        MorphManager.INSTANCE.setActiveSettings(message.settings);
    }
}