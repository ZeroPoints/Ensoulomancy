package com.zeropoints.ensoulomancy.model.husk.legs;

import com.zeropoints.ensoulomancy.model.husk.HuskLegsBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class QuadrapedLegs<T> extends HuskLegsBase<T> {
	
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public QuadrapedLegs(int height, float scale, Vec3d offset) {
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.setRotationPoint(-3, (float)(24 - height), 7);
        this.addBox(this.leg1, offset, -2, 0, -2, 4, height, 4, scale);
        
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.setRotationPoint(3, (float)(24 - height), 7);
        this.addBox(this.leg2, offset, -2, 0, -2, 4, height, 4, scale);
        
        this.leg3 = new ModelRenderer(this, 0, 16);
        this.leg3.setRotationPoint(-3, (float)(24 - height), -5);
        this.addBox(this.leg3, offset, -2, 0, -2, 4, height, 4, scale);
        
        this.leg4 = new ModelRenderer(this, 0, 16);
        this.leg4.setRotationPoint(3, (float)(24 - height), -5);
        this.addBox(this.leg4, offset, -2, 0, -2, 4, height, 4, scale);
        
        this.base.addChild(this.leg1);
        this.base.addChild(this.leg2);
        this.base.addChild(this.leg3);
        this.base.addChild(this.leg4);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	float f1 = limbSwing * 0.6662F;
    	float f2 = 1.4F * limbSwingAmount;
    	float f3 = MathHelper.cos(f1) * f2;
    	float f4 = MathHelper.cos(f1 + (float)Math.PI) * f2;
    	
        this.leg1.rotateAngleX = f3;
        this.leg2.rotateAngleX = f4;
        this.leg3.rotateAngleX = f4;
        this.leg4.rotateAngleX = f3;
    }
}