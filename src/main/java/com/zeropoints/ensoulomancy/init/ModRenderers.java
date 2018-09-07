package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.blocks.BlockSoulSkull;
import com.zeropoints.ensoulomancy.items.ItemSoulSkull;
import com.zeropoints.ensoulomancy.render.entity.mobs.EntityImp;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderImp;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntityItemSoulSkullStackRenderer;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;
import com.zeropoints.ensoulomancy.util.IEntity;
import com.zeropoints.ensoulomancy.util.IHasModel;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Side.CLIENT)
public class ModRenderers {
	
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
    	// Items
    	for (final Item item: ModItems.ITEMS) {
			// Only when item has IHasModel implemented do we want to force 'generic' render
			if (item instanceof IHasModel) {
				((IHasModel)item).registerModels();	
			}
		}
    	
    	// Blocks
    	for (final Block block: ModBlocks.BLOCKS) {
    		// Only when block has IHasModel implemented do we want to force 'generic' render
    		if (block instanceof IHasModel) {
    			((IHasModel)block).registerModels();
    		}
    	}
    	
        // Entities
        /*for (final Entity entity: ModEntities.ENTITIES) {
        	if (entity instanceof IEntity) {
        		((IEntity)entity).RegisterEntityRenderer();
        	}
        }*/
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, new RenderImp.RenderFactory());
    }

    public static void registerRenderer(Item item, int meta, String name) {
        ModelResourceLocation resource = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, resource);
    }

    public static void registerRenderer(Block block, int meta, String name) {
        registerRenderer(Item.getItemFromBlock(block), meta, name);
    }
    
}