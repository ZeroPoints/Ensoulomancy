package com.zeropoints.soulcraft.network.client;

import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import com.zeropoints.soulcraft.network.common.PacketMorph;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHandlerMorph extends ClientMessageHandler<PacketMorph> {
    @Override
    @SideOnly(Side.CLIENT)
    public void run(EntityPlayerSP player, PacketMorph message) {
        IMorphing capability = Morphing.get(player);

        if (capability != null) {
            capability.setCurrentMorph(message.morph, player);
        }
    }
}