package com.zeropoints.ensoulomancy.model.ghost;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelIttanMomen - ChickenMobile
 * Created using Tabula 7.0.0
 */
public class ModelIttanMomen extends ModelBase {
    public ModelRenderer Head;
    public ModelRenderer Segment1;
    public ModelRenderer Segment2;
    public ModelRenderer Segment3;
    public ModelRenderer Segment4;
    public ModelRenderer Segment5;
    public ModelRenderer Segment6;
    public ModelRenderer Segment7;
    
    private ModelIttanMomen.State state = ModelIttanMomen.State.BLOCK;

    public ModelIttanMomen() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Head = new ModelRenderer(this, 16, 0);
        this.Head.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Head.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Segment1 = new ModelRenderer(this, 0, 2);
        this.Segment1.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.Segment1.addBox(0.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Segment2 = new ModelRenderer(this, 0, 4);
        this.Segment2.setRotationPoint(16.0F, 2.0F, 0.0F);
        this.Segment2.addBox(-16.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Segment3 = new ModelRenderer(this, 0, 6);
        this.Segment3.setRotationPoint(-16.0F, 2.0F, 0.0F);
        this.Segment3.addBox(0.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Segment4 = new ModelRenderer(this, 0, 8);
        this.Segment4.setRotationPoint(16.0F, 2.0F, 0.0F);
        this.Segment4.addBox(-16.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Segment5 = new ModelRenderer(this, 0, 10);
        this.Segment5.setRotationPoint(-16.0F, 2.0F, 0.0F);
        this.Segment5.addBox(0.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Segment6 = new ModelRenderer(this, 0, 12);
        this.Segment6.setRotationPoint(16.0F, 2.0F, 0.0F);
        this.Segment6.addBox(-16.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Segment7 = new ModelRenderer(this, 0, 14);
        this.Segment7.setRotationPoint(-16.0F, 2.0F, 0.0F);
        this.Segment7.addBox(0.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Head.addChild(this.Segment1);
        this.Segment1.addChild(this.Segment2);
        this.Segment2.addChild(this.Segment3);
        this.Segment3.addChild(this.Segment4);
        this.Segment4.addChild(this.Segment5);
        this.Segment5.addChild(this.Segment6);
        this.Segment6.addChild(this.Segment7);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Head.render(f5);
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    	if (!entitylivingbaseIn.onGround) {
    		this.state = ModelIttanMomen.State.FLYING;
    		return;
    	}
    	this.state = ModelIttanMomen.State.BLOCK;
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch, float scaleFactor, Entity entityIn) {
    	//this.Head.rotateAngleX = bipedHeadPitch * 0.017453292F;
        //this.Head.rotateAngleY = netbipedHeadYaw * 0.017453292F;
        this.Head.rotateAngleZ = 0.0F;

        if (this.state == ModelIttanMomen.State.FLYING) {
        	// Rainicorn it up!!!
        	int counter = 0;
        	final float fPI = (float)Math.PI;
        	final float pi7 = fPI / 6.0F; 
        	final float sinP = ageInTicks / 7.0F;
        	
        	this.Head.rotateAngleZ = -1.2F + MathHelper.sin(sinP - 1.0F) * 0.4F;
        	
        	// Body Swing
        	this.Segment1.rotateAngleZ = MathHelper.sin(sinP + ++counter / pi7) * 0.5F - fPI - 5.0F;
     		this.Segment2.rotateAngleZ = MathHelper.sin(sinP + ++counter / pi7) * 0.5F - fPI;
     		this.Segment3.rotateAngleZ = MathHelper.sin(sinP + ++counter / pi7) * 0.6F - fPI;
     		this.Segment4.rotateAngleZ = MathHelper.sin(sinP + ++counter / pi7) * 0.75F - fPI;
     		this.Segment5.rotateAngleZ = MathHelper.sin(sinP + ++counter / pi7) * 0.8F - fPI;
     		this.Segment6.rotateAngleZ = MathHelper.sin(sinP + ++counter / pi7) * 0.8F - fPI;
     		this.Segment7.rotateAngleZ = MathHelper.sin(sinP + ++counter / pi7) * 0.8F - fPI;
     		return;
        }
        
    	// Folded cube state
    	this.Segment1.rotateAngleZ = 0.0F;
    	this.Segment2.rotateAngleZ = 0.0F;
    	this.Segment3.rotateAngleZ = 0.0F;
    	this.Segment4.rotateAngleZ = 0.0F;
    	this.Segment5.rotateAngleZ = 0.0F;
    	this.Segment6.rotateAngleZ = 0.0F;
    	this.Segment7.rotateAngleZ = 0.0F;
    }
    
    @SideOnly(Side.CLIENT)
    static enum State {
        FLYING,
        BLOCK
    }
    
}
