package com.zeropoints.soulcraft.init;

import com.zeropoints.soulcraft.entity.EntityMorph;
import com.zeropoints.soulcraft.renderer.entity.mobs.*;
import com.zeropoints.soulcraft.renderer.entity.mobs.RenderImp;
import com.zeropoints.soulcraft.renderer.player.RenderGhostPlayer;
import com.zeropoints.soulcraft.renderer.player.RenderMorph;
import com.zeropoints.soulcraft.renderer.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.soulcraft.tileentity.TileEntitySoulSkull;

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
		
		// Player Possessions
		RenderingRegistry.registerEntityRenderingHandler(EntityMorph.class, new RenderMorph.RenderFactory());
		
	}
	
	public static void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
	
}
