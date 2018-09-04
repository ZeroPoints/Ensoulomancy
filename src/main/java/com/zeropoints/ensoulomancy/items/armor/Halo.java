package com.zeropoints.ensoulomancy.items.armor;


import com.zeropoints.ensoulomancy.init.ModItems;

import net.minecraft.inventory.EntityEquipmentSlot;

public class Halo extends ArmorBase  {

	public static final String NAME = "halo_helmet";
	
	public Halo() {
		super(NAME, ModItems.ARMOR_MATERIAL_HALO, 1, EntityEquipmentSlot.HEAD);
		
		this.GRANTS_FLIGHT = true;
	}
	
}
