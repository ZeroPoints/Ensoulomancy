package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelPigHead extends ModelHeadBase {
    public ModelRenderer Nose;

    public ModelPigHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Nose = new ModelRenderer(this, 16, 16);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-2.0F, -4.0F, -5.0F, 4, 3, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.Head.addChild(this.Nose);
    }

}
