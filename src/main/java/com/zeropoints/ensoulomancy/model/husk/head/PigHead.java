package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PigHead extends HuskHeadBase<PigHead> {
	
    private final ModelRenderer Nose;

    public PigHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public PigHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Nose = new ModelRenderer(this, 16, 16);
        this.addBox(this.Nose, offset, -2, -4, -5, 4, 3, 1);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -4, -8, -4, 8, 8, 8);
        
        this.base.addChild(this.Nose);
    }

}
