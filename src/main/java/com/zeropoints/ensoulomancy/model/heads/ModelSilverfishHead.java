package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelSilverfishHead extends ModelHeadBase {
    public ModelRenderer Dots;
    public ModelRenderer Body;

    public ModelSilverfishHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Dots = new ModelRenderer(this, 20, 18);
        this.Dots.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Dots.addBox(-3.0F, -5.0F, 0.5F, 6, 5, 3, 0.0F);
        this.Body = new ModelRenderer(this, 0, 4);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-2.0F, -3.0F, -1.0F, 4, 3, 2, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-1.5F, -2.0F, -3.0F, 3, 2, 2, 0.0F);
        this.Head.addChild(this.Dots);
        this.Head.addChild(this.Body);
    }

}
