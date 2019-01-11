package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EndermanHead extends HuskHeadBase<EndermanHead> {
	
	private final ModelRenderer Jaw;

    public EndermanHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public EndermanHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.addBox(this.Jaw, offset, -4, -8, -4, 8, 8, 8, -0.5F);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -4, -8, -4, 8, 8, 8);
        
        this.base.addChild(this.Jaw);
    }
    
}
