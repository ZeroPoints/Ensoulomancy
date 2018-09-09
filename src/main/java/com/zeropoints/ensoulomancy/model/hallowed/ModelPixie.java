package com.zeropoints.ensoulomancy.model.hallowed;

import com.zeropoints.ensoulomancy.entity.hallowed.EntityPixie;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Pixie - ChickenMobile
 * Created using Tabula 7.0.0
 */
public class ModelPixie extends ModelBiped {
    //public ModelRenderer bipedBody;
    public ModelRenderer LeftWing;
    public ModelRenderer RightWing;
    //public ModelRenderer bipedLeftArm; // Biped arm already exists, for holding items etc
    //public ModelRenderer bipedRightArm;
    //public ModelRenderer Head;
    //public ModelRenderer bipedLeftLeg;
    //public ModelRenderer bipedRightLeg;
    public ModelRenderer Pants;
    public ModelRenderer LeftWingBottom;
    public ModelRenderer RightWingBottom;

    public ModelPixie() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Pants = new ModelRenderer(this, 0, 24);
        this.Pants.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Pants.addBox(-3.0F, 0.0F, -2.0F, 6, 4, 4, 0.0F);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 18, 12);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(1.2F, 8.0F, 0.0F);
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.bipedBody = new ModelRenderer(this, 0, 12);
        this.bipedBody.setRotationPoint(0.0F, 6.0F, -4.0F);
        this.bipedBody.addBox(-2.5F, 0.0F, -2.0F, 5, 7, 4, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 18, 12);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(2.5F, 0.0F, 0.0F);
        this.bipedLeftArm.addBox(0.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
        this.RightWingBottom = new ModelRenderer(this, 26, 16);
        this.RightWingBottom.setRotationPoint(0.0F, 7.0F, 1.0F);
        this.RightWingBottom.addBox(-16.0F, -3.5F, 0.0F, 16, 7, 1, 0.0F);
        this.setRotateAngle(RightWingBottom, 0.2617993877991494F, 0.6108652381980153F, -0.20943951023931953F);
        this.LeftWingBottom = new ModelRenderer(this, 26, 16);
        this.LeftWingBottom.mirror = true;
        this.LeftWingBottom.setRotationPoint(0.0F, 7.0F, 1.0F);
        this.LeftWingBottom.addBox(0.0F, -3.5F, 0.0F, 16, 7, 1, 0.0F);
        this.setRotateAngle(LeftWingBottom, 0.2617993877991494F, -0.6108652381980153F, 0.20943951023931953F);
        this.LeftWing = new ModelRenderer(this, 22, 24);
        this.LeftWing.mirror = true;
        this.LeftWing.setRotationPoint(0.0F, 3.0F, 2.0F);
        this.LeftWing.addBox(0.0F, -3.5F, 0.0F, 20, 7, 1, 0.0F);
        this.setRotateAngle(LeftWing, 0.0F, -0.5235987755982988F, -0.3839724354387525F);
        this.RightWing = new ModelRenderer(this, 22, 24);
        this.RightWing.setRotationPoint(0.0F, 3.0F, 2.0F);
        this.RightWing.addBox(-20.0F, -3.5F, 0.0F, 20, 7, 1, 0.0F);
        this.setRotateAngle(RightWing, 0.0F, 0.5235987755982988F, 0.3839724354387525F);
        this.bipedRightArm = new ModelRenderer(this, 18, 12);
        this.bipedRightArm.setRotationPoint(-2.5F, 0.0F, 0.0F);
        this.bipedRightArm.addBox(-2.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 18, 12);
        this.bipedRightLeg.setRotationPoint(-1.2F, 8.0F, 0.0F);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.bipedBody.addChild(this.Pants);
        this.bipedBody.addChild(this.bipedHead);
        this.bipedBody.addChild(this.bipedLeftLeg);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.bipedBody.addChild(this.RightWingBottom);
        this.bipedBody.addChild(this.LeftWingBottom);
        this.bipedBody.addChild(this.LeftWing);
        this.bipedBody.addChild(this.RightWing);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.bipedRightLeg);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.bipedBody.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netbipedHeadYaw, float bipedHeadPitch, float scaleFactor, Entity entityIn) {
    	this.bipedHead.rotateAngleX = bipedHeadPitch * 0.017453292F;
        this.bipedHead.rotateAngleY = netbipedHeadYaw * 0.017453292F;
        this.bipedHead.rotateAngleZ = 0.0F;
               
        if (entityIn.getDataManager().get(EntityPixie.flying)) {
        	// Legs
        	this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            
            // Arms
            this.bipedLeftArm.rotateAngleX = this.bipedRightLeg.rotateAngleX * 0.6F - 1.0F;
            this.bipedRightArm.rotateAngleX = this.bipedLeftLeg.rotateAngleX * 0.6F - 1.0F;
            
            // Wings
            //this.LeftWing.rotateAngleY = -0.6F;
            //this.RightWing.rotateAngleY = -this.LeftWing.rotateAngleY;
        }
        else {
        	// Leg Swing
            this.bipedLeftLeg.rotateAngleX = MathHelper.sin(ageInTicks / 10) * 0.6F;
            this.bipedRightLeg.rotateAngleX = MathHelper.cos(ageInTicks / 15) * 0.6F;
        	
        	// Arm Swing
        	
        	// Body Swing
     		//this.bipedBody.rotateAngleX = ((float)Math.PI / 8F) + MathHelper.cos(ageInTicks * 0.1F) * 0.05F;
     		//this.bipedBody.rotateAngleY = 0.0F;
            
            // Wings
            float speed = 0.75F;
            float magnitude = 0.2F;
            
            this.RightWing.rotateAngleY = MathHelper.cos(ageInTicks * speed) * (float)Math.PI * magnitude + 0.6F * limbSwingAmount;
            this.LeftWing.rotateAngleY = -this.RightWing.rotateAngleY;
            this.RightWing.rotateAngleX = MathHelper.sin(ageInTicks * speed) * (float)Math.PI * magnitude / 3 * limbSwingAmount;
            this.LeftWing.rotateAngleX = this.RightWing.rotateAngleX;
            
            this.RightWingBottom.rotateAngleY = MathHelper.cos(ageInTicks * speed + 0.3F) * (float)Math.PI * magnitude + 0.8F * limbSwingAmount;
            this.LeftWingBottom.rotateAngleY = -this.RightWingBottom.rotateAngleY;
            this.RightWingBottom.rotateAngleX = MathHelper.sin(ageInTicks * speed) * (float)Math.PI * magnitude / 2 * limbSwingAmount;
            this.LeftWingBottom.rotateAngleX = this.RightWingBottom.rotateAngleX;
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
