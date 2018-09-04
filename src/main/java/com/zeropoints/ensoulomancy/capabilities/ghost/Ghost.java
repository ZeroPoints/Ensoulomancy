package com.zeropoints.ensoulomancy.capabilities.ghost;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;
import com.zeropoints.ensoulomancy.network.Dispatcher;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;
import com.zeropoints.ensoulomancy.world.PurgatoryWorldType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Ghost implements IGhost {
	
    /** The health of the player currently sleeping in the bed is stored here for when player returns to body */
    private int health = 0;
    
    /** Whether this player is currently ghost-form */
    private boolean isGhost = false;
    
    /** Visible to other players? */
    private boolean visible = true; 
    
    /** The position of your soulpool bed. */
    private BlockPos bedPosition;
    
    /** Number of ticks lying in bed till commit ghost form */
    private int sleepTimer;
    
    /** Whether the player is trying to become ghost-form */
    private boolean isSleeping = false;
    
    
	public static IGhost getCapability(EntityPlayer player) {
        return player.getCapability(GhostProvider.GHOST_CAPABILITY, null);
    }
	
	/**
	 * Return settings based on the capability to send to the client-side. 
	 */
	@Override
	public GhostSettings getSettings() {
		GhostSettings settings = new GhostSettings();
		settings.isGhost = this.isGhost;
		settings.health = this.health;
		return settings;
	}
	
	/**
	 * Set the settings given by a map format
	 */
	@Override
	public void setSettings(GhostSettings settings) {
		Main.log(Level.INFO, "Set the ghost settings!");
		
		// TODO: how to make this friendlier?
		this.isGhost = settings.isGhost;
		this.health = settings.health;
	}
	
	@Override
	public boolean isGhost() {
		return this.isGhost;
	}

	private void becomeGhost(EntityPlayer player) {
		if (player != null && !player.world.isRemote) {
	        // Poof!
	        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.PORTAL, false, player.posX, player.posY + 0.5, player.posZ, 25, 0.5, 0.5, 0.5, 0.05);
        }
		
		// Put SP cost here?
		this.isGhost = true;
		
		player.sendMessage(new TextComponentString("Is a Ghost")); 
	}

	@Override
	public void deGhost(EntityPlayer player) {
		this.isSleeping = false;
		this.isGhost = false;
		
		if (!player.world.isRemote) {
	        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.PORTAL, false, player.posX, player.posY + 0.5, player.posZ, 25, 0.5, 0.5, 0.5, 0.05);
			player.sendMessage(new TextComponentString("Not a Ghost"));
		}
	}
	
	@Override
	public BlockPos getBedPosition() {
		return this.bedPosition;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderPlayer(EntityPlayer player) {
		if (this.isGhost) {
			// Render all players normally in purgatory
			if (player.world.getWorldType() instanceof PurgatoryWorldType) {
				return false;
			}
			
			// Render player if they see themself or visible to another player
			/*if (this.visible || Minecraft.getMinecraft().player == player) {
				GlStateManager.color(1.0F, 1.0F, 1.0F, 0.35F);
				return false;
			}*/
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Set variables when player sleeds in bed
	 */
	@Override
	public void sleep(BlockPos bedPosition) {
		this.isSleeping = true;
		this.sleepTimer = 0;
		
		// Set position for this bed so when player leaves ghost form they appear there
		this.bedPosition = bedPosition; 
	}

	/**
	 * Update function that constantly runs onPlayerTick event
	 * Handles the player sleeping in a bed to become a ghost
	 */
	@Override
	public void update(EntityPlayer player) {
		if (player != null && !player.world.isRemote && !isGhost && isSleeping) {
			
			// Leave bed if sneaky
			if (player.isSneaking()) {
				this.isSleeping = false;
				player.sendMessage(new TextComponentString("Cancelled Sleeping")); 
				return;
			}
			
			++this.sleepTimer;
			
			// After 100 ticks, player will become ghost
			if (this.sleepTimer >= 100) {
				this.sleepTimer = 0;
				this.isSleeping = false;
				
				this.becomeGhost(player);
				
				// Send data to client
				Dispatcher.sendTo(new PacketGhost(this.getSettings()), (EntityPlayerMP) player);
			}
		}
	}
	
}
