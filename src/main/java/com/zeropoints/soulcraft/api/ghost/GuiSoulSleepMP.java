package com.zeropoints.soulcraft.api.ghost;

import java.io.IOException;

import com.zeropoints.soulcraft.capabilities.ghost.Ghost;
import com.zeropoints.soulcraft.capabilities.ghost.IGhost;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSoulSleepMP extends GuiScreen {
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2, I18n.format("multiplayer.stopSleeping")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 - 24, "Become Ghost"));
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.wakeFromSleep();
        }
        else if (keyCode == 2) {
        	this.becomeGhost();
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            this.wakeFromSleep();
        }
        else if (button.id == 2) {
        	this.becomeGhost();
        }
        else {
            super.actionPerformed(button);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }

    private void becomeGhost() {
    	EntityPlayer player = (EntityPlayer)this.mc.player;
    	IGhost ghost = Ghost.getCapability(player); 
    	
    	if (ghost != null) {
    		ghost.sleep(ghost.getBedPosition());
    	}
    	
    	Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
    }
    
    private void wakeFromSleep() {
    	NetHandlerPlayClient nethandlerplayclient = this.mc.player.connection;
    	this.mc.player.sendStatusMessage(new TextComponentTranslation("You have left the bed", new Object[0]), true);
    	
    	// This resets the GUI to nothing (hopefully)
    	Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
    	
        //nethandlerplayclient.sendPacket(new CPacketEntityAction(this.mc.player, CPacketEntityAction.Action.STOP_SLEEPING));
    }
}