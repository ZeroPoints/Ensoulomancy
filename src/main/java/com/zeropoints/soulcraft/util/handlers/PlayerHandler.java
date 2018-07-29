package com.zeropoints.soulcraft.util.handlers;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.player.ISoulpool;
import com.zeropoints.soulcraft.player.PlayerDataProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;


public class PlayerHandler {

	@SubscribeEvent 
	public void onPlayerLogsIn(PlayerLoggedInEvent event) { 
		EntityPlayer player = event.player; 
		ISoulpool soul = player.getCapability(PlayerDataProvider.SOULPOOL_CAPABILITY, null); 
	
		if(soul == null) {
			Main.LogMesssage("Login", "You have no Soul");
			return;
		}
		String message = String.format("You have %1$d soul.", soul.get()); 
		player.sendMessage(new TextComponentString(message)); 
	} 
	

	@SubscribeEvent 
	public void onPlayerSleep(PlayerSleepInBedEvent event) { 
		EntityPlayer player = event.getEntityPlayer(); 
		
		if (player.world.isRemote) return; 
		
		ISoulpool soul = player.getCapability(PlayerDataProvider.SOULPOOL_CAPABILITY, null); 
		
		if(soul == null) {
			Main.LogMesssage("Login", "You have no Soul");
			return;
		}
		
		soul.add(50); 
		
		String message = String.format("You received 50 souls, you have %1$d soul.", soul.get()); 
		player.sendMessage(new TextComponentString(message)); 
	}
	
}
