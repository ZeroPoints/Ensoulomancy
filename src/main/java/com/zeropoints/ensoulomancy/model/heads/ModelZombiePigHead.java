package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelZombiePigHead extends ModelHeadBase {
    public ModelRenderer InnerHead;

    public ModelZombiePigHead() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.InnerHead = new ModelRenderer(this, 0, 0);
        this.InnerHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.InnerHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
        this.Head = new ModelRenderer(this, 32, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.Head.addChild(this.InnerHead);
    }

}
