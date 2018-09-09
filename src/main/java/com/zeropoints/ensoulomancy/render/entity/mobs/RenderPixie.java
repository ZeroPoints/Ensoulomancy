package com.zeropoints.ensoulomancy.render.entity.mobs;

import com.zeropoints.ensoulomancy.entity.hallowed.EntityPixie;
import com.zeropoints.ensoulomancy.model.hallowed.ModelPixie;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class RenderPixie extends RenderBiped<EntityPixie> {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/hallowed/pixie.png");
	
	public RenderPixie(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelPixie(), 0.2F);
	}
	
	@Override
	public void transformHeldFull3DItemLayer() {
        //GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
	
	protected ResourceLocation getEntityTexture(EntityPixie entity) {
		return TEXTURES;
	}
	
    protected void preRenderCallback(EntityPixie entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.7F, 0.7F, 0.7F);
    }
	
	/**
     * Rendering factory
     * 
     * Returns new instance of the morph renderer
     */
    public static class RenderFactory implements IRenderFactory<EntityPixie> {
        @Override
        public Render<? super EntityPixie> createRenderFor(RenderManager manager) {
            return new RenderPixie(manager);
        }
    }
	
}
