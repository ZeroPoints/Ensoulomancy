package com.zeropoints.soulcraft.init;




import com.zeropoints.soulcraft.items.tools.ToolBeheading;
import com.zeropoints.soulcraft.util.handlers.CapabilityHandler;
import com.zeropoints.soulcraft.util.handlers.PlayerHandler;

import net.minecraftforge.common.MinecraftForge;


public class ModEvents {
	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerHandler());
		MinecraftForge.EVENT_BUS.register(new ToolBeheading());
	}
}
