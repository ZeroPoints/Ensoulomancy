package com.zeropoints.ensoulomancy.capabilities.ghost;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.api.ghost.GuiSoulSleep;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;
import com.zeropoints.ensoulomancy.network.Dispatcher;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;
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
	public void sleep(EntityPlayer player, BlockPos bedPosition) {
		// Set position for this bed so when player leaves ghost form they appear there ???
		this.bedPosition = bedPosition;
		
		// Not sure what this does. Will test
    	try {
    		ReflectionHelper.findMethod(EntityPlayer.class, "spawnShoulderEntities", "func_192030_dh").invoke(player);
		} 
    	catch (Exception e) {
			e.printStackTrace();
		}
		
		// This code teleports the player to the bed and locks views?
		final IBlockState state = player.world.isBlockLoaded(bedPosition) ? player.world.getBlockState(bedPosition) : null;
        final boolean isBed = state != null && state.getBlock() == ModBlocks.SOUL_BED;
        final EnumFacing enumfacing = isBed && state.getBlock() instanceof BlockHorizontal ? (EnumFacing)state.getValue(BlockHorizontal.FACING) : null;
        
        float f1 = 0.5F;
        float f2 = 0.5F;
        
        if (enumfacing != null) {
            f1 = 0.5F + (float)enumfacing.getFrontOffsetX() * 0.4F;
            f2 = 0.5F + (float)enumfacing.getFrontOffsetZ() * 0.4F;            
            player.renderOffsetX = -1.8F * (float)enumfacing.getFrontOffsetX();
            player.renderOffsetZ = -1.8F * (float)enumfacing.getFrontOffsetZ();
        }
        
        player.setPosition(bedPosition.getX() + f1, bedPosition.getY() + 0.6875F, bedPosition.getZ() + f2);

        player.motionX = 0.0D;
        player.motionY = 0.0D;
        player.motionZ = 0.0D;
		
        // Start the sleeping animations in the update function now
		if (!player.world.isRemote) {
			return;
		}
		
		this.isSleeping = true;
		this.sleepTimer = 0;
	}
	
	/*
	@SideOnly(Side.CLIENT)
    public boolean tryStartCameraFlight() {
        if (cameraFlightActive || !isClientCloseEnough()) {
            return false;
        }

        Vector3 offset = new Vector3(this).add(0, 6, 0);
        ClientCameraFlightHelper.CameraFlightBuilder builder = ClientCameraFlightHelper.builder(offset.clone().add(4, 0, 4), new Vector3(this).add(0.5, 0.5, 0.5));
        builder.addCircularPoints(offset, ClientCameraFlightHelper.DynamicRadiusGetter.dyanmicIncrease( 5,  0.025), 200, 2);
        builder.addCircularPoints(offset, ClientCameraFlightHelper.DynamicRadiusGetter.dyanmicIncrease(10, -0.01) , 200, 2);
        builder.setTickDelegate(createFloatDelegate(new Vector3(this).add(0.5F, 1.2F, 0.5F)));
        builder.setStopDelegate(createAttunementDelegate());

        OrbitalPropertiesAttunement att = new OrbitalPropertiesAttunement(this, true);
        OrbitalEffectController ctrl = EffectHandler.getInstance().orbital(att, att, null);
        ctrl.setOrbitAxis(Vector3.RotAxis.Y_AXIS).setOrbitRadius(3).setTicksPerRotation(80).setOffset(new Vector3(this).add(0.5, 0.5, 0.5));

        ctrl = EffectHandler.getInstance().orbital(att, att, null);
        ctrl.setOrbitAxis(Vector3.RotAxis.Y_AXIS).setOrbitRadius(3)
                .setTicksPerRotation(80).setTickOffset(40).setOffset(new Vector3(this).add(0.5, 0.5, 0.5));

        this.clientActiveCameraFlight = builder.finishAndStart();
        this.cameraFlightActive = true;
        return true;
    }
	*/
	
	
	@Override
	public void stopSleeping(EntityPlayer player) {
		this.isSleeping = false;
		this.sleepTimer = 0;
		
		//if (player.world.isRemote) {
		Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
		//}
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
