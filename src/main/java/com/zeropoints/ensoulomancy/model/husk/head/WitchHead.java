package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WitchHead extends HuskHeadBase<WitchHead> {
    
    private final ModelRenderer Nose;
    private final ModelRenderer HatBrim;
    private final ModelRenderer Wart;
    private final ModelRenderer HatMid;
    private final ModelRenderer HatUpper;
    private final ModelRenderer HatStalk;
    
	public WitchHead() {
		this(new Vec3d(0,0,0));
	}
	
    public WitchHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 128;
        
        this.Wart = new ModelRenderer(this, 0, 0);
        this.Wart.mirror = true;
        this.Wart.setRotationPoint(0, -2, 0);
        this.addBox(this.Wart, offset, 0, 3, -6.75F, 1, 1, 1);
        
        this.HatStalk = new ModelRenderer(this, 0, 95);
        this.HatStalk.setRotationPoint(1.75F, -2, 2);
        this.addBox(this.HatStalk, offset, 0, 0, 0, 1, 2, 1);
        this.setRotateAngle(HatStalk, -0.20943951023931953F, 0, 0.10471975511965977F);
        
        this.HatUpper = new ModelRenderer(this, 0, 87);
        this.HatUpper.setRotationPoint(1.75F, -4, 2);
        this.addBox(this.HatUpper, offset, 0, 0, 0, 4, 4, 4);
        this.setRotateAngle(HatUpper, -0.10471975511965977F, 0, 0.05235987755982988F);
        
        this.Nose = new ModelRenderer(this, 24, 0);
        this.Nose.addChild(this.Wart);
        this.Nose.setRotationPoint(0, -2, 0);
        this.addBox(this.Nose, offset, -1, -1, -6, 2, 4, 2);
        this.setRotateAngle(Nose, 0, 0, 0.04363323129985824F);
        
        this.HatMid = new ModelRenderer(this, 0, 76);
        this.HatMid.setRotationPoint(1.75F, -4, 2);
        this.addBox(this.HatMid, offset, 0, 0, 0, 7, 4, 7);
        this.setRotateAngle(HatMid, -0.05235987755982988F, 0, 0.02617993877991494F);
        
        this.HatBrim = new ModelRenderer(this, 0, 64);
        this.HatBrim.setRotationPoint(-5, -10.03F, -5);
        this.addBox(this.HatBrim, offset, 0, 0, 0, 10, 2, 10);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0, 0, 0);
        this.addBox(this.base, offset, -4, -10, -4, 8, 10, 8);
        
        this.HatUpper.addChild(this.HatStalk);
        this.HatMid.addChild(this.HatUpper);
        this.base.addChild(this.Nose);
        this.HatBrim.addChild(this.HatMid);
        this.base.addChild(this.HatBrim);
    }
    
}
