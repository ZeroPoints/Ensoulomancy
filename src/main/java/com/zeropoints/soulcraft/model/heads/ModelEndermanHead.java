package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

/**
 * ModelEnderman - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
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
