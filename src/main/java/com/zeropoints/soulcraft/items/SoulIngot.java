package com.zeropoints.soulcraft.items;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;


public class SoulIngot extends Item implements IHasModel {

	public SoulIngot() {
		super();
		setUnlocalizedName("soul_ingot");
		setRegistryName("soul_ingot");
		setCreativeTab(Main.SOULCRAFT_TAB);
		
		ModItems.ITEMS.add(this);
	}

	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "soul_ingot");
		
	}
	
	
}