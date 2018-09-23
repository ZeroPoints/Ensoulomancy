package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelEnderMiteHead extends ModelHeadBase {
    public ModelRenderer Body;

    public ModelEnderMiteHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-2.0F, -3.0F, -3.0F, 4, 3, 2, 0.0F);
        this.Body = new ModelRenderer(this, 0, 5);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-3.0F, -4.0F, -1.0F, 6, 4, 5, 0.0F);
        this.Head.addChild(this.Body);
    }

}
