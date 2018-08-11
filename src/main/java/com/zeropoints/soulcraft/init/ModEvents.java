package com.zeropoints.soulcraft.init;

import com.zeropoints.soulcraft.api.morphs.MorphHandler;
import com.zeropoints.soulcraft.api.morphs.MorphManager;
import com.zeropoints.soulcraft.api.morphs.helpers.RegisterHandler;
import com.zeropoints.soulcraft.items.tools.ToolBeheading;
import com.zeropoints.soulcraft.renderer.player.RenderingHandler;
import com.zeropoints.soulcraft.util.handlers.CapabilityHandler;
import com.zeropoints.soulcraft.util.handlers.PlayerHandler;

import net.minecraftforge.common.MinecraftForge;


public class ModEvents {
	
	public static void init() {
		/* Event listeners */
		MinecraftForge.EVENT_BUS.register(new PlayerHandler());
		MinecraftForge.EVENT_BUS.register(new ToolBeheading());
		/* Morphing */
    	MinecraftForge.EVENT_BUS.register(new MorphHandler());
    	MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
    	MinecraftForge.EVENT_BUS.register(new RegisterHandler());
	}
	
	public static void initClientEvents() {
		/* Hooks to render the morphed player */
		MinecraftForge.EVENT_BUS.register(new RenderingHandler());
				
		MorphManager.INSTANCE.registerClient();
	}
}
