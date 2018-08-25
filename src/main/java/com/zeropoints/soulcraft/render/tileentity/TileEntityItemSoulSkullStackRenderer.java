package com.zeropoints.soulcraft.render.tileentity;

import com.zeropoints.soulcraft.init.ModItems;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityItemSoulSkullStackRenderer extends TileEntityItemStackRenderer {
	
	public static TileEntityItemStackRenderer instance = new TileEntityItemSoulSkullStackRenderer();
	
	@Override 
	public void renderByItem(ItemStack stack) {
		this.renderByItem(stack, 0);
	}
	
	@Override
	public void renderByItem(ItemStack stack, float partialTicks) {
        Item item = stack.getItem();
        
        if (item == ModItems.SOUL_SKULL) {
            if (TileEntitySoulSkullRenderer.instance != null) {
                GlStateManager.pushMatrix();
                GlStateManager.disableCull();
                TileEntitySoulSkullRenderer.instance.renderSkull(0, 0, 0, EnumFacing.UP, 180.0F, stack.getMetadata(), -1);
                GlStateManager.enableCull();
                GlStateManager.popMatrix();
            }
        }
    }
	
}
