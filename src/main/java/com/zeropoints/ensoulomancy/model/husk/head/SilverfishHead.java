package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SilverfishHead extends HuskHeadBase<SilverfishHead> {
	
    private final ModelRenderer Dots;
    private final ModelRenderer Body;

    public SilverfishHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public SilverfishHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Dots = new ModelRenderer(this, 20, 18);
        this.addBox(this.Dots, offset, -3, -5, 0.5F, 6, 5, 3);
        
        this.Body = new ModelRenderer(this, 0, 4);
        this.addBox(this.Body, offset, -2, -3, -1, 4, 3, 2);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -1.5F, -2, -3, 3, 2, 2);
        
        this.base.addChild(this.Dots);
        this.base.addChild(this.Body);
    }

}
