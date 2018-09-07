package com.zeropoints.ensoulomancy.network.client;

import com.zeropoints.ensoulomancy.capabilities.morphing.IMorphing;
import com.zeropoints.ensoulomancy.capabilities.morphing.MorphingProvider;
import com.zeropoints.ensoulomancy.network.common.PacketMorphPlayer;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHandlerMorphPlayer extends ClientMessageHandler<PacketMorphPlayer>
{
    @Override
    @SideOnly(Side.CLIENT)
    public void run(EntityPlayerSP player, PacketMorphPlayer message) {
        Entity entity = player.world.getEntityByID(message.id);
        IMorphing capability = entity.getCapability(MorphingProvider.MORPHING_CAPABILITY, null);

        if (capability != null) {
            capability.setCurrentMorph(message.morph, (EntityPlayer) entity);
        }
    }
}