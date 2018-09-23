package com.zeropoints.ensoulomancy.capabilities.ghost;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.api.ghost.GuiSoulSleep;
import com.zeropoints.ensoulomancy.entity.EntityPlayerCorpse;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;
import com.zeropoints.ensoulomancy.network.Dispatcher;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;
import com.zeropoints.ensoulomancy.util.CameraRenderHelper;
import com.zeropoints.ensoulomancy.world.PurgatoryWorldType;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
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
    
    /** The entity that currently resides in the player's soul-bed */
    private EntityPlayerCorpse corpseEntity;
    
    
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

	/**
	 * 
	 */
	@Override
	public void becomeGhost(EntityPlayer player) {
		// Put SP cost here?
		this.isGhost = true;
		
		if (player != null && !player.world.isRemote) {
	        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.PORTAL, false, player.posX, player.posY + 0.5, player.posZ, 25, 0.5, 0.5, 0.5, 0.05);
	        player.sendMessage(new TextComponentString("Is a Ghost")); 
        }
	}

	@Override
	public void deGhost(EntityPlayer player) {
		this.isSleeping = false;
		this.isGhost = false;
		
		if (this.corpseEntity != null && !this.corpseEntity.isDead) {
			this.corpseEntity.setDead();
		}
		
		if (player != null && !player.world.isRemote) {
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
			if (this.visible || Minecraft.getMinecraft().player == player) {
				GlStateManager.color(1.0F, 1.0F, 1.0F, 0.35F);
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void sleep(EntityPlayer player, BlockPos bedPos) {
		// Set position for this bed so when player leaves ghost form they appear there ???
		this.bedPosition = bedPos;
		
		// This code teleports the player to the bed and locks views?
		final IBlockState state = player.world.isBlockLoaded(bedPos) ? player.world.getBlockState(bedPos) : null;
        final boolean isBed = state != null && state.getBlock() == ModBlocks.SOUL_BED;
        final EnumFacing enumfacing = isBed && state.getBlock() instanceof BlockHorizontal ? (EnumFacing)state.getValue(BlockHorizontal.FACING) : null;
        
        float yaw = CameraRenderHelper.getYaw(enumfacing.getOpposite());
        BlockPos bedFront = bedPos.offset(enumfacing, -2);
        player.setLocationAndAngles(bedFront.getX() + 0.5F, bedFront.getY(), bedFront.getZ() + 0.5F, yaw, 0.0F);
        player.motionX = 0.0D;
        player.motionY = 0.0D;
        player.motionZ = 0.0D;
        
    	// Spawn fake player rendered in the bed
        this.corpseEntity = new EntityPlayerCorpse(player.world, player, false);
        player.world.spawnEntity(this.corpseEntity);
        this.corpseEntity.setLocationAndAngles(0.5D + bedPos.getX(), bedPos.getY() + 0.5625D, bedPos.getZ() + 0.5D, yaw, 0.0F);
        
        // Spawn fake ghost player that will render to stand up
        EntityPlayerCorpse ghostCorpse = new EntityPlayerCorpse(player.world, player, true);
        player.world.spawnEntity(ghostCorpse);
        ghostCorpse.setLocationAndAngles(0.5D + bedPos.getX(), bedPos.getY() + 0.5625D, bedPos.getZ() + 0.5D, yaw, 0.0F);
        
    	if (player.world.isRemote) {
    		// This method executes when the spawned camera stops rendering the player's view.
    		Runnable onStopped = new Runnable() {
				@Override
				public void run() {
					ghostCorpse.setDead();
				}
    		};
    		
    		// Create the camera and set the player's view to it
    		CameraRenderHelper.createCamera(bedPos, enumfacing, onStopped);
    	}  
        
        // Start the sleeping animations in the update function now
		if (!player.world.isRemote) {
			return;
		}
		
		this.isSleeping = true;
		this.sleepTimer = 0;
	}	
	
	@Override
	public void stopSleeping(EntityPlayer player) {
		this.isSleeping = false;
		this.sleepTimer = 0;
		
		Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
	}

	@Override
	public void update(EntityPlayer player) {
		if (player == null) {
			return;
		}
		
		// Client-side sleep, will make pretty particles for that player only
		if (player.world.isRemote && !isGhost && isSleeping) {
			++this.sleepTimer;
			
			// After 100 ticks, player will become ghost
			if (this.sleepTimer >= 100) {
				// TODO: I can insert different animations and things based on the sleepTimer variable
				
				this.sleepTimer = 0;
				this.isSleeping = false;
				
				this.becomeGhost(player);
				
				// Send data to server
				Dispatcher.sendToServer(new PacketGhost(this.getSettings()));
			}
		}
	}
	
}
