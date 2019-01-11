package com.zeropoints.ensoulomancy.model.husk.legs;

import com.zeropoints.ensoulomancy.model.husk.utility.BatWings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CowLegs extends QuadrapedLegs<CowLegs> {
	
	public CowLegs() {
		this(new Vec3d(0,0,0));
	}
	
    public CowLegs(Vec3d offset) {
    	super(12, 0, offset);
    	
        --this.leg1.rotationPointX;
        ++this.leg2.rotationPointX;
        --this.leg3.rotationPointX;
        ++this.leg4.rotationPointX;
        --this.leg3.rotationPointZ;
        --this.leg4.rotationPointZ;
    }
	
}