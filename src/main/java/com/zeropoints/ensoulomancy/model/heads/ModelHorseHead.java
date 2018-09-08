package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelHorseHead extends ModelHeadBase {
	
    public ModelRenderer Nose;
    public ModelRenderer Jaw;
    public ModelRenderer Neck;
    public ModelRenderer Mane;
    public ModelRenderer RightEar;
    public ModelRenderer RightEarLong;
    public ModelRenderer LeftEar;
    public ModelRenderer LeftEarLong;

    // There are multiple types attached to the entityName
    public static String[] subTypes = new String[] {"horse_white", "horse_creamy", "horse_chestnut", "horse_brown", "horse_black", "horse_gray", "horse_darkbrown"};
    
    public ModelHorseHead() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.RightEarLong = new ModelRenderer(this, 0, 12);
        this.RightEarLong.setRotationPoint(0, 0, 0);
        this.RightEarLong.addBox(0, -16.0F, 4.0F, 2, 7, 1, 0);
        this.setRotateAngle(RightEarLong, 0, 0, -0.2617993877991494F);
        this.LeftEar = new ModelRenderer(this, 0, 0);
        this.LeftEar.setRotationPoint(0, 0, 0);
        this.LeftEar.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1, 0);
        this.Jaw = new ModelRenderer(this, 24, 27);
        this.Jaw.setRotationPoint(0, 0, 0);
        this.Jaw.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5, 0);
        this.Neck = new ModelRenderer(this, 0, 12);
        this.Neck.setRotationPoint(0, 0, 0);
        this.Neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8, 0);
        this.Nose = new ModelRenderer(this, 24, 18);
        this.Nose.setRotationPoint(0, 0.02F, 0.02F);
        this.Nose.addBox(-2.0F, -10, -7.0F, 4, 3, 6, 0);
        this.Mane = new ModelRenderer(this, 58, 0);
        this.Mane.setRotationPoint(0, 0, 0);
        this.Mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4, 0);
        this.RightEar = new ModelRenderer(this, 0, 0);
        this.RightEar.setRotationPoint(0, 0, 0);
        this.RightEar.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1, 0);
        this.LeftEarLong = new ModelRenderer(this, 0, 12);
        this.LeftEarLong.setRotationPoint(0, 0, 0);
        this.LeftEarLong.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1, 0);
        this.setRotateAngle(LeftEarLong, 0, 0, 0.2617993877991494F);
        
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-2.5F, -10, -1.5F, 5, 5, 7, 0);
        this.setRotateAngle(Head, 0.5235987755982988F, 0, 0);
        
        this.Head.addChild(this.RightEarLong);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.Jaw);
        this.Head.addChild(this.Neck);
        this.Head.addChild(this.Nose);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.Mane);
        this.Head.addChild(this.LeftEarLong);
    }
}
