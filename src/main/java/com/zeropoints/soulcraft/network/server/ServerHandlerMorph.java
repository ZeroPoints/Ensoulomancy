package com.zeropoints.soulcraft.network.server;

import com.zeropoints.soulcraft.api.morphs.MorphAPI;
import com.zeropoints.soulcraft.network.common.PacketMorph;
import net.minecraft.entity.player.EntityPlayerMP;

public class ServerHandlerMorph extends ServerMessageHandler<PacketMorph> {
    @Override
    public void run(EntityPlayerMP player, PacketMorph message) {
        MorphAPI.morph(player, message.morph);
    }
}