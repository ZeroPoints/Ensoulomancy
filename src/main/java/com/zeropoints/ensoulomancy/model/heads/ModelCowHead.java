package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelCowHead extends ModelHeadBase {
    public ModelRenderer RightHorn;
    public ModelRenderer LeftHorn;

    public ModelCowHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightHorn = new ModelRenderer(this, 22, 0);
        this.RightHorn.setRotationPoint(0, 0, 0);
        this.RightHorn.addBox(-5, -9, -1, 1, 3, 1, 0);
        this.LeftHorn = new ModelRenderer(this, 22, 0);
        this.LeftHorn.setRotationPoint(0, 0, 0);
        this.LeftHorn.addBox(4, -9, -1, 1, 3, 1, 0);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-4, -8, -3, 8, 8, 6, 0);
        this.Head.addChild(this.RightHorn);
        this.Head.addChild(this.LeftHorn);
    }
}