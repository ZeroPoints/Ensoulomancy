package com.zeropoints.ensoulomancy.model.husk;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHuskSpiderLegs extends ModelBase {
	
    /** Spider's first leg */
    public ModelRenderer spiderLeg1;
    /** Spider's second leg */
    public ModelRenderer spiderLeg2;
    /** Spider's third leg */
    public ModelRenderer spiderLeg3;
    /** Spider's fourth leg */
    public ModelRenderer spiderLeg4;
    /** Spider's fifth leg */
    public ModelRenderer spiderLeg5;
    /** Spider's sixth leg */
    public ModelRenderer spiderLeg6;
    /** Spider's seventh leg */
    public ModelRenderer spiderLeg7;
    /** Spider's eight leg */
    public ModelRenderer spiderLeg8;

    public ModelHuskSpiderLegs(float bodyWidth, float scale) {
    	// assuming the bodywidth is mirrored from the center, we can assume that the offset of the legs need to be half from center
    	float w = bodyWidth / 2F;
    	
        this.spiderLeg1 = new ModelRenderer(this, 18, 0);
        this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg1.setRotationPoint(-w, 15.0F, 2.0F);
        
        this.spiderLeg2 = new ModelRenderer(this, 18, 0);
        this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg2.setRotationPoint(w, 15.0F, 2.0F);
        
        this.spiderLeg3 = new ModelRenderer(this, 18, 0);
        this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg3.setRotationPoint(-w, 15.0F, 1.0F);
        
        this.spiderLeg4 = new ModelRenderer(this, 18, 0);
        this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg4.setRotationPoint(w, 15.0F, 1.0F);
        
        this.spiderLeg5 = new ModelRenderer(this, 18, 0);
        this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg5.setRotationPoint(-w, 15.0F, scale);
        
        this.spiderLeg6 = new ModelRenderer(this, 18, 0);
        this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg6.setRotationPoint(w, 15.0F, scale);
        
        this.spiderLeg7 = new ModelRenderer(this, 18, 0);
        this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg7.setRotationPoint(-w, 15.0F, -1.0F);
        
        this.spiderLeg8 = new ModelRenderer(this, 18, 0);
        this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, scale);
        this.spiderLeg8.setRotationPoint(w, 15.0F, -1.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.spiderLeg1.render(scale);
        this.spiderLeg2.render(scale);
        this.spiderLeg3.render(scale);
        this.spiderLeg4.render(scale);
        this.spiderLeg5.render(scale);
        this.spiderLeg6.render(scale);
        this.spiderLeg7.render(scale);
        this.spiderLeg8.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	float PI = (float)Math.PI;
        float f1 = PI / 4F;
        float f2 = f1 / 2F;
        float limbF = limbSwing * 0.6662F;
        
        this.spiderLeg1.rotateAngleZ = -f1;
        this.spiderLeg2.rotateAngleZ = f1;
        this.spiderLeg3.rotateAngleZ = -0.58119464F;
        this.spiderLeg4.rotateAngleZ = 0.58119464F;
        this.spiderLeg5.rotateAngleZ = -0.58119464F;
        this.spiderLeg6.rotateAngleZ = 0.58119464F;
        this.spiderLeg7.rotateAngleZ = -f1;
        this.spiderLeg8.rotateAngleZ = f1;
        
        this.spiderLeg1.rotateAngleY = f1;
        this.spiderLeg2.rotateAngleY = -f1;
        this.spiderLeg3.rotateAngleY = f2;
        this.spiderLeg4.rotateAngleY = -f2;
        this.spiderLeg5.rotateAngleY = -f2;
        this.spiderLeg6.rotateAngleY = f2;
        this.spiderLeg7.rotateAngleY = -f1;
        this.spiderLeg8.rotateAngleY = f1;
        
        float leg1YRot = -(MathHelper.cos(limbF * 2F) * 0.4F) * limbSwingAmount;
        float leg2YRot = -(MathHelper.cos(limbF * 2F + PI) * 0.4F) * limbSwingAmount;
        float leg3YRot = -(MathHelper.cos(limbF * 2F + (PI / 2F)) * 0.4F) * limbSwingAmount;
        float leg4YRot = -(MathHelper.cos(limbF * 2F + (PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        float leg1ZRot = Math.abs(MathHelper.sin(limbF) * 0.4F) * limbSwingAmount;
        float leg2ZRot = Math.abs(MathHelper.sin(limbF + PI) * 0.4F) * limbSwingAmount;
        float leg3ZRot = Math.abs(MathHelper.sin(limbF + (PI / 2F)) * 0.4F) * limbSwingAmount;
        float leg4ZRot = Math.abs(MathHelper.sin(limbF + (PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        
        this.spiderLeg1.rotateAngleY += leg1YRot;
        this.spiderLeg2.rotateAngleY += -leg1YRot;
        this.spiderLeg3.rotateAngleY += leg2YRot;
        this.spiderLeg4.rotateAngleY += -leg2YRot;
        this.spiderLeg5.rotateAngleY += leg3YRot;
        this.spiderLeg6.rotateAngleY += -leg3YRot;
        this.spiderLeg7.rotateAngleY += leg4YRot;
        this.spiderLeg8.rotateAngleY += -leg4YRot;
        this.spiderLeg1.rotateAngleZ += leg1ZRot;
        this.spiderLeg2.rotateAngleZ += -leg1ZRot;
        this.spiderLeg3.rotateAngleZ += leg2ZRot;
        this.spiderLeg4.rotateAngleZ += -leg2ZRot;
        this.spiderLeg5.rotateAngleZ += leg3ZRot;
        this.spiderLeg6.rotateAngleZ += -leg3ZRot;
        this.spiderLeg7.rotateAngleZ += leg4ZRot;
        this.spiderLeg8.rotateAngleZ += -leg4ZRot;
    }
}