package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelEndermanHead extends ModelHeadBase {
    public ModelRenderer Jaw;

    public ModelEndermanHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.Jaw.setRotationPoint(0, 0, 0);
        this.Jaw.addBox(-4, -8, -4, 8, 8, 8, -0.5F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-4, -8, -4, 8, 8, 8, 0.0F);
        this.Head.addChild(this.Jaw);
    }
    
}
