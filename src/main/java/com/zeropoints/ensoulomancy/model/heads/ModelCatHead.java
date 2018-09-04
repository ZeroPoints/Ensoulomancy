package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelCatHead extends ModelHeadBase {
    public ModelRenderer Nose;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;
    
    public static String[] subTypes = new String[] {"ocelot", "black", "red", "siamese"};

    public ModelCatHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightEar = new ModelRenderer(this, 0, 10);
        this.RightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEar.addBox(-2.0F, -5.0F, 0.0F, 1, 1, 2, 0.0F);
        this.LeftEar = new ModelRenderer(this, 6, 10);
        this.LeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEar.addBox(1.0F, -5.0F, 0.0F, 1, 1, 2, 0.0F);
        this.Nose = new ModelRenderer(this, 0, 24);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-1.5F, -2.0F, -4.0F, 3, 2, 2, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-2.5F, -4.0F, -3.0F, 5, 4, 5, 0.0F);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.Nose);
    }

}
