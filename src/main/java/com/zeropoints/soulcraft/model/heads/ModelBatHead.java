package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelBatHead extends ModelHeadBase {
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;

    public ModelBatHead() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.LeftEar = new ModelRenderer(this, 24, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(0, 0, 0);
        this.LeftEar.addBox(1, -9, -2, 3, 4, 1, 0);
        this.RightEar = new ModelRenderer(this, 24, 0);
        this.RightEar.setRotationPoint(0, 0, 0);
        this.RightEar.addBox(-4, -9, -2, 3, 4, 1, 0);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-3, -6, -3, 6, 6, 6, 0);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.RightEar);
    }

}
