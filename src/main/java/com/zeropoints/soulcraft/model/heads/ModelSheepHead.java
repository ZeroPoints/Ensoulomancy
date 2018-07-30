package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelSheepHead extends ModelHeadBase {

    public ModelSheepHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Head = new ModelRenderer(this, 2, 2);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
    }

}
