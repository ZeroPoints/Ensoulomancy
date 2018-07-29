package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCowHead extends ModelHeadBase {
    public ModelRenderer RightHorn;
    public ModelRenderer LeftHorn;

    public ModelCowHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightHorn = new ModelRenderer(this, 22, 0);
        this.RightHorn.setRotationPoint(0, 0, 0);
        this.RightHorn.addBox(-5, -8, -1, 1, 3, 1, 0);
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