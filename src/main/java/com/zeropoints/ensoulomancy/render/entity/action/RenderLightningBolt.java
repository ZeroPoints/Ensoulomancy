package com.zeropoints.ensoulomancy.render.entity.action;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.action.EntityLightningBolt;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



@SideOnly(Side.CLIENT)
public class RenderLightningBolt extends Render<EntityLightningBolt> {
	
    
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/items/spirit_guide.png");
	
	public RenderLightningBolt(RenderManager renderManagerIn) {
		super(renderManagerIn);
		Main.log("LightningBolt render init");
	}
	
	protected ResourceLocation getEntityTexture(EntityLightningBolt entity) {
		//Main.log("LightningBolt render get");
		return TEXTURES;
	}
	
	
	
	public void doRender(EntityLightningBolt entity, double x, double y, double z, float entityYaw, float partialTicks)
    {

        GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);    
        
        
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(1, 1, 1);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        //bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
		bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
		
		
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
     * Rendering factory
     * 
     * Returns new instance of the morph renderer
     */
    public static class RenderFactory implements IRenderFactory<EntityLightningBolt> {
        @Override
        public Render<EntityLightningBolt> createRenderFor(RenderManager manager) {
    		Main.log("LightningBolt render createRenderFor");
            return new RenderLightningBolt(manager);
        }
    }









	
}
