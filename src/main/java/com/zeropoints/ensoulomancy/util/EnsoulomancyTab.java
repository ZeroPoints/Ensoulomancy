package com.zeropoints.ensoulomancy.util;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class EnsoulomancyTab extends CreativeTabs {
	
	
	/**
	 * Our creative menu tab
	 */
	public EnsoulomancyTab() { 
		super(Main.MOD_ID + "_tab"); 
	}

	
	/**
	 * Icon
	 */
	public ItemStack getTabIconItem() { 
		return new ItemStack(ModItems.SOUL_SEEDS);
	}
}

