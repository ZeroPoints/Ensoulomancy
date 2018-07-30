package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelVillagerHead extends ModelHeadBase {
    public ModelRenderer Nose;

    public ModelVillagerHead() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Nose = new ModelRenderer(this, 24, 0);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.Head.addChild(this.Nose);
    }

}
