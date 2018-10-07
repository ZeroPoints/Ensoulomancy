package com.zeropoints.ensoulomancy.render.layer.husk;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.model.husk.ModelHuskSpiderLegs;
import com.zeropoints.ensoulomancy.render.entity.husk.RenderHusk;


@SideOnly(Side.CLIENT)
public class LayerHuskChickenBody<T extends EntityHusk> implements LayerRenderer<T> {
	
    private final RenderHusk<T> modelRenderer;
    private final ModelHuskSpiderLegs model;
    private static final ResourceLocation TEXTURES = new ResourceLocation("textures/entity/spider/spider.png");

    public LayerHuskChickenBody(RenderHusk renderer) {
        this.modelRenderer = renderer;
        model = new ModelHuskSpiderLegs(6.0F, 0.0F);
    }

	@Override
	public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		// Don't render any layer is not actually applicable
		if (!entity.options.extraOptions.contains("chickenBody")) {
			return;
		}
		
		this.modelRenderer.bindTexture(TEXTURES);
		
        if (entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
        
        //this.modelRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        //this.modelRenderer.postRender(0.0625F);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	
	@Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
}