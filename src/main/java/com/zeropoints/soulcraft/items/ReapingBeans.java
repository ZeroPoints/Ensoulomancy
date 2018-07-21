package com.zeropoints.soulcraft.items;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraftforge.oredict.OreDictionary;


public class ReapingBeans extends ItemFood implements IHasModel {

	public ReapingBeans() {
		super(3, 0.6f, false);
		setUnlocalizedName("reaping_beans");
		setRegistryName("reaping_beans");
		setCreativeTab(Main.SOULCRAFT_TAB);
		
		ModItems.ITEMS.add(this);
	}

	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "reaping_beans");
		
	}
	
	
}