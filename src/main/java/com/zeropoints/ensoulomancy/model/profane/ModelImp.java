package com.zeropoints.ensoulomancy.model.profane;

import com.zeropoints.ensoulomancy.entity.profane.EntityImp;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Imp - ChickenMobile
 * Created using Tabula 7.0.0
 */
public class ModelImp extends ModelBiped {
    public ModelRenderer Neck;
    public ModelRenderer LeftThigh;
    public ModelRenderer RightThigh;
    public ModelRenderer LeftWing;
    public ModelRenderer RightWing;
    public ModelRenderer Tail;
    public ModelRenderer LeftHorn;
    public ModelRenderer RightHorn;
    public ModelRenderer LeftHornTip;
    public ModelRenderer RightHornTip;
    public ModelRenderer LeftFoot;
    public ModelRenderer RightFoot;
    public ModelRenderer LeftWingEnd;
    public ModelRenderer RightWingEnd;
    public ModelRenderer Tail_2;
    
    private ModelImp.State state = ModelImp.State.STANDING;

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
        this.bipedRightLeg = new ModelRenderer(this, 3, 23);
        this.bipedRightLeg.setRotationPoint(0.0F, 4.0F, -0.5F);
        this.bipedRightLeg.addBox(-1.5F, 0.0F, -1.0F, 3, 4, 2, 0.0F);
        this.RightFoot = new ModelRenderer(this, 25, 24);
        this.RightFoot.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.RightFoot.addBox(-1.5F, 0.0F, -1.0F, 3, 6, 2, 0.0F);
        this.setRotateAngle(RightFoot, -1.1344640137963142F, 0.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 0, 12);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(3.0F, 3.0F, 0.0F);
        this.bipedLeftArm.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.bipedBody = new ModelRenderer(this, 3, 18);
        this.bipedBody.setRotationPoint(0.0F, 8.0F, -4.0F);
        this.bipedBody.addBox(-3.0F, 2.0F, -2.0F, 6, 9, 5, 0.0F);
        this.setRotateAngle(bipedBody, 0.6829473363053812F, 0.0F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 3, 23);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(0.0F, 4.0F, -0.5F);
        this.bipedLeftLeg.addBox(-1.5F, 0.0F, -1.0F, 3, 4, 2, 0.0F);
        this.Tail_2 = new ModelRenderer(this, 2, 11);
        this.Tail_2.setRotationPoint(0.0F, 8.1F, 0.0F);
        this.Tail_2.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(Tail_2, 0.5235987755982988F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 0, 12);
        this.bipedRightArm.setRotationPoint(-3.0F, 3.0F, 0.0F);
        this.bipedRightArm.addBox(-2.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.LeftWingEnd = new ModelRenderer(this, 25, 12);
        this.LeftWingEnd.mirror = true;
        this.LeftWingEnd.setRotationPoint(13.0F, 0.0F, 0.0F);
        this.LeftWingEnd.addBox(0.0F, -6.0F, 0.0F, 9, 11, 1, 0.0F);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.bipedHead.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
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
        this.bipedHead.addChild(this.LeftHorn);
        this.bipedBody.addChild(this.Tail);
        this.RightThigh.addChild(this.bipedRightLeg);
        this.bipedRightLeg.addChild(this.RightFoot);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.LeftThigh.addChild(this.bipedLeftLeg);
        this.Tail.addChild(this.Tail_2);
        this.bipedBody.addChild(this.bipedRightArm);
        this.LeftWing.addChild(this.LeftWingEnd);
        this.Neck.addChild(this.bipedHead);
        this.bipedBody.addChild(this.RightWing);
        this.LeftHorn.addChild(this.LeftHornTip);
        this.bipedHead.addChild(this.RightHorn);
        this.bipedBody.addChild(this.Neck);
        this.bipedBody.addChild(this.LeftThigh);
        this.bipedBody.addChild(this.LeftWing);
        this.RightHorn.addChild(this.RightHornTip);
        this.bipedLeftLeg.addChild(this.LeftFoot);
        this.bipedBody.addChild(this.RightThigh);
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
        
        if (this.state == ModelImp.State.FLYING) {
        	// Body Swing
    		this.bipedBody.rotateAngleX = ((float)Math.PI / 4F) + MathHelper.cos(ageInTicks * 0.1F) * 0.15F;
            this.bipedBody.rotateAngleY = 0.0F;
            
            // Leg Swing
            this.bipedLeftLeg.rotateAngleX = MathHelper.sin(ageInTicks / 10) * 0.3F;
            this.bipedRightLeg.rotateAngleX = MathHelper.cos(ageInTicks / 15) * 0.3F;
            
            // Wings
            this.RightWing.rotateAngleY = MathHelper.cos(ageInTicks * 1.3F) * (float)Math.PI * 0.25F + 0.5F;
            this.LeftWing.rotateAngleY = -this.RightWing.rotateAngleY;
            this.LeftWingEnd.rotateAngleY = this.RightWing.rotateAngleY * 0.5F;
            this.RightWingEnd.rotateAngleY = -this.RightWing.rotateAngleY * 0.5F;
    	}
        else { // Flying
        	// Body
    		this.bipedBody.rotateAngleX = 0.68F;
            this.bipedBody.rotateAngleY = 0.0F;
        	
            // Legs
            this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        	this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            
            // Arms
            this.bipedLeftArm.rotateAngleX = this.bipedRightLeg.rotateAngleX * 0.6F - 1.0F;
            this.bipedRightArm.rotateAngleX = this.bipedLeftLeg.rotateAngleX * 0.6F - 1.0F;
            
            // Wings
            this.LeftWing.rotateAngleY = -0.6F;
            this.RightWing.rotateAngleY = 0.6F;
            this.LeftWingEnd.rotateAngleY = 1.5F;
            this.RightWingEnd.rotateAngleY = -1.5F;
        }
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
    	if (!entity.onGround || (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isFlying)) {
    		this.state = ModelImp.State.FLYING;
    		return;
    	}
    	this.state = ModelImp.State.STANDING;
    }
    
    @Override
    public void postRenderArm(float scale, EnumHandSide side) {
        this.postRender(this.getArmForSide(side), scale);
    }
    
    /**
     * This changes the position and angles for when the item render layer is drawn
     * For now I hackily changed the numbers to be in the correct position. 
     */
    private void postRender(ModelRenderer mr, float scale) {
        GlStateManager.translate(mr.rotationPointX * scale, 14 * scale, 3 * scale);
        float rot = 180F / (float)Math.PI;
        
        if (mr.rotateAngleX != 0F) GlStateManager.rotate(mr.rotateAngleX * rot, 1, 0, 0);
        if (mr.rotateAngleY != 0F) GlStateManager.rotate(mr.rotateAngleY * rot, 0, 1, 0);
        if (mr.rotateAngleZ != 0F) GlStateManager.rotate(mr.rotateAngleZ * rot, 0, 0, 1);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @SideOnly(Side.CLIENT)
    static enum State {
        FLYING,
        STANDING
    }
}
