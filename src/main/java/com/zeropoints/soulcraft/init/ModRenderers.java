package com.zeropoints.soulcraft.init;

import com.zeropoints.soulcraft.renderer.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.soulcraft.tileentity.TileEntitySoulSkull;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModRenderers {

	public static void register() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoulSkull.class, new TileEntitySoulSkullRenderer());
	}
	
	public static void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
	
}
