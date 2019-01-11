package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HorseHead extends HuskHeadBase<HorseHead> {
	
	// There are multiple types attached to the entityName
    public static String[] subTypes = new String[] {
    		"horse_white", "horse_creamy", "horse_chestnut", "horse_brown", "horse_black", "horse_gray", "horse_darkbrown"
    		};
	
    private final ModelRenderer Nose;
    private final ModelRenderer Jaw;
    private final ModelRenderer Neck;
    private final ModelRenderer Mane;
    private final ModelRenderer RightEar;
    private final ModelRenderer RightEarLong;
    private final ModelRenderer LeftEar;
    private final ModelRenderer LeftEarLong;
    
    public HorseHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public HorseHead(Vec3d offset) {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.RightEarLong = new ModelRenderer(this, 0, 12);
        this.addBox(this.RightEarLong, offset, 0, -16, 4, 2, 7, 1);
        this.setRotateAngle(RightEarLong, 0, 0, -0.2617993877991494F);
        
        this.LeftEar = new ModelRenderer(this, 0, 0);
        this.addBox(this.LeftEar, offset, 0.45F, -12, 4, 2, 3, 1);
        
        this.Jaw = new ModelRenderer(this, 24, 27);
        this.addBox(this.Jaw, offset, -2, -7, -6.5F, 4, 2, 5);
        
        this.Neck = new ModelRenderer(this, 0, 12);
        this.addBox(this.Neck, offset, -2.05F, -9.8F, -2.0F, 4, 14, 8);
        
        this.Nose = new ModelRenderer(this, 24, 18);
        this.Nose.setRotationPoint(0, 0.02F, 0.02F);
        this.addBox(this.Nose, offset, -2, -10, -7, 4, 3, 6);
        
        this.Mane = new ModelRenderer(this, 58, 0);
        this.Mane.setRotationPoint(0, 0, 0);
        this.addBox(this.Mane, offset, -1, -11.5F, 5, 2, 16, 4);
        
        this.RightEar = new ModelRenderer(this, 0, 0);
        this.addBox(this.RightEar, offset, -2.45F, -12, 4, 2, 3, 1);
        
        this.LeftEarLong = new ModelRenderer(this, 0, 12);
        this.addBox(this.LeftEarLong, offset, -2, -16, 4, 2, 7, 1);
        this.setRotateAngle(LeftEarLong, 0, 0, 0.2617993877991494F);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -2.5F, -10, -1.5F, 5, 5, 7);
        this.setRotateAngle(this.base, 0.5235987755982988F, 0, 0);
        
        this.base.addChild(this.RightEarLong);
        this.base.addChild(this.LeftEar);
        this.base.addChild(this.Jaw);
        this.base.addChild(this.Neck);
        this.base.addChild(this.Nose);
        this.base.addChild(this.RightEar);
        this.base.addChild(this.Mane);
        this.base.addChild(this.LeftEarLong);
    }
    
}
