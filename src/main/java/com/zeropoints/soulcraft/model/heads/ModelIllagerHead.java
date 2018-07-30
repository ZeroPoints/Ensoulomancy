package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelIllagerHead extends ModelHeadBase {
    public ModelRenderer Hair;
    public ModelRenderer Nose;

    public String[] subTypes = {""};
    
    public ModelIllagerHead() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.Hair = new ModelRenderer(this, 32, 0);
        this.Hair.setRotationPoint(0, 0, 0);
        this.Hair.addBox(-4, -10, -4, 8, 12, 8, 0.5F);
        this.Nose = new ModelRenderer(this, 24, 0);
        this.Nose.setRotationPoint(0, 0, 0);
        this.Nose.addBox(-1, -3, -6, 2, 4, 2, 0);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-4, -10, -4, 8, 10, 8, 0);
        this.Head.addChild(this.Hair);
        this.Head.addChild(this.Nose);
    }
}
