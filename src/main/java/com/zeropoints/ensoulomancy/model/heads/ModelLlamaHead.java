package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

public class ModelLlamaHead extends ModelHeadBase {
    public ModelRenderer Nose;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;
    
    public static String[] subTypes = new String[] { "default", "llama_brown", "llama_creamy", "llama_gray", "llama_white" };

    public ModelLlamaHead() {    	
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.Nose = new ModelRenderer(this, 0, 0);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-2.0F, -6.0F, -5.0F, 4, 4, 9, 0.0F);
        this.RightEar = new ModelRenderer(this, 17, 0);
        this.RightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEar.addBox(-4.0F, -11.0F, 1.0F, 3, 3, 2, 0.0F);
        this.LeftEar = new ModelRenderer(this, 17, 0);
        this.LeftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEar.addBox(1.0F, -11.0F, 1.0F, 3, 3, 2, 0.0F);
        this.Head = new ModelRenderer(this, 0, 14);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -8.0F, -1.0F, 8, 8, 6, 0.0F);
        this.Head.addChild(this.Nose);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.LeftEar);
    }

}
