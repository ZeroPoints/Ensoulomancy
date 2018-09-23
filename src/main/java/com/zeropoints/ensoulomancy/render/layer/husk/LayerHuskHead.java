package com.zeropoints.ensoulomancy.render.layer.husk;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.model.ModelHeadBase;
import com.zeropoints.ensoulomancy.model.husk.ModelHuskSpiderLegs;
import com.zeropoints.ensoulomancy.render.entity.husk.RenderHusk;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.ensoulomancy.util.SoulSkullType;
import com.zeropoints.ensoulomancy.util.SoulSkullType.SkullRegistryHelper;


@SideOnly(Side.CLIENT)
public class LayerHuskHead<T extends EntityHusk> implements LayerRenderer<T> {
	
    private final RenderHusk<T> modelRenderer;
    
    public LayerHuskHead(RenderHusk renderer) {
        this.modelRenderer = renderer;
    }

	@Override
	public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		// Don't render a head if it is invalid
		if (entity.options == null || !SkullRegistryHelper.SoulSkullTypeMap.containsKey(entity.options.head)) {
			return;
		}
		
    	int skullType = SkullRegistryHelper.SoulSkullTypeMap.get(entity.options.head);
        
    	SoulSkullType soulSkull = SkullRegistryHelper.SoulSkullTypes[skullType];
        ModelHeadBase model = soulSkull.model;
        model.scale = scale;

    	this.modelRenderer.bindTexture(soulSkull.texture); // Would this work?

    	model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	
	@Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
}