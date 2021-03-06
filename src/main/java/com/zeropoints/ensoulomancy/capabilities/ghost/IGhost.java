package com.zeropoints.ensoulomancy.capabilities.ghost;

import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGhost { 
	
	/** Returns the current settings needed for packet transfer to the client */
	public GhostSettings getSettings();
	
	/** Set the settings based on message packet data */
	public void setSettings(GhostSettings settings);
	
	/** Returns whether the player is currently a ghost */
	public boolean isGhost();
	
	/** Change player to normal */
	public void deGhost(EntityPlayer player);
	
	/** Turn ethereal! */
	public void becomeGhost(EntityPlayer player);
	
    /** Get the soul bed position */
    public BlockPos getBedPosition();
    
	/** Render the player as ghost-form */
	@SideOnly(Side.CLIENT)
    public boolean renderPlayer(EntityPlayer player);
	
	/** Force animation in bed before turning into a ghost */
	void sleep(EntityPlayer player, BlockPos bedPosition);
	
	/** The player cancels the sleep event */
	public void stopSleeping(EntityPlayer player);
	
	/** Update the player per tick */
    public void update(EntityPlayer player);

}
