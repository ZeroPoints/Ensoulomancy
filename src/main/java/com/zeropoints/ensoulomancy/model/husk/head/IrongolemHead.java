package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IrongolemHead extends HuskHeadBase<IrongolemHead> {
	
	private final ModelRenderer Nose;

    public IrongolemHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public IrongolemHead(Vec3d offset) {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Nose = new ModelRenderer(this, 24, 0);
        this.addBox(this.Nose, offset, -1, -3, -6, 2, 4, 2);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -4, -10, -4, 8, 10, 8);
        
        this.base.addChild(this.Nose);
    }

}
