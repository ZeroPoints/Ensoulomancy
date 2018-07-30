package com.zeropoints.soulcraft.model.heads;

import com.zeropoints.soulcraft.model.ModelHeadBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelChickenHead extends ModelHeadBase {
    public ModelRenderer Beak;
    public ModelRenderer Red;

    public ModelChickenHead() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Red = new ModelRenderer(this, 14, 4);
        this.Red.setRotationPoint(0, 0, 0);
        this.Red.addBox(-1, -2, -3, 2, 2, 2, 0);
        this.Beak = new ModelRenderer(this, 14, 0);
        this.Beak.setRotationPoint(0, 0, 0);
        this.Beak.addBox(-2, -4, -4, 4, 2, 2, 0);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-2, -6, -2, 4, 6, 3, 0);
        this.Head.addChild(this.Red);
        this.Head.addChild(this.Beak);
    }
}