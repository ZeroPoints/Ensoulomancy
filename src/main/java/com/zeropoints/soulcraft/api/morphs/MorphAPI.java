package com.zeropoints.soulcraft.api.morphs;

import com.zeropoints.soulcraft.api.morphs.events.MorphEvent;
import com.zeropoints.soulcraft.api.morphs.AbstractMorph;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import com.zeropoints.soulcraft.network.Dispatcher;
import com.zeropoints.soulcraft.network.common.PacketMorph;
import com.zeropoints.soulcraft.network.common.PacketMorphPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;

/**
 * Morph API class
 * 
 * This class provides public API for morphing the player. Let me know which 
 * methods I may add to simplify your life :D
 * 
 * Use this API on the server side, please. Thanks!
 */
public class MorphAPI {
	
    /**
     * Demorph given player 
     */
    public static boolean demorph(EntityPlayer player) {
        return morph(player, null);
    }

    /**
     * Morph a player into given morph with given force flag. 
     * 
     * @return true, if player was morphed successfully
     */
    public static boolean morph(EntityPlayer player, AbstractMorph morph) {
        IMorphing morphing = Morphing.getCapability(player);

        if (morphing == null) {
            return false;
        }

        MorphEvent.Pre event = new MorphEvent.Pre(player, morph);

        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        }

        boolean morphed = morphing.setCurrentMorph(event.morph, player);

        if (!player.world.isRemote && morphed) {
            Dispatcher.sendTo(new PacketMorph(morph), (EntityPlayerMP) player);
            Dispatcher.updateTrackers(player, new PacketMorphPlayer(player.getEntityId(), morph));
        }

        if (morphed) {
            MinecraftForge.EVENT_BUS.post(new MorphEvent.Post(player, event.morph));
        }

        return morphed;
    }

}