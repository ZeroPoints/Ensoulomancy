package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BatHead extends HuskHeadBase<BatHead> {
	
    private final ModelRenderer RightEar;
    private final ModelRenderer LeftEar;

    public BatHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public BatHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.LeftEar = new ModelRenderer(this, 24, 0);
        this.LeftEar.mirror = true;
        this.addBox(this.LeftEar, offset, 1, -9, -2, 3, 4, 1);
        
        this.RightEar = new ModelRenderer(this, 24, 0);
        this.addBox(this.RightEar, offset, -4, -9, -2, 3, 4, 1);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -3, -6, -3, 6, 6, 6, 0);
        
        this.base.addChild(this.LeftEar);
        this.base.addChild(this.RightEar);
    }

}
