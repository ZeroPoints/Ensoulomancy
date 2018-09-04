package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelIronGolemHead extends ModelHeadBase {
    public ModelRenderer Nose;

    public ModelIronGolemHead() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Nose = new ModelRenderer(this, 24, 0);
        this.Nose.setRotationPoint(0, 0, 0);
        this.Nose.addBox(-1, -3, -6, 2, 4, 2, 0);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-4, -10, -4, 8, 10, 8, 0);
        this.Head.addChild(this.Nose);
    }

}
