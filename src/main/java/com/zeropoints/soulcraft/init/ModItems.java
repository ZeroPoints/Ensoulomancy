package com.zeropoints.soulcraft.init;

import java.util.ArrayList;
import java.util.List;

import com.zeropoints.soulcraft.items.tools.ReapingScythe;
import com.zeropoints.soulcraft.items.tools.ToolSword;
import com.zeropoints.soulcraft.blocks.ReapingBeansCrop;
import com.zeropoints.soulcraft.items.*;
import com.zeropoints.soulcraft.items.armor.ArmorBase;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	
	// Materials
	public static final ToolMaterial MATERIAL_SOUL_INGOT = EnumHelper.addToolMaterial("material_soul_ingot", 2, 250, 6.0F, 5.0F, 10);
	
	// Plants
	public static final ReapingBeans REAPING_BEANS = new ReapingBeans();
	public static final ReapingSeeds REAPING_SEEDS = new ReapingSeeds();

	// Tools
	public static final ReapingScythe REAPING_SCYTHE = new ReapingScythe();
	
	// Items
	public static final SoulIngot SOUL_INGOT = new SoulIngot();
	
}
