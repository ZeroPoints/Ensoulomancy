package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelParrotHead extends ModelHeadBase {
    public ModelRenderer HeadTop;
    public ModelRenderer LowerBeak;
    public ModelRenderer Beak;
    public ModelRenderer Crest;

    public static String[] subTypes = new String[] {"parrot_blue", "parrot_green", "parrot_grey", "parrot_red_blue", "parrot_yellow_blue"};
    
    public ModelParrotHead() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        
        this.HeadTop = new ModelRenderer(this, 10, 0);
        this.HeadTop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HeadTop.addBox(-1.0F, -4.0F, -3.0F, 2, 1, 4, 0.0F);
        this.Beak = new ModelRenderer(this, 16, 7);
        this.Beak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Beak.addBox(-0.5F, -3.25F, -3.0F, 1, 2, 1, 0.0F);
        this.LowerBeak = new ModelRenderer(this, 11, 7);
        this.LowerBeak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LowerBeak.addBox(-0.5F, -3.0F, -2.0F, 1, 2, 1, 0.0F);
        this.Crest = new ModelRenderer(this, 2, 18);
        this.Crest.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Crest.addBox(0.0F, -8.0F, -2.5F, 0, 5, 4, 0.0F);
        this.setRotateAngle(Crest, -0.2214822820780804F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 2, 2);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.Head.addChild(this.HeadTop);
        this.Head.addChild(this.Beak);
        this.Head.addChild(this.LowerBeak);
        this.Head.addChild(this.Crest);
    }

}
