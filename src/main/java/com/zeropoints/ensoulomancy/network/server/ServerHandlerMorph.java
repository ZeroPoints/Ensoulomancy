package com.zeropoints.ensoulomancy.network.server;

import com.zeropoints.ensoulomancy.api.morphs.MorphAPI;
import com.zeropoints.ensoulomancy.network.common.PacketMorph;

import net.minecraft.entity.player.EntityPlayerMP;

public class ServerHandlerMorph extends ServerMessageHandler<PacketMorph> {
    @Override
    public void run(EntityPlayerMP player, PacketMorph message) {
        MorphAPI.morph(player, message.morph);
    }
}