package com.zeropoints.soulcraft.init;

import com.zeropoints.soulcraft.renderer.entity.mobs.EntityImp;
import com.zeropoints.soulcraft.renderer.entity.mobs.RenderImp;
import com.zeropoints.soulcraft.renderer.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.soulcraft.tileentity.TileEntitySoulSkull;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModRenderers {

	public static void register() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoulSkull.class, new TileEntitySoulSkullRenderer());
		
		// Mobs
		RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, new IRenderFactory<EntityImp>() {
			@Override
			public Render<? super EntityImp> createRenderFor(RenderManager manager) {
				return new RenderImp(manager);
			}
		});
	}
	
	public static void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
	
}
