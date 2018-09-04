package com.zeropoints.ensoulomancy.network.client;

import com.zeropoints.ensoulomancy.capabilities.morphing.IMorphing;
import com.zeropoints.ensoulomancy.capabilities.morphing.Morphing;
import com.zeropoints.ensoulomancy.network.common.PacketMorph;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHandlerMorph extends ClientMessageHandler<PacketMorph> {
    @Override
    @SideOnly(Side.CLIENT)
    public void run(EntityPlayerSP player, PacketMorph message) {
        IMorphing capability = Morphing.getCapability(player);

        if (capability != null) {
            capability.setCurrentMorph(message.morph, player);
        }
    }
}