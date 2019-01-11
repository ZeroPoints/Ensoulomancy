package com.zeropoints.ensoulomancy.model.husk.legs;

import com.zeropoints.ensoulomancy.model.husk.HuskLegsBase;
import com.zeropoints.ensoulomancy.model.husk.head.SquidHead;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SpiderLegs extends HuskLegsBase<SpiderLegs> {
	
    private final ModelRenderer spiderLeg1;
    private final ModelRenderer spiderLeg2;
    private final ModelRenderer spiderLeg3;
    private final ModelRenderer spiderLeg4;
    private final ModelRenderer spiderLeg5;
    private final ModelRenderer spiderLeg6;
    private final ModelRenderer spiderLeg7;
    private final ModelRenderer spiderLeg8;
    
	public SpiderLegs() {
		this(new Vec3d(0,0,0));
	}
	
    public SpiderLegs(Vec3d offset) {
    	// assuming the bodywidth is mirrored from the center, we can assume that the offset of the legs need to be half from center
    	float w = 4;
    	
        this.spiderLeg1 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg1, offset, -15, -1, -1, 16, 2, 2);
        this.spiderLeg1.setRotationPoint(-w, 15, 2);
        
        this.spiderLeg2 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg2, offset, -1, -1, -1, 16, 2, 2);
        this.spiderLeg2.setRotationPoint(w, 15, 2);
        
        this.spiderLeg3 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg3, offset, -15, -1, -1, 16, 2, 2);
        this.spiderLeg3.setRotationPoint(-w, 15, 1);
        
        this.spiderLeg4 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg4, offset, -1, -1, -1, 16, 2, 2);
        this.spiderLeg4.setRotationPoint(w, 15, 1);
        
        this.spiderLeg5 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg5, offset, -15, -1, -1, 16, 2, 2);
        this.spiderLeg5.setRotationPoint(-w, 15, 0);
        
        this.spiderLeg6 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg6, offset, -1, -1, -1, 16, 2, 2);
        this.spiderLeg6.setRotationPoint(w, 15, 0);
        
        this.spiderLeg7 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg7, offset, -15, -1, -1, 16, 2, 2);
        this.spiderLeg7.setRotationPoint(-w, 15, -1);
        
        this.spiderLeg8 = new ModelRenderer(this, 18, 0);
        this.addBox(this.spiderLeg8, offset, -1, -1, -1, 16, 2, 2);
        this.spiderLeg8.setRotationPoint(w, 15, -1);
        
        this.base = new ModelRenderer(this, 0, 0);
    	this.addBaseBox(this.base, offset, 0, 0, 0, 0, 0, 0);
    	this.base.setRotationPoint(0, 0, 0);
        
        this.base.addChild(spiderLeg1);
        this.base.addChild(spiderLeg2);
        this.base.addChild(spiderLeg3);
        this.base.addChild(spiderLeg4);
        this.base.addChild(spiderLeg5);
        this.base.addChild(spiderLeg6);
        this.base.addChild(spiderLeg7);
        this.base.addChild(spiderLeg8);
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
        float f3 = 0.58119464F;
        float limbF = limbSwing * 0.6662F;
        
        this.spiderLeg1.rotateAngleZ = -f1;
        this.spiderLeg2.rotateAngleZ = f1;
        this.spiderLeg3.rotateAngleZ = -f3;
        this.spiderLeg4.rotateAngleZ = f3;
        this.spiderLeg5.rotateAngleZ = -f3;
        this.spiderLeg6.rotateAngleZ = f3;
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