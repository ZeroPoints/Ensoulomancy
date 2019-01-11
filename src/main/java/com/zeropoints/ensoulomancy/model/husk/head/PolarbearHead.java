package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PolarbearHead extends HuskHeadBase<PolarbearHead> {
	
    private final ModelRenderer Nose;
    private final ModelRenderer RightEar;
    private final ModelRenderer LeftEar;

    public PolarbearHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public PolarbearHead(Vec3d offset) {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.RightEar = new ModelRenderer(this, 26, 0);
        this.addBox(this.RightEar, offset, -4.5F, -8, -1, 2, 2, 1, 0);
        
        this.Nose = new ModelRenderer(this, 0, 44);
        this.addBox(this.Nose, offset, -2.5F, -3, -6.5F, 5, 3, 3, 0);
        
        this.LeftEar = new ModelRenderer(this, 26, 0);
        this.LeftEar.mirror = true;
        this.addBox(this.LeftEar, offset, 2.5F, -8, -1, 2, 2, 1, 0);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -3.5F, -7, -3.5F, 7, 7, 7, 0);
        
        this.base.addChild(this.RightEar);
        this.base.addChild(this.Nose);
        this.base.addChild(this.LeftEar);
    }

}
