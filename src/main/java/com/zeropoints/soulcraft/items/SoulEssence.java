package com.zeropoints.soulcraft.items;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraftforge.oredict.OreDictionary;


public class SoulEssence extends ItemFood implements IHasModel {

	public SoulEssence() {
		super(3, 0.6f, false);
		setUnlocalizedName("soul_essence");
		setRegistryName("soul_essence");
		setCreativeTab(Main.SOULCRAFT_TAB);
		
		ModItems.ITEMS.add(this);
	}

	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "soul_essence");
		
	}
	
	
}