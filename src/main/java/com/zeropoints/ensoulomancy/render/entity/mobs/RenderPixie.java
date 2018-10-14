package com.zeropoints.ensoulomancy.render.entity.mobs;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.hallowed.EntityPixie;
import com.zeropoints.ensoulomancy.model.hallowed.ModelPixie;


import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class RenderPixie extends RenderBiped<EntityPixie> {
	
	private static final ResourceLocation PIXIE_PINK = new ResourceLocation(Main.MOD_ID + ":textures/entity/hallowed/pixie_pink.png");
	private static final ResourceLocation PIXIE_BLUE = new ResourceLocation(Main.MOD_ID + ":textures/entity/hallowed/pixie_blue.png");
	private static final ResourceLocation PIXIE_GREEN = new ResourceLocation(Main.MOD_ID + ":textures/entity/hallowed/pixie_green.png");
	private static final ResourceLocation PIXIE_BARBARIAN = new ResourceLocation(Main.MOD_ID + ":textures/entity/hallowed/pixie_barbarian.png");
	
	public RenderPixie(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelPixie(), 0.2F);
	}
	
	protected ResourceLocation getEntityTexture(EntityPixie entity) {
		switch(entity.getVariant()) {
			default:
			case 0: return PIXIE_PINK;
			case 1: return PIXIE_BLUE;
			case 2: return PIXIE_GREEN;
			case 3: return PIXIE_BARBARIAN;
		}
	}
	
    protected void preRenderCallback(EntityPixie entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.6F, 0.6F, 0.6F);
    }
	
	/**
     * Rendering factory
     * 
     * Returns new instance of the morph renderer
     */
    public static class RenderFactory implements IRenderFactory<EntityPixie> {
        @Override
        public Render<EntityPixie> createRenderFor(RenderManager manager) {
            return new RenderPixie(manager);
        }
    }
	
}
