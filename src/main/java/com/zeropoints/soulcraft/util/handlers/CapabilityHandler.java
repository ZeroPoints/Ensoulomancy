package com.zeropoints.soulcraft.util.handlers;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.api.morphs.MorphManager;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import com.zeropoints.soulcraft.capabilities.morphing.MorphingProvider;
import com.zeropoints.soulcraft.network.Dispatcher;
import com.zeropoints.soulcraft.network.common.PacketMorph;
import com.zeropoints.soulcraft.network.common.PacketMorphPlayer;
import com.zeropoints.soulcraft.network.common.PacketSettings;
import com.zeropoints.soulcraft.player.PlayerDataProvider;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;


public class CapabilityHandler {
	
    public static final ResourceLocation SOULPOOL_CAPABILITY = new ResourceLocation(Reference.MOD_ID, "_soulpool_capability");
    public static final ResourceLocation MORPHING_CAPABILITY = new ResourceLocation(Reference.MOD_ID, "morphing_capability");
    
    /**
     * Attach capabilities
     */
    @SubscribeEvent
    public void addCapability(AttachCapabilitiesEvent<Entity> event) {
    	if (!(event.getObject() instanceof EntityPlayer)) return;
    	
        event.addCapability(SOULPOOL_CAPABILITY, new PlayerDataProvider());
        event.addCapability(MORPHING_CAPABILITY, new MorphingProvider());
    }    

    /**
     * When player logs in, sent him his server counter partner's values.
     */
    @SubscribeEvent
    public void playerLogsIn(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        IMorphing capability = Morphing.get(player);

        if (capability != null) {
            this.getCurrentMorph(capability, player);

            /* Ensure that player was morphed */
            if (capability.isMorphed()) {
            	capability.getCurrentMorph().morph(player);
            }

            /* Send data */
            Dispatcher.sendTo(new PacketSettings(MorphManager.INSTANCE.activeSettings), (EntityPlayerMP) player);
        }
    }

    /**
     * When player starts tracking another player, server has to send its
     * morphing values.
     */
    @SubscribeEvent
    public void playerStartsTracking(StartTracking event) {
        if (event.getTarget() instanceof EntityPlayer) {
            Entity target = event.getTarget();
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            IMorphing capability = target.getCapability(MorphingProvider.MORPHING_CAP, null);

            Dispatcher.sendTo(new PacketMorphPlayer(target.getEntityId(), capability.getCurrentMorph()), player);
        }
    }

    /**
     * On player's spawn in the world (when player travels in other dimension 
     * and spawns there or when player dies and then respawns).
     * 
     * This method is responsible for sending morphing data on the client.
     */
    @SubscribeEvent
    public void onPlayerSpawn(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();

            if (!player.world.isRemote) {
                IMorphing morphing = Morphing.get(player);
                this.getCurrentMorph(morphing, player);
            }
        }
    }

    /**
     * Copy data from dead player (or player returning from end) to the new player
     */
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        IMorphing morphing = Morphing.get(player);
        IMorphing oldMorphing = Morphing.get(event.getOriginal());

        if (!event.isWasDeath()) {
            morphing.copy(oldMorphing, player);
        }
    }

    /**
     * Send currently morphed morph to the given player. 
     */
    private void getCurrentMorph(IMorphing cap, EntityPlayer player) {
        Dispatcher.sendTo(new PacketMorph(cap.getCurrentMorph()), (EntityPlayerMP)player);
    }
}

