package com.zeropoints.ensoulomancy.render.player;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zeropoints.ensoulomancy.entity.EntityPlayerCorpse;
import com.zeropoints.ensoulomancy.model.ModelPlayerCorpse;
import com.zeropoints.ensoulomancy.render.layer.LayerGhostBipedArmor;

@SideOnly(Side.CLIENT)
public class RenderEntityPlayerCorpse extends RenderLivingBase<EntityPlayerCorpse> {
	
	public RenderEntityPlayerCorpse(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelPlayerCorpse(), 0.5F);
		this.addLayer(new LayerGhostBipedArmor(this)); // Add ghost armor layer renderer
	}

	protected ResourceLocation getEntityTexture(EntityPlayerCorpse entity) {
		return entity.getSkinLocation();
	}
	
	protected void preRenderCallback(EntityPlayerCorpse entity, float partialTickTime) {
		// This rotates the entity so it is laying down at height for a bed. 
		// Might need to do this in a state somwhere else if we want to re-use the fake player render model
        GlStateManager.rotate(entity.rotationYaw, 0, 1, 0);
        GlStateManager.rotate(-90.0F, 1, 0, 0);
        GlStateManager.translate(0, 1.5F, -0.2F);
    }
   
	
	public static class RenderFactory implements IRenderFactory<EntityPlayerCorpse> {
		@Override
		public Render<? super EntityPlayerCorpse> createRenderFor(RenderManager manager) {
			return new RenderEntityPlayerCorpse(manager);
		}
	}
   
}