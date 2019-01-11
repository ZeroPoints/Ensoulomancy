package com.zeropoints.ensoulomancy.model.husk.legs;

import com.zeropoints.ensoulomancy.model.husk.HuskLegsBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class BipedLegs<T> extends HuskLegsBase<T> {
	
    public BipedLegs(int height, float scale, Vec3d offset) {
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.setRotationPoint(-3, (float)(24 - height), 7);
        this.addBox(this.leg1, offset, -2, 0, -2, 4, height, 4, scale);
        
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.setRotationPoint(3, (float)(24 - height), 7);
        this.addBox(this.leg2, offset, -2, 0, -2, 4, height, 4, scale);
        
        this.base.addChild(this.leg1);
        this.base.addChild(this.leg2);
    }
    
    public BipedLegs() {
    	
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entityIn) {
    	float f1 = limbSwing * 0.6662F;
    	float f2 = 1.4F * limbSwingAmount;
        this.leg1.rotateAngleX = MathHelper.cos(f1) * f2;
        this.leg2.rotateAngleX = MathHelper.cos(f1 + (float)Math.PI) * f2;
    }
    
}