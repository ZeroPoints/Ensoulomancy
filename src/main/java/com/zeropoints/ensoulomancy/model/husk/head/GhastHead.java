package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GhastHead extends HuskHeadBase<GhastHead> {
	
	public GhastHead() {
		this(new Vec3d(0,0,0));
	}
	
    public GhastHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -8, -12, -8, 16, 16, 16, -4);
    }
    
}
