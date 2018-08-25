package com.zeropoints.soulcraft.capabilities;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter.Result;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.api.DefaultSettings;
import com.zeropoints.soulcraft.api.ghost.GhostSettings;
import com.zeropoints.soulcraft.api.morphs.MorphManager;
import com.zeropoints.soulcraft.api.morphs.MorphSettings;
import com.zeropoints.soulcraft.capabilities.ghost.Ghost;
import com.zeropoints.soulcraft.capabilities.ghost.GhostProvider;
import com.zeropoints.soulcraft.capabilities.ghost.IGhost;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import com.zeropoints.soulcraft.capabilities.morphing.MorphingProvider;
import com.zeropoints.soulcraft.capabilities.soulpool.ISoulpool;
import com.zeropoints.soulcraft.capabilities.soulpool.Soulpool;
import com.zeropoints.soulcraft.capabilities.soulpool.SoulpoolProvider;
import com.zeropoints.soulcraft.network.Dispatcher;
import com.zeropoints.soulcraft.network.common.PacketMorph;
import com.zeropoints.soulcraft.network.common.PacketSettings;
import com.zeropoints.soulcraft.util.Reference;
import com.zeropoints.soulcraft.world.PurgatoryWorldType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class CapabilityHandler {
	
    public static final ResourceLocation SOULPOOL_CAPABILITY = new ResourceLocation(Reference.MOD_ID, "soulpool_capability");
    public static final ResourceLocation MORPHING_CAPABILITY = new ResourceLocation(Reference.MOD_ID, "morphing_capability");
    public static final ResourceLocation GHOST_CAPABILITY = new ResourceLocation(Reference.MOD_ID, "ghost_capability");
    
    /**
     * Attach capabilities
     */
    @SubscribeEvent
    public void addCapability(AttachCapabilitiesEvent<Entity> event) {
    	if (!(event.getObject() instanceof EntityPlayer)) return;
    	
        event.addCapability(SOULPOOL_CAPABILITY, new SoulpoolProvider());
        event.addCapability(MORPHING_CAPABILITY, new MorphingProvider());
        event.addCapability(GHOST_CAPABILITY, new GhostProvider());
    }
    
    /**
     * Called when the player logs into the server
     * Handles assignment of soulpool, morphing and ghost form capabilities
     */
	@SubscribeEvent 
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {
		
		EntityPlayer player = event.player; 
		Map<String,MorphSettings> morphSettings = new HashMap<String,MorphSettings>();
		GhostSettings ghostSettings = null;
		boolean sendSettingsFlag = false;
		
		// Soulpool
		ISoulpool soulpool = Soulpool.getCapability(player); 
	
		if (soulpool == null) {
			Main.log(Level.INFO, "You have no Soul");
			return;
		}
		String message = String.format("You have %1$d soul.", soulpool.get()); 
		player.sendMessage(new TextComponentString(message)); 
		

		// Morphing
        IMorphing morph = Morphing.getCapability(player);
        if (morph != null) {
            this.getCurrentMorph(morph, player);

            // Ensure that player was morphed 
            if (morph.isMorphed()) {
            	morph.getCurrentMorph().morph(player);
            }

            sendSettingsFlag = true;
            morphSettings = MorphManager.INSTANCE.activeSettings;
        }
        
        
        // Ghost
    	IGhost ghost = Ghost.getCapability(player);
    	if (ghost != null && ghost.isGhost()) {
    		player.sendMessage(new TextComponentString("You are a ghost"));
    		
    		sendSettingsFlag = true;
    		ghostSettings = ghost.getSettings();
    	}
    	
    	// Send settings to client
    	if (sendSettingsFlag) {
    		Dispatcher.sendTo(new PacketSettings(morphSettings, ghostSettings), (EntityPlayerMP) player);
    	}
	} 
	
	/**
	 * When player sleeps in bed. Ghost it up
	 */
	@SubscribeEvent 
	public void onPlayerSleep(PlayerSleepInBedEvent event) { 
		
		EntityPlayer player = event.getEntityPlayer();
		
		if (player.world.isRemote) { 
			return;
		}
		
		ISoulpool soul = Soulpool.getCapability(player); 
		String message;
		
		if (soul == null) {
			message = "You have no Soul";
		}
		else {
			message = String.format("You have %1$d Soul.", soul.get());
		}
		
		player.sendMessage(new TextComponentString(message)); 
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
                IMorphing morphing = Morphing.getCapability(player);
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
        IMorphing morphing = Morphing.getCapability(player);
        IMorphing oldMorphing = Morphing.getCapability(event.getOriginal());

        if (!event.isWasDeath()) {
            morphing.copy(oldMorphing, player);
        }
    }

    /**
     * Send currently morphed morph to the given player. 
     */
    private void getCurrentMorph(IMorphing morph, EntityPlayer player) {
        Dispatcher.sendTo(new PacketMorph(morph.getCurrentMorph()), (EntityPlayerMP)player);
    }	
	
}

