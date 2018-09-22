package com.zeropoints.ensoulomancy.items;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.util.IHasModel;

import net.minecraft.item.ItemFood;


public class SoulEssence extends ItemFood implements IHasModel {

	private static final String name = "soul_essence";
	
	public SoulEssence() {
		super(3, 0.6f, false);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ENSOULOMANCY_TAB);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		ModRenderers.registerRenderer(this, 0, name);
	}
	
}