package com.zeropoints.soulcraft.util.handlers;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.player.ISoulpool;
import com.zeropoints.soulcraft.player.PlayerDataProvider;
import com.zeropoints.soulcraft.renderer.entity.mobs.EntityImp;
import com.zeropoints.soulcraft.renderer.entity.mobs.RenderImp;
import com.zeropoints.soulcraft.renderer.player.RenderGhostPlayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntity.S15PacketEntityRelMove;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class PlayerHandler {

	@SubscribeEvent 
	public void onPlayerLogsIn(PlayerLoggedInEvent event) { 
		EntityPlayer player = event.player; 
		ISoulpool soul = player.getCapability(PlayerDataProvider.SOULPOOL_CAPABILITY, null); 
	
		if(soul == null) {
			Main.log(Level.INFO, "You have no Soul");
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
			Main.log(Level.INFO, "You have no Soul");
			return;
		}
		
		soul.add(50); 
		
		String message = String.format("You received 50 souls, you have %1$d soul.", soul.get()); 
		player.sendMessage(new TextComponentString(message)); 
	}
	
	/*
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Pre e) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.35F);
		Minecraft.getMinecraft().getRenderManager().setRenderShadow(false);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerPostRender(RenderPlayerEvent.Post e){
		Minecraft.getMinecraft().getRenderManager().setRenderShadow(true);
	}
	*/
	
}
