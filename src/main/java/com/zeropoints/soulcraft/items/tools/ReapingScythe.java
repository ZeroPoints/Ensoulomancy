package com.zeropoints.soulcraft.items.tools;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class ReapingScythe extends ToolSword {

	public static final String NAME = "reaping_scythe";
	
	public ReapingScythe() {
		super(NAME, ModItems.MATERIAL_SOUL_INGOT);
		
		this.CAN_BEHEAD = true;
		this.HEAD_DROP_CHANCE = 0.5F;
		
	}
	
}
