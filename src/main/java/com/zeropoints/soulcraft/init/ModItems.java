package com.zeropoints.soulcraft.init;

import java.util.ArrayList;
import java.util.List;

import com.zeropoints.soulcraft.items.tools.ReapingScythe;
import com.zeropoints.soulcraft.items.tools.ToolSword;
import com.zeropoints.soulcraft.render.player.RenderGhostPlayer;
import com.zeropoints.soulcraft.items.ItemSoulSkull;
import com.zeropoints.soulcraft.blocks.ReapingBeansCrop;
import com.zeropoints.soulcraft.items.*;
import com.zeropoints.soulcraft.items.armor.ArmorBase;
import com.zeropoints.soulcraft.util.IHasModel;
import com.zeropoints.soulcraft.util.Reference;

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
	public static final ToolMaterial MATERIAL_SOUL_INGOT = EnumHelper.addToolMaterial("material_soul_ingot", 2, 250, 6.0F, 5.0F, 10);
	
	// Plants
	public static final ReapingBeans REAPING_BEANS = new ReapingBeans();
	public static final ReapingSeeds REAPING_SEEDS = new ReapingSeeds();

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
