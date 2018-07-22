package com.zeropoints.soulcraft.init;

import com.zeropoints.soulcraft.items.tools.ToolBeheading;
import net.minecraftforge.common.MinecraftForge;


public class ModEvents {
	public static void init() {
		// Beheading event initialization
		MinecraftForge.EVENT_BUS.register(new ToolBeheading());
	}
}
