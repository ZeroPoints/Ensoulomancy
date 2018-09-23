package com.zeropoints.ensoulomancy.init;

//import com.zeropoints.ensoulomancy.api.morphs.MorphHandler;
//import com.zeropoints.ensoulomancy.api.morphs.MorphManager;
//import com.zeropoints.ensoulomancy.api.morphs.helpers.RegisterHandler;
import com.zeropoints.ensoulomancy.capabilities.CapabilityHandler;
import com.zeropoints.ensoulomancy.items.tools.ToolBeheading;
//import com.zeropoints.ensoulomancy.network.client.KeyboardHandler;
import com.zeropoints.ensoulomancy.render.player.RenderingHandler;

import net.minecraftforge.common.MinecraftForge;

public class ModEvents {
	
	public static void init() {
		// Event listeners 
		MinecraftForge.EVENT_BUS.register(new ToolBeheading()); // TODO: Place this somewhere else and just inherit it in the tool
		
		// Morphing 
    	//MinecraftForge.EVENT_BUS.register(new MorphHandler()); // Events for Morph-related activities
    	MinecraftForge.EVENT_BUS.register(new CapabilityHandler()); // Events for capabilities
    	//MinecraftForge.EVENT_BUS.register(new RegisterHandler()); // Events for settings & updating of settings
	}
	
	public static void initClientEvents() {
		// Hooks to render the morphed player 
		MinecraftForge.EVENT_BUS.register(new RenderingHandler()); // Handles rendering player for morphs
		//MinecraftForge.EVENT_BUS.register(new KeyboardHandler()); // Handles key press and actions for morphs
				
		//MorphManager.INSTANCE.registerClient(); // Registers each morph factory client-side
	}
}
