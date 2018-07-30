package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;


public class ModelMagmaCubeHead extends ModelHeadBase {
	public ModelRenderer Slice_1;
    public ModelRenderer Slice_2;
    public ModelRenderer Slice_3;

    public ModelMagmaCubeHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Slice_1 = new ModelRenderer(this, 0, 0);
        this.Slice_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Slice_1.addBox(-4.0F, -8.0F, -4.0F, 8, 2, 8, 0.0F);
        this.Slice_2 = new ModelRenderer(this, 24, 10);
        this.Slice_2.setRotationPoint(0.0F, -0.0F, 0.0F);
        this.Slice_2.addBox(-4.0F, -6.0F, -4.0F, 8, 1, 8, 0.0F);
        this.Slice_3 = new ModelRenderer(this, 24, 19);
        this.Slice_3.setRotationPoint(0.0F, -0.0F, 0.0F);
        this.Slice_3.addBox(-4.0F, -5.0F, -4.0F, 8, 1, 8, 0.0F);
        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -4.0F, -4.0F, 8, 4, 8, 0.0F);
        this.Head.addChild(this.Slice_3);
        this.Head.addChild(this.Slice_1);
        this.Head.addChild(this.Slice_2);
    }

}
