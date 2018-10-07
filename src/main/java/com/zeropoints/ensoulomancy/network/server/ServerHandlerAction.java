package com.zeropoints.ensoulomancy.network.server;

//import com.zeropoints.ensoulomancy.api.morphs.AbstractMorph;
//import com.zeropoints.ensoulomancy.api.morphs.events.MorphActionEvent;
//import com.zeropoints.ensoulomancy.capabilities.morphing.IMorphing;
//import com.zeropoints.ensoulomancy.capabilities.morphing.Morphing;
import com.zeropoints.ensoulomancy.network.common.PacketAction;

import net.minecraft.entity.player.EntityPlayerMP;

public class ServerHandlerAction extends ServerMessageHandler<PacketAction> {
    @Override
    public void run(EntityPlayerMP player, PacketAction message) {
        /*
    	IMorphing capability = Morphing.getCapability(player);

        if (capability != null && capability.isMorphed()) {
            AbstractMorph morph = capability.getCurrentMorph();

            morph.action(player);
            MinecraftForge.EVENT_BUS.post(new MorphActionEvent(player, morph.settings.action, morph));
        }
        */
    }
}