package com.zeropoints.ensoulomancy.render.tileentity;

import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulBed;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityItemSoulBedStackRenderer extends TileEntityItemStackRenderer {
	
	//public static TileEntityItemStackRenderer instance = new TileEntityItemSoulBedStackRenderer();
	
	private final TileEntitySoulBed bed = new TileEntitySoulBed();
	
	@Override 
	public void renderByItem(ItemStack stack) {
		this.renderByItem(stack, 0);
	}
	
	@Override
	public void renderByItem(ItemStack stack, float partialTicks) {
        Item item = stack.getItem();
        
        if (item == ModItems.SOUL_BED) {
        	TileEntityRendererDispatcher.instance.render(this.bed, 0.0D, 0.0D, 0.0D, 0.0F);
        }
    }
	
}
