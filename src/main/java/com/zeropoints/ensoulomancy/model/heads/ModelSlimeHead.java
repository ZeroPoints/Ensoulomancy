package com.zeropoints.ensoulomancy.model.heads;

import org.lwjgl.opengl.GL11;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

//@SideOnly(Side.CLIENT)
public class ModelSlimeHead extends ModelHeadBase {
    public ModelRenderer Mouth;
    public ModelRenderer LeftEye;
    public ModelRenderer RightEye;
    public ModelRenderer Outer;

    public ModelSlimeHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftEye = new ModelRenderer(this, 32, 4);
        this.LeftEye.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEye.addBox(1.25F, -6.0F, -3.5F, 2, 2, 2, 0.0F);
        this.RightEye = new ModelRenderer(this, 32, 0);
        this.RightEye.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEye.addBox(-3.25F, -6.0F, -3.5F, 2, 2, 2, 0.0F);
        this.Mouth = new ModelRenderer(this, 32, 8);
        this.Mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mouth.addBox(0.0F, -3.0F, -3.5F, 1, 1, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 16);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-3.0F, -7.0F, -3.0F, 6, 6, 6, 0.0F);
        this.Head.addChild(this.LeftEye);
        this.Head.addChild(this.RightEye);
        this.Head.addChild(this.Mouth);
        
        // Outside cube - transparency
        this.Outer = new ModelRenderer(this, 0, 0);
        this.Outer.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Outer.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
    }
    
    @Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	
    	this.setRotationAngles(this.Outer, netHeadYaw, headPitch);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.Outer.render(scale);
        GlStateManager.disableBlend();
	}

}
