package com.zeropoints.ensoulomancy.model.husk.legs;

import com.zeropoints.ensoulomancy.model.husk.head.SquidHead;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ChickenLegs extends BipedLegs<ChickenLegs> { 
	
	public ChickenLegs() {
		this(new Vec3d(0,0,0));
	}
	
    public ChickenLegs(Vec3d offset) {
    	this.leg1 = new ModelRenderer(this, 26, 0);
        this.leg1.addBox(-1.5F, 0, -3, 3, 5, 3);
        this.leg1.setRotationPoint(1.5F, 0, 1);
        
    	this.leg2 = new ModelRenderer(this, 26, 0);
        this.leg2.addBox(-1.5F, 0, -3, 3, 5, 3);
        this.leg2.setRotationPoint(-1.5F, 0, 1);
        
        this.base.addChild(this.leg1);
        this.base.addChild(this.leg2);
        
        this.baseHeight = 5; // Total height from 0,0,0
    }
    
}