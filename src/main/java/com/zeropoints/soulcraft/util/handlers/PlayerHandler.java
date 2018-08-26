package com.zeropoints.soulcraft.util.handlers;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.items.armor.Halo;
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
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntity.S15PacketEntityRelMove;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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
	
	
	
	
	@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

		//Gets this current player

        EntityPlayer player = event.player;
		
		
        GrantFlight(player);
    }

	
	
	
	
	
	public static void GrantFlight(EntityPlayer player) {
		
		boolean hasFlight = false;
		//Gets players items

		NonNullList<ItemStack> stack = player.inventory.armorInventory;
		//Find whether the player has a halo on for creative flight

		for(int i = 0; i < stack.size(); i++) {
			if(stack.get(i).isEmpty() || !(stack.get(i).getItem() instanceof Halo)) {
				
			}
			else {
				hasFlight = true;
			}
		}
		
		if(!hasFlight) {
			player.capabilities.allowFlying = false;
			return;
		}
		
		if (player.world.isRemote) {
			//GRANT FLIGHT AND SPEED
			player.capabilities.allowFlying = true;
			player.capabilities.setFlySpeed(0.05F + (0.05F * 5 * (float)1D));	
			
		}

		
	}
	
	
	
	@SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
		
		if(event.getEntityLiving() instanceof EntityPlayer) {

			//Gets this current player
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			
			//Gets players items
			NonNullList<ItemStack> stack = player.inventory.armorInventory;
			boolean hasFlight = false;

			//Find whether the player has a halo on for creative flight
			for(int i = 0; i < stack.size(); i++) {
				if(stack.get(i).isEmpty() || !(stack.get(i).getItem() instanceof Halo)) {
					
				}
				else {

					hasFlight = true;
				}
			}
			
			//Disable fall damage if player falling
			if(hasFlight && event.getSource().damageType.equals("fall")) {
				event.setCanceled(true);
				return;
			}
			
		}
		
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
