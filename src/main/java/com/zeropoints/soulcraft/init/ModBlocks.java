package com.zeropoints.soulcraft.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.common.base.Preconditions;

import com.zeropoints.soulcraft.blocks.BlockSoulSkull;
import com.zeropoints.soulcraft.tileentity.TileEntitySoulSkull;
import com.zeropoints.soulcraft.util.Reference;
import com.zeropoints.soulcraft.blocks.BlockBase;
import com.zeropoints.soulcraft.blocks.ReapingBeansCrop;
import com.zeropoints.soulcraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	
	public static Set<Block> BLOCKS = new HashSet<>();
	public static Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

	//---------------------------------------------------------------------
	
	// Crops
	public static final ReapingBeansCrop REAPING_BEANS_CROP = new ReapingBeansCrop();
	
	// Decorations
	public static final BlockSoulSkull SOUL_SKULL = new BlockSoulSkull();
	
	//---------------------------------------------------------------------

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {

			final IForgeRegistry<Block> registry = event.getRegistry();

			for (final Block block: BLOCKS) {
				registry.register(block);
				
				if(block instanceof IHasModel) {
					((IHasModel)block).registerModels();	
				}
			}
			
			registerTileEntities();
		}

		@SubscribeEvent
		public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {

			final IForgeRegistry<Item> registry = event.getRegistry();

			for (final ItemBlock item : ITEM_BLOCKS) {
				final Block block = item.getBlock();
				final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
				registry.register(item.setRegistryName(registryName));
			}
		}
	}

	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySoulSkull.class, TileEntitySoulSkull.resourceLocation);
	}
	
}
