package com.zeropoints.ensoulomancy.util;

import com.zeropoints.ensoulomancy.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class EnsoulomancyTab extends CreativeTabs {
	
	public EnsoulomancyTab(String label) { 
		super("ensoulomancy_tab"); 
	}

	public ItemStack getTabIconItem() { 
		return new ItemStack(ModItems.SOUL_SEEDS);
	}
}

