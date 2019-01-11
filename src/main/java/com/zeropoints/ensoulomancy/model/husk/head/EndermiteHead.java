package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EndermiteHead extends HuskHeadBase<EndermiteHead> {
	
	private final ModelRenderer Body;

    public EndermiteHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public EndermiteHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Body = new ModelRenderer(this, 0, 5);
        this.addBox(this.Body, offset, -3, -4, -1, 6, 4, 5);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -2, -3, -3, 4, 3, 2);
        
        this.base.addChild(this.Body);
    }

}
