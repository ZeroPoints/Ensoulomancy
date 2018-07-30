package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

public class ModelWolfHead extends ModelHeadBase {
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;
    public ModelRenderer Nose;

    public ModelWolfHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightEar = new ModelRenderer(this, 16, 14);
        this.RightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEar.addBox(-3.0F, -8.0F, 0.0F, 2, 2, 1, 0.0F);
        this.LeftEar = new ModelRenderer(this, 16, 14);
        this.LeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEar.addBox(1.0F, -8.0F, 0.0F, 2, 2, 1, 0.0F);
        this.Nose = new ModelRenderer(this, 0, 10);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-1.5F, -3.0F, -5.0F, 3, 3, 4, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-3.0F, -6.0F, -2.0F, 6, 6, 4, 0.0F);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.Nose);
    }

}
