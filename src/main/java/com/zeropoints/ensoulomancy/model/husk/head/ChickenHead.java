package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ChickenHead extends HuskHeadBase<ChickenHead> {
	
	private final ModelRenderer Beak;
    private final ModelRenderer Red;

    public ChickenHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public ChickenHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Red = new ModelRenderer(this, 14, 4);
        this.addBox(this.Red, offset, -1, -2, -3, 2, 2, 2);
        
        this.Beak = new ModelRenderer(this, 14, 0);
        this.addBox(this.Beak, offset, -2, -4, -4, 4, 2, 2);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -2, -6, -2, 4, 6, 3);
        
        this.base.addChild(this.Red);
        this.base.addChild(this.Beak);
    }
}