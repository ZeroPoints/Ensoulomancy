package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SpiderHead extends HuskHeadBase<SpiderHead> {
	
	public SpiderHead() {
		this(new Vec3d(0,0,0));
	}
	
    public SpiderHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.base = new ModelRenderer(this, 32, 4);
        this.addBaseBox(this.base, offset, -4, -8, -4, 8, 8, 8);
    }

}
