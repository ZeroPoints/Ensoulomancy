package com.zeropoints.ensoulomancy.render.entity.mobs;

import com.zeropoints.ensoulomancy.entity.profane.EntityImp;
import com.zeropoints.ensoulomancy.model.profane.ModelImp;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class RenderImp extends RenderBiped<EntityImp> {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/profane/imp.png");
	
	public RenderImp(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelImp(), 0.2F);
	}
	
	@Override
	public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
	
	protected ResourceLocation getEntityTexture(EntityImp entity) {
		return TEXTURES;
	}
	
    protected void preRenderCallback(EntityImp entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.7F, 0.7F, 0.7F);
    }
	
    /*
     * This applies walk gait
     */
	protected void applyRotations(EntityImp entity, float a, float rotationYaw, float partialTicks) {
		super.applyRotations(entity, a, rotationYaw, partialTicks);
		
		if (entity.onGround && (double)entity.limbSwingAmount >= 0.01D) {
			float f = 13.0F;
			float f1 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
			float f2 = (Math.abs(f1 % f - f/2) - f/4) / (f/4);
			GlStateManager.rotate((f/2) * f2, 0.0F, 0.0F, 1.0F);
		}
	}
	
	/**
     * Rendering factory
     * 
     * Returns new instance of the morph renderer
     */
    public static class RenderFactory implements IRenderFactory<EntityImp> {
        @Override
        public Render<? super EntityImp> createRenderFor(RenderManager manager) {
            return new RenderImp(manager);
        }
    }
	
}
