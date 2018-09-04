package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.entity.EntityMorph;
import com.zeropoints.ensoulomancy.render.entity.mobs.*;
import com.zeropoints.ensoulomancy.render.player.RenderGhostPlayer;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModRenderers {

	public static void register() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoulSkull.class, new TileEntitySoulSkullRenderer());
		
		// Mobs
		RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, new RenderImp.RenderFactory());
	}
	
	public static void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
	
}
