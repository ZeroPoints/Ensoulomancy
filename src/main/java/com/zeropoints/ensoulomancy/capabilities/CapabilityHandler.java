package com.zeropoints.ensoulomancy.capabilities;

import org.apache.logging.log4j.Level;
import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;
//import com.zeropoints.ensoulomancy.api.morphs.MorphManager;
//import com.zeropoints.ensoulomancy.api.morphs.MorphSettings;
import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.capabilities.ghost.GhostProvider;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
//import com.zeropoints.ensoulomancy.capabilities.morphing.IMorphing;
//import com.zeropoints.ensoulomancy.capabilities.morphing.Morphing;
//import com.zeropoints.ensoulomancy.capabilities.morphing.MorphingProvider;
import com.zeropoints.ensoulomancy.capabilities.soulpool.ISoulpool;
import com.zeropoints.ensoulomancy.capabilities.soulpool.Soulpool;
import com.zeropoints.ensoulomancy.capabilities.soulpool.SoulpoolProvider;
import com.zeropoints.ensoulomancy.items.armor.ArmorBase;
import com.zeropoints.ensoulomancy.network.Dispatcher;
//import com.zeropoints.ensoulomancy.network.common.PacketMorph;
import com.zeropoints.ensoulomancy.network.common.PacketSettings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;


public class CapabilityHandler {
	
    public static final ResourceLocation SOULPOOL_CAPABILITY = new ResourceLocation(Main.MOD_ID, "soulpool_capability");
    public static final ResourceLocation GHOST_CAPABILITY = new ResourceLocation(Main.MOD_ID, "ghost_capability");
    
    /**
     * Attach capabilities
     */
    @SubscribeEvent
    public void addCapability(AttachCapabilitiesEvent<Entity> event) {
    	if (!(event.getObject() instanceof EntityPlayer)) return;
    	
        event.addCapability(SOULPOOL_CAPABILITY, new SoulpoolProvider());
        event.addCapability(GHOST_CAPABILITY, new GhostProvider());
    }
    
    /**
     * Called when the player logs into the server
     * Handles assignment of soulpool, ghost form capabilities
     */
	@SubscribeEvent 
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {
		
		EntityPlayer player = event.player; 
		GhostSettings ghostSettings = new GhostSettings(); // A default of null was crashing the dispatcher sometimes
		
		// Soulpool
		ISoulpool soulpool = Soulpool.getCapability(player); 
	
		if (soulpool == null) {
			Main.log(Level.INFO, "You have no Soul");
			return;
		}
		String message = String.format("You have %1$d soul.", soulpool.get()); 
		player.sendMessage(new TextComponentString(message)); 
		
        
        // Ghost
    	IGhost ghost = Ghost.getCapability(player);
    	if (ghost != null) {
	    	if (ghost.isGhost()) {
	    		player.sendMessage(new TextComponentString("You are a ghost"));
	    	}
	    	
    		ghostSettings = ghost.getSettings();
    	}
    	
    	// Send settings to client
		Dispatcher.sendTo(new PacketSettings(ghostSettings), (EntityPlayerMP) player);
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
            }
        }
    }

    /**
     * Copy data from dead player (or player returning from end) to the new player
     */
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
       
        if (!event.isWasDeath()) {
        }
    }


	/**
	 * Every tick check player data...
	 */
	@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (!hasFlight(event.player)) {
			if (!event.player.isCreative()) {
				event.player.capabilities.allowFlying = false;
			}
			event.player.capabilities.setFlySpeed(0.05F);	
			return;
		}
		
		if (event.player.world.isRemote) {
			event.player.capabilities.allowFlying = true;
			event.player.capabilities.setFlySpeed(0.05F + (0.05F * 5 * (float)1D));	
		}
    }

	
	private static boolean hasFlight(EntityPlayer player) {
		// Gets players currently worn items
		NonNullList<ItemStack> armorInventory = player.inventory.armorInventory;

		// Find whether the player has a halo on for creative flight
		for (ItemStack itemStack : armorInventory) {
			if (!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorBase && ((ArmorBase)itemStack.getItem()).GRANTS_FLIGHT) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * When player damaged do checks if it was flight damage and they have flight gear
	 */
	@SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
		EntityLivingBase player = event.getEntityLiving();
		
		if (player instanceof EntityPlayer) {
			//Disable fall damage if player falling
			if (event.getSource().damageType.equals("fall") && hasFlight((EntityPlayer)player)) {
				event.setCanceled(true);
			}
		}
	}
	
	
}

