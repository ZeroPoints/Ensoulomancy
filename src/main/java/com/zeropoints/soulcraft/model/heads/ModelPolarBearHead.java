package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelPolarBearHead extends ModelHeadBase {
    public ModelRenderer Nose;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;

    public ModelPolarBearHead() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.RightEar = new ModelRenderer(this, 26, 0);
        this.RightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEar.addBox(-4.5F, -8.0F, -1.0F, 2, 2, 1, 0.0F);
        this.Nose = new ModelRenderer(this, 0, 44);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-2.5F, -3.0F, -6.5F, 5, 3, 3, 0.0F);
        this.LeftEar = new ModelRenderer(this, 26, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEar.addBox(2.5F, -8.0F, -1.0F, 2, 2, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-3.5F, -7.0F, -3.5F, 7, 7, 7, 0.0F);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.Nose);
        this.Head.addChild(this.LeftEar);
    }

}
