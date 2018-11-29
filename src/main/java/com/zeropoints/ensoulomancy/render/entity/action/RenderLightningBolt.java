package com.zeropoints.ensoulomancy.render.entity.action;

import java.util.Random;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.action.EntityLightningBolt;

import net.minecraft.client.gui.Gui;
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
        

        double xCoord = 0;
        double yCoord = 0;
        double widthIn = 2;
        double heightIn = 2;
        
        GlStateManager.translate((float)x - (widthIn/2), (float)y - (widthIn/2), (float)z - (widthIn/2));
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(1, 1, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), 0).color(1F, 0, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), 0).color(0, 1F, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), 0).color(0, 0, 1F, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), 0).color(0.5F, 0.5F, 0.5F, 0.5F).endVertex();
        
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), widthIn).color(1F, 0, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), widthIn).color(0, 1F, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), widthIn).color(0, 0, 1F, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), widthIn).color(0.5F, 0.5F, 0.5F, 0.5F).endVertex();
        
        bufferbuilder.pos(0, (double)(xCoord + 0), (double)(yCoord + heightIn)).color(1F, 0, 0, 0.5F).endVertex();
        bufferbuilder.pos(0, (double)(xCoord + widthIn), (double)(yCoord + heightIn)).color(0, 1F, 0, 0.5F).endVertex();
        bufferbuilder.pos(0, (double)(xCoord + widthIn), (double)(yCoord + 0)).color(0, 0, 1F, 0.5F).endVertex();
        bufferbuilder.pos(0, (double)(xCoord + 0), (double)(yCoord + 0)).color(0.5F, 0.5F, 0.5F, 0.5F).endVertex();
        
        bufferbuilder.pos(widthIn, (double)(xCoord + 0), (double)(yCoord + heightIn)).color(1F, 0, 0, 0.5F).endVertex();
        bufferbuilder.pos(widthIn, (double)(xCoord + widthIn), (double)(yCoord + heightIn)).color(0, 1F, 0, 0.5F).endVertex();
        bufferbuilder.pos(widthIn, (double)(xCoord + widthIn), (double)(yCoord + 0)).color(0, 0, 1F, 0.5F).endVertex();
        bufferbuilder.pos(widthIn, (double)(xCoord + 0), (double)(yCoord + 0)).color(0.5F, 0.5F, 0.5F, 0.5F).endVertex();
        
        bufferbuilder.pos((double)(xCoord + 0), 0, (double)(yCoord + heightIn)).color(1F, 0, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), 0, (double)(yCoord + heightIn)).color(0, 1F, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), 0, (double)(yCoord + 0)).color(0, 0, 1F, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), 0, (double)(yCoord + 0)).color(0.5F, 0.5F, 0.5F, 0.5F).endVertex();
        
        bufferbuilder.pos((double)(xCoord + 0), widthIn, (double)(yCoord + heightIn)).color(1F, 0, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), widthIn, (double)(yCoord + heightIn)).color(0, 1F, 0, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), widthIn, (double)(yCoord + 0)).color(0, 0, 1F, 0.5F).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), widthIn, (double)(yCoord + 0)).color(0.5F, 0.5F, 0.5F, 0.5F).endVertex();
        
        tessellator.draw();

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
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
