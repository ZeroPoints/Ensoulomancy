package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelWitchHead extends ModelHeadBase {
    public ModelRenderer Nose;
    public ModelRenderer HatBrim;
    public ModelRenderer Wart;
    public ModelRenderer HatMid;
    public ModelRenderer HatUpper;
    public ModelRenderer HatStalk;

    public ModelWitchHead() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.Wart = new ModelRenderer(this, 0, 0);
        this.Wart.mirror = true;
        this.Wart.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.Wart.addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, 0.0F);
        this.HatStalk = new ModelRenderer(this, 0, 95);
        this.HatStalk.setRotationPoint(1.75F, -2.0F, 2.0F);
        this.HatStalk.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(HatStalk, -0.20943951023931953F, 0.0F, 0.10471975511965977F);
        this.HatUpper = new ModelRenderer(this, 0, 87);
        this.HatUpper.setRotationPoint(1.75F, -4.0F, 2.0F);
        this.HatUpper.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(HatUpper, -0.10471975511965977F, 0.0F, 0.05235987755982988F);
        this.Nose = new ModelRenderer(this, 24, 0);
        this.Nose.addChild(this.Wart);
        this.Nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.Nose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Nose, 0.0F, 0.0F, 0.04363323129985824F);
        this.HatMid = new ModelRenderer(this, 0, 76);
        this.HatMid.setRotationPoint(1.75F, -4.0F, 2.0F);
        this.HatMid.addBox(0.0F, 0.0F, 0.0F, 7, 4, 7, 0.0F);
        this.setRotateAngle(HatMid, -0.05235987755982988F, 0.0F, 0.02617993877991494F);
        this.HatBrim = new ModelRenderer(this, 0, 64);
        this.HatBrim.setRotationPoint(-5.0F, -10.03F, -5.0F);
        this.HatBrim.addBox(0.0F, 0.0F, 0.0F, 10, 2, 10, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.HatUpper.addChild(this.HatStalk);
        this.HatMid.addChild(this.HatUpper);
        this.Head.addChild(this.Nose);
        this.HatBrim.addChild(this.HatMid);
        this.Head.addChild(this.HatBrim);
    }
    
}
