package com.zeropoints.ensoulomancy.items.armor;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmorBase extends ItemArmor implements IHasModel {

	public boolean GRANTS_FLIGHT = false;
	
	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		
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
