package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelRabbitHead extends ModelHeadBase {
    public ModelRenderer Nose;
    public ModelRenderer LeftEar;
    public ModelRenderer RightEar;
    
    public static String[] subTypes = new String[] {"brown","white","black","white_splotched","gold","caerbannog","toast"};

    public ModelRabbitHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightEar = new ModelRenderer(this, 52, 0);
        this.RightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEar.addBox(-2.0F, -9.0F, 1.3F, 2, 5, 1, 0.0F);
        this.setRotateAngle(RightEar, 0.0F, -0.2617993877991494F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 58, 0);
        this.LeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEar.addBox(0.0F, -9.0F, 1.3F, 2, 5, 1, 0.0F);
        this.setRotateAngle(LeftEar, 0.0F, 0.2617993877991494F, 0.0F);
        this.Nose = new ModelRenderer(this, 32, 9);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-0.5F, -2.5F, -3.0F, 1, 1, 1, 0.0F);
        this.Head = new ModelRenderer(this, 32, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-2.5F, -4.0F, -2.5F, 5, 4, 5, 0.0F);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.Nose);
    }
    
}
