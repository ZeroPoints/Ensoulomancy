package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelSpiderHead extends ModelHeadBase {
	
    public ModelSpiderHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Head = new ModelRenderer(this, 32, 4);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
    }

}
