package com.zeropoints.ensoulomancy.init;

import java.util.ArrayList;
import java.util.List;

import com.zeropoints.ensoulomancy.items.*;
import com.zeropoints.ensoulomancy.items.armor.ArmorBase;
import com.zeropoints.ensoulomancy.items.armor.Halo;
import com.zeropoints.ensoulomancy.items.tools.ReapingScythe;
import com.zeropoints.ensoulomancy.items.tools.ToolSword;
import com.zeropoints.ensoulomancy.render.player.RenderGhostPlayer;
import com.zeropoints.ensoulomancy.util.IHasModel;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
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
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//---------------------------------------------------------------------
	

		
	// Materials
	//DONT PUT ITEMS THAT USE MATERIALS BEFORE THESE MATERIALS
	public static final ToolMaterial MATERIAL_SOUL_INGOT = EnumHelper.addToolMaterial("material_soul_ingot", 2, 250, 6.0F, 5.0F, 10);
	public static final ArmorMaterial ARMOR_MATERIAL_HALO = EnumHelper.addArmorMaterial("armor_material_halo", Reference.MOD_ID + ":halo", 10, new int[] {4,7,9,4}, 10,SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	
	
	
	
	

	// Armor
	public static final Halo HALO_HELMET = new Halo();
		
		
	
	// Plants
	public static final SoulEssence SOUL_ESSENCE = new SoulEssence();
	public static final SoulSeeds SOUL_SEEDS = new SoulSeeds();

	public static final SoulSeedsStem SOUL_SEEDS_STEM = new SoulSeedsStem();
	public static final PedestalSeeds PEDESTAL_SEEDS = new PedestalSeeds();
	public static final ObjSeeds OBJ_SEEDS = new ObjSeeds();

	// Tools
	public static final ReapingScythe REAPING_SCYTHE = new ReapingScythe();
	
	// Items
	public static final SoulIngot SOUL_INGOT = new SoulIngot();
	public static final ItemSoulSkull SOUL_SKULL = new ItemSoulSkull();
	

	// Block Items
	public static final ItemSoulBed SOUL_BED = new ItemSoulBed();


	//---------------------------------------------------------------------
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {

			// uses fixed array with items to register instead of generated list

			final IForgeRegistry<Item> registry = event.getRegistry();
						
			for (final Item item: ITEMS) {
				registry.register(item);
				
				// Only when item has IHasModel implemented do we want to force 'generic' render
				if(item instanceof IHasModel) {
					((IHasModel)item).registerModels();	
				}
			}
		}
		
	}
	
}