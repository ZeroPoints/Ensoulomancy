package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ZombiepigHead extends HuskHeadBase<ZombiepigHead> {
    
	private final ModelRenderer InnerHead;
	
	public ZombiepigHead() {
		this(new Vec3d(0,0,0));
	}
	
    public ZombiepigHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.InnerHead = new ModelRenderer(this, 0, 0);
        this.InnerHead.addBox(-4, -8, -4, 8, 8, 8, -0.5F);
        
        this.base = new ModelRenderer(this, 32, 0);
        this.base.addBox(-4, -8, -4, 8, 8, 8);
        
        this.base.addChild(this.InnerHead);
    }

}
