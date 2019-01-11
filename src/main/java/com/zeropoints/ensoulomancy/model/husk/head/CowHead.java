package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CowHead extends HuskHeadBase<CowHead> {
	
	private final ModelRenderer RightHorn;
    private final ModelRenderer LeftHorn;

    public CowHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public CowHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.RightHorn = new ModelRenderer(this, 22, 0);
        this.addBox(this.RightHorn, offset, -5, -9, -1, 1, 3, 1);
        
        this.LeftHorn = new ModelRenderer(this, 22, 0);
        this.addBox(this.LeftHorn, offset, 4, -9, -1, 1, 3, 1);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -4, -8, -3, 8, 8, 6);
        
        this.base.addChild(this.RightHorn);
        this.base.addChild(this.LeftHorn);
    }
    
}