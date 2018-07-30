package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;
import net.minecraft.client.model.ModelRenderer;


public class ModelEnderMiteHead extends ModelHeadBase {
    public ModelRenderer Body;

    public ModelEnderMiteHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body = new ModelRenderer(this, 0, 5);
        this.Body.setRotationPoint(0, 0, 0);
        this.Body.addBox(-1, -4, 0, 6, 4, 5, 0);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(0, -3, -1, 4, 3, 2, 0);
        this.Head.addChild(this.Body);
    }

}
