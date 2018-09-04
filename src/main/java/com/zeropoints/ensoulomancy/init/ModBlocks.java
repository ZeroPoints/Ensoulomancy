package com.zeropoints.ensoulomancy.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.common.base.Preconditions;
import com.zeropoints.ensoulomancy.blocks.BlockBase;
import com.zeropoints.ensoulomancy.blocks.BlockSoulBed;
import com.zeropoints.ensoulomancy.blocks.BlockSoulSkull;
import com.zeropoints.ensoulomancy.blocks.ObjCrop;
import com.zeropoints.ensoulomancy.blocks.PedestalCrop;
import com.zeropoints.ensoulomancy.blocks.SoulEssenceCrop;
import com.zeropoints.ensoulomancy.blocks.SoulEssenceStem;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulBed;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;
import com.zeropoints.ensoulomancy.util.IHasModel;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockStem;
import net.minecraft.block.SoundType;
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
	public static final SoulEssenceCrop SOUL_ESSENCE_CROP = new SoulEssenceCrop();
	public static final PedestalCrop PEDESTAL_CROP = new PedestalCrop();
	public static final SoulEssenceStem SOUL_ESSENCE_STEM = new SoulEssenceStem();
	public static final ObjCrop OBJ_CROP = new ObjCrop();
	
	
	// Decorations
	public static final BlockSoulSkull SOUL_SKULL = new BlockSoulSkull();
	
	// Tile Entities
	public static final BlockSoulBed SOUL_BED = new BlockSoulBed();
	
		
	//---------------------------------------------------------------------

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {

			final IForgeRegistry<Block> registry = event.getRegistry();

			for (final Block block: BLOCKS) {
				registry.register(block);
				
				if (block instanceof IHasModel) {
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

	/**
	 * Register tile entity, will render as its model in the world
	 */
	private static void registerTileEntities() {
		
		GameRegistry.registerTileEntity(TileEntitySoulSkull.class, new ResourceLocation(TileEntitySoulSkull.resourceLocation));
		GameRegistry.registerTileEntity(TileEntitySoulBed.class, new ResourceLocation(TileEntitySoulBed.resourceLocation));
	}
	
}
