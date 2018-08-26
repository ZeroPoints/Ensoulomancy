package com.zeropoints.soulcraft.network.client;

import org.lwjgl.input.Keyboard;

import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import com.zeropoints.soulcraft.network.Dispatcher;
import com.zeropoints.soulcraft.network.common.PacketAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * Keyboard handler 
 * 
 * This class (handler) is responsible for handling the keyboard input for 
 * executing an action
 */
public class KeyboardHandler {
	
    /* Action key */
    private KeyBinding keyAction;

    public KeyboardHandler() {
    	String category = "key.metamorph";
    	
        /* Create key bindings */
        keyAction = new KeyBinding("key.metamorph.action", Keyboard.KEY_V, category);
        
        /* Register them in the client registry */
        ClientRegistry.registerKeyBinding(keyAction);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();

        /* Action */
        if (keyAction.isPressed()) {
            Dispatcher.sendToServer(new PacketAction());

            EntityPlayer player = Minecraft.getMinecraft().player;
            IMorphing capability = Morphing.getCapability(player);

            if (capability != null & capability.isMorphed()) {
                capability.getCurrentMorph().action(player);
            }
        }
    }
}