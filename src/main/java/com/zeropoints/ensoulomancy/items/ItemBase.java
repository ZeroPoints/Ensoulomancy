package com.zeropoints.ensoulomancy.items;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.util.IHasModel;

import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ENSOULOMANCY_TAB);
		
		ModItems.ITEMS.add(this);
	}

	
	@Override
	public void registerModels() {
		ModRenderers.registerRenderer(this, 0, "inventory");
	}
	
}
