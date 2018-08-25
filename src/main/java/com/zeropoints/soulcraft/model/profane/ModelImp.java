package com.zeropoints.soulcraft.model.profane;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * Imp - ChickenMobile
 * Created using Tabula 7.0.0
 */
public class ModelImp extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer Neck;
    public ModelRenderer LeftThigh;
    public ModelRenderer RightThigh;
    public ModelRenderer LeftWing;
    public ModelRenderer RightWing;
    public ModelRenderer LeftArm;
    public ModelRenderer RightArm;
    public ModelRenderer Tail;
    public ModelRenderer Head;
    public ModelRenderer LeftHorn;
    public ModelRenderer RightHorn;
    public ModelRenderer LeftHornTip;
    public ModelRenderer RightHornTip;
    public ModelRenderer LeftLeg;
    public ModelRenderer LeftFoot;
    public ModelRenderer RightLeg;
    public ModelRenderer RightFoot;
    public ModelRenderer LeftWingEnd;
    public ModelRenderer RightWingEnd;
    public ModelRenderer Tail_2;

    public ModelImp() {
    	this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightWingEnd = new ModelRenderer(this, 25, 12);
        this.RightWingEnd.setRotationPoint(-13.0F, 0.0F, 0.0F);
        this.RightWingEnd.addBox(-9.0F, -6.0F, 0.0F, 9, 11, 1, 0.0F);
        this.LeftHorn = new ModelRenderer(this, 0, 0);
        this.LeftHorn.setRotationPoint(2.0F, -4.9F, 0.0F);
        this.LeftHorn.addBox(-0.5F, -3.0F, -0.5F, 1, 2, 2, 0.0F);
        this.setRotateAngle(LeftHorn, 0.0F, 0.0F, 0.593411945678072F);
        this.Tail = new ModelRenderer(this, 2, 11);
        this.Tail.setRotationPoint(0.0F, 10.0F, 2.5F);
        this.Tail.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Tail, 0.5235987755982988F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 3, 23);
        this.RightLeg.setRotationPoint(0.0F, 4.0F, -0.5F);
        this.RightLeg.addBox(-1.5F, 0.0F, -1.0F, 3, 4, 2, 0.0F);
        this.RightFoot = new ModelRenderer(this, 25, 24);
        this.RightFoot.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.RightFoot.addBox(-1.5F, 0.0F, -1.0F, 3, 6, 2, 0.0F);
        this.setRotateAngle(RightFoot, -1.1344640137963142F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 0, 12);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(3.0F, 3.0F, 0.0F);
        this.LeftArm.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(LeftArm, -1.0471975511965976F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 3, 18);
        this.Body.setRotationPoint(0.0F, 8.0F, -4.0F);
        this.Body.addBox(-3.0F, 2.0F, -2.0F, 6, 9, 5, 0.0F);
        this.setRotateAngle(Body, 0.6829473363053812F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 3, 23);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(0.0F, 4.0F, -0.5F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -1.0F, 3, 4, 2, 0.0F);
        this.Tail_2 = new ModelRenderer(this, 2, 11);
        this.Tail_2.setRotationPoint(0.0F, 8.1F, 0.0F);
        this.Tail_2.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(Tail_2, 0.5235987755982988F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 0, 12);
        this.RightArm.setRotationPoint(-3.0F, 3.0F, 0.0F);
        this.RightArm.addBox(-2.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(RightArm, -1.0471975511965976F, 0.0F, 0.0F);
        this.LeftWingEnd = new ModelRenderer(this, 25, 12);
        this.LeftWingEnd.mirror = true;
        this.LeftWingEnd.setRotationPoint(13.0F, 0.0F, 0.0F);
        this.LeftWingEnd.addBox(0.0F, -6.0F, 0.0F, 9, 11, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.Head.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
        this.RightWing = new ModelRenderer(this, 24, 0);
        this.RightWing.setRotationPoint(0.0F, 6.0F, 3.0F);
        this.RightWing.addBox(-13.0F, -6.0F, 0.0F, 13, 11, 1, 0.0F);
        this.setRotateAngle(RightWing, 0.0F, 0.5235987755982988F, 0.0F);
        this.LeftHornTip = new ModelRenderer(this, 18, 0);
        this.LeftHornTip.mirror = true;
        this.LeftHornTip.setRotationPoint(0.0F, -3.0F, 0.5F);
        this.LeftHornTip.addBox(-0.5F, -1.9F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(LeftHornTip, 0.0F, 0.0F, -0.41887902047863906F);
        this.RightHorn = new ModelRenderer(this, 0, 0);
        this.RightHorn.setRotationPoint(-2.0F, -4.9F, 0.0F);
        this.RightHorn.addBox(-0.5F, -3.0F, -0.5F, 1, 2, 2, 0.0F);
        this.setRotateAngle(RightHorn, 0.0F, 0.0F, -0.593411945678072F);
        this.Neck = new ModelRenderer(this, 7, 0);
        this.Neck.setRotationPoint(0.0F, 0.8F, 0.6F);
        this.Neck.addBox(-2.0F, 0.0F, -1.0F, 4, 2, 3, 0.0F);
        this.setRotateAngle(Neck, -0.5235987755982988F, 0.0F, 0.0F);
        this.LeftThigh = new ModelRenderer(this, 4, 18);
        this.LeftThigh.mirror = true;
        this.LeftThigh.setRotationPoint(2.0F, 8.0F, 1.0F);
        this.LeftThigh.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(LeftThigh, -0.5235987755982988F, 0.0F, 0.0F);
        this.LeftWing = new ModelRenderer(this, 24, 0);
        this.LeftWing.mirror = true;
        this.LeftWing.setRotationPoint(0.0F, 6.0F, 3.0F);
        this.LeftWing.addBox(0.0F, -6.0F, 0.0F, 13, 11, 1, 0.0F);
        this.setRotateAngle(LeftWing, 0.0F, -0.5235987755982988F, 0.0F);
        this.RightHornTip = new ModelRenderer(this, 18, 0);
        this.RightHornTip.mirror = true;
        this.RightHornTip.setRotationPoint(0.0F, -3.0F, 0.5F);
        this.RightHornTip.addBox(-0.5F, -1.9F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(RightHornTip, 0.0F, 0.0F, 0.41887902047863906F);
        this.LeftFoot = new ModelRenderer(this, 25, 24);
        this.LeftFoot.mirror = true;
        this.LeftFoot.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.LeftFoot.addBox(-1.5F, 0.0F, -1.0F, 3, 6, 2, 0.0F);
        this.setRotateAngle(LeftFoot, -1.1344640137963142F, 0.0F, 0.0F);
        this.RightThigh = new ModelRenderer(this, 4, 18);
        this.RightThigh.setRotationPoint(-2.0F, 8.0F, 1.0F);
        this.RightThigh.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(RightThigh, -0.5235987755982988F, 0.0F, 0.0F);
        this.RightWing.addChild(this.RightWingEnd);
        this.Head.addChild(this.LeftHorn);
        this.Body.addChild(this.Tail);
        this.RightThigh.addChild(this.RightLeg);
        this.RightLeg.addChild(this.RightFoot);
        this.Body.addChild(this.LeftArm);
        this.LeftThigh.addChild(this.LeftLeg);
        this.Tail.addChild(this.Tail_2);
        this.Body.addChild(this.RightArm);
        this.LeftWing.addChild(this.LeftWingEnd);
        this.Neck.addChild(this.Head);
        this.Body.addChild(this.RightWing);
        this.LeftHorn.addChild(this.LeftHornTip);
        this.Head.addChild(this.RightHorn);
        this.Body.addChild(this.Neck);
        this.Body.addChild(this.LeftThigh);
        this.Body.addChild(this.LeftWing);
        this.RightHorn.addChild(this.RightHornTip);
        this.LeftLeg.addChild(this.LeftFoot);
        this.Body.addChild(this.RightThigh);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	//super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	
    	this.Head.rotateAngleX = headPitch * 0.017453292F;
        this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.Head.rotateAngleZ = 0.0F;
        this.Body.rotateAngleX = ((float)Math.PI / 4F) + MathHelper.cos(ageInTicks * 0.1F) * 0.15F;
        this.Body.rotateAngleY = 0.0F;
        this.RightWing.rotateAngleY = MathHelper.cos(ageInTicks * 1.3F) * (float)Math.PI * 0.25F;
        this.LeftWing.rotateAngleY = -this.RightWing.rotateAngleY;
        this.LeftWingEnd.rotateAngleY = this.RightWing.rotateAngleY * 0.5F;
        this.RightWingEnd.rotateAngleY = -this.RightWing.rotateAngleY * 0.5F;
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
