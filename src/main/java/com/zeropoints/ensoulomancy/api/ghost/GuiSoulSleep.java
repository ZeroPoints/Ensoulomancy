package com.zeropoints.ensoulomancy.api.ghost;

import java.io.IOException;

import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
import com.zeropoints.ensoulomancy.network.Dispatcher;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSoulSleep extends GuiScreen {
	
	private BlockPos bedPosition;
	
	public GuiSoulSleep(BlockPos bedPosition) {
		super();
		this.bedPosition = bedPosition;
	}
	
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2, "Cancel"));
        
        IGhost ghost = Ghost.getCapability(this.mc.player);
        
        if (ghost != null) {
        	if (ghost.isGhost()) {
        		this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 - 24, "Leave Ghost Form"));
        	}
        	else {
        		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 - 24, "Become Ghost"));
        	}
        }
    }
    
    /**
     * 
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground(); // This is the darkened background for a gui (I think)
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
    	Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        if (button.id == 2) {
        	this.becomeGhost();
        }
        else if (button.id == 3) {
        	this.deGhost();
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }

    /**
     * Will turn the player into a ghost
     */
    private void becomeGhost() {
    	EntityPlayer player = (EntityPlayer)this.mc.player;
    	IGhost ghost = Ghost.getCapability(player); 
    	
    	if (ghost != null) {
    		ghost.sleep(player, this.bedPosition);
    	}
    }
    
    /**
     * Will turn the player back into normal form
     */
    private void deGhost() {
    	EntityPlayer player = (EntityPlayer)this.mc.player;
    	IGhost ghost = Ghost.getCapability(player);
    	
    	if (ghost != null) {
    		ghost.deGhost(player);
    		// Send settings to server
    		Dispatcher.sendToServer(new PacketGhost(ghost.getSettings())); 
    	}
    }
    
}