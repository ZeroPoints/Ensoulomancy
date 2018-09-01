package com.zeropoints.soulcraft.util;

import com.zeropoints.soulcraft.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SoulcraftTab extends CreativeTabs {
	
	public SoulcraftTab(String label) { 
		super("soulcraft_tab"); 
	}

	public ItemStack getTabIconItem() { 
		return new ItemStack(ModItems.SOUL_SEEDS);
	}
}

