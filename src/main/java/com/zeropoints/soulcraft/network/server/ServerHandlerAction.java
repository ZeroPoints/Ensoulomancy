package com.zeropoints.soulcraft.network.server;

import com.zeropoints.soulcraft.api.morphs.events.MorphActionEvent;
import com.zeropoints.soulcraft.api.morphs.AbstractMorph;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import com.zeropoints.soulcraft.network.common.PacketAction;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;

public class ServerHandlerAction extends ServerMessageHandler<PacketAction> {
    @Override
    public void run(EntityPlayerMP player, PacketAction message) {
        IMorphing capability = Morphing.getCapability(player);

        if (capability != null && capability.isMorphed()) {
            AbstractMorph morph = capability.getCurrentMorph();

            morph.action(player);
            MinecraftForge.EVENT_BUS.post(new MorphActionEvent(player, morph.settings.action, morph));
        }
    }
}