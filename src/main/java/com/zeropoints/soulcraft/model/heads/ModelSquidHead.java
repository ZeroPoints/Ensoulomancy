package com.zeropoints.soulcraft.model.heads;

import net.minecraft.client.model.ModelRenderer;

import com.zeropoints.soulcraft.model.ModelHeadBase;

public class ModelSquidHead extends ModelHeadBase {

    public ModelSquidHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-6.0F, -14.0F, -6.0F, 12, 16, 12, -2.0F);
    }

}
