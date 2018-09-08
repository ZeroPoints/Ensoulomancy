package com.zeropoints.ensoulomancy.model.heads;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.client.model.ModelRenderer;

//@SideOnly(Side.CLIENT)
public class ModelGuardianHead extends ModelHeadBase {
    public ModelRenderer Nook_TS;
    public ModelRenderer Nook_TN;
    public ModelRenderer Hook_TW;
    public ModelRenderer Hook_TE;
    public ModelRenderer Hook_NE;
    public ModelRenderer Hook_NW;
    public ModelRenderer Hook_SW;
    public ModelRenderer Hook_SE;
    public ModelRenderer Hook_BS;
    public ModelRenderer Hook_BN;
    public ModelRenderer Hook_BW;
    public ModelRenderer Hoob_BE;
    public ModelRenderer Eye;
    public ModelRenderer Right;
    public ModelRenderer Left;
    public ModelRenderer Top;
    public ModelRenderer Bottom;

    public ModelGuardianHead() {
    	this.scale = 0.03125F;
    	
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.Eye = new ModelRenderer(this, 8, 0);
        this.Eye.setRotationPoint(0, 0, 0);
        this.Eye.addBox(-1, -8, -8.25F, 2, 2, 1, -0.0F);
        
        this.Right = new ModelRenderer(this, 0, 28);
        this.Right.setRotationPoint(0, 0, 0);
        this.Right.addBox(-8, -14, -6, 2, 12, 12, -0.0F);
        
        this.Left = new ModelRenderer(this, 0, 28);
        this.Left.mirror = true;
        this.Left.setRotationPoint(0, 0, 0);
        this.Left.addBox(6, -14, -6, 2, 12, 12, -0.0F);
        
        this.Top = new ModelRenderer(this, 16, 40);
        this.Top.setRotationPoint(0, 0, 0);
        this.Top.addBox(-6, -16, -6, 12, 2, 12, -0.0F);
        
        this.Bottom = new ModelRenderer(this, 16, 40);
        this.Bottom.setRotationPoint(0, 0, 0);
        this.Bottom.addBox(-6, -2, -6, 12, 2, 12, -0.0F);
        
        this.Hook_TW = new ModelRenderer(this, 0, 0);
        this.Hook_TW.setRotationPoint(0, 0, 0);
        this.Hook_TW.addBox(-6.6F, -15.3F, -1, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_TW, 0, 0, 0.7853981633974483F);
        
        this.Nook_TS = new ModelRenderer(this, 0, 0);
        this.Nook_TS.setRotationPoint(0, 0, 0);
        this.Nook_TS.addBox(-1, -15.3F, -6.6F, 2, 9, 2, -0.0F);
        this.setRotateAngle(Nook_TS, 5.497787143782138F, 0, 0);
        
        this.Hook_TE = new ModelRenderer(this, 0, 0);
        this.Hook_TE.setRotationPoint(0, 0, 0);
        this.Hook_TE.addBox(4.6F, -15.3F, -1, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_TE, 0, 0, 5.497787143782138F);
        
        this.Hook_BS = new ModelRenderer(this, 0, 0);
        this.Hook_BS.setRotationPoint(0, 0, 0);
        this.Hook_BS.addBox(-1, -4, -6.7F, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_BS, 3.9269908169872414F, 0, 0);
        
        this.Hook_SE = new ModelRenderer(this, 0, 0);
        this.Hook_SE.setRotationPoint(0, 0, 0);
        this.Hook_SE.addBox(-1, -9.5F, 7, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_SE, 1.5707963267948966F, 2.356194490192345F, 0);
        
        this.Hook_BN = new ModelRenderer(this, 0, 0);
        this.Hook_BN.setRotationPoint(0, 0, 0);
        this.Hook_BN.addBox(-1, -4, 4.7F, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_BN, 2.356194490192345F, 0, 0);
        
        this.Hook_BW = new ModelRenderer(this, 0, 0);
        this.Hook_BW.setRotationPoint(0, 0, 0);
        this.Hook_BW.addBox(-6.7F, -4, -1, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_BW, 0, 0, 2.356194490192345F);
        
        this.Hoob_BE = new ModelRenderer(this, 0, 0);
        this.Hoob_BE.setRotationPoint(0, 0, 0);
        this.Hoob_BE.addBox(4.7F, -4, -1, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hoob_BE, 0, 0, 3.9269908169872414F);
        
        this.Hook_NE = new ModelRenderer(this, 0, 0);
        this.Hook_NE.setRotationPoint(0, 0, 0);
        this.Hook_NE.addBox(-1, -9.5F, 6, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_NE, 1.5707963267948966F, 0.7853981633974483F, 0);
        
        this.Hook_SW = new ModelRenderer(this, 0, 0);
        this.Hook_SW.setRotationPoint(0, 0, 0);
        this.Hook_SW.addBox(-1, -9.5F, 7, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_SW, 1.5707963267948966F, 3.9269908169872414F, 0);
        
        this.Nook_TN = new ModelRenderer(this, 0, 0);
        this.Nook_TN.setRotationPoint(0, 0, 0);
        this.Nook_TN.addBox(-1, -15.3F, 4.6F, 2, 9, 2, -0.0F);
        this.setRotateAngle(Nook_TN, 0.7853981633974483F, 0, 0);
        
        this.Hook_NW = new ModelRenderer(this, 0, 0);
        this.Hook_NW.setRotationPoint(0, 0, 0);
        this.Hook_NW.addBox(-1, -9.5F, 6, 2, 9, 2, -0.0F);
        this.setRotateAngle(Hook_NW, 1.5707963267948966F, 5.497787143782138F, 0);
        
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0, 0, 0);
        this.Head.addBox(-6, -14, -8, 12, 12, 16, -0.0F);
        
        this.Head.addChild(this.Eye);
        this.Head.addChild(this.Right);
        this.Head.addChild(this.Left);
        this.Head.addChild(this.Top);
        this.Head.addChild(this.Bottom);
        this.Head.addChild(this.Hook_TW);
        this.Head.addChild(this.Nook_TS);
        this.Head.addChild(this.Hook_TE);
        this.Head.addChild(this.Hook_BS);
        this.Head.addChild(this.Hook_SE);
        this.Head.addChild(this.Hook_BN);
        this.Head.addChild(this.Hook_BW);
        this.Head.addChild(this.Hoob_BE);
        this.Head.addChild(this.Hook_NE);
        this.Head.addChild(this.Hook_SW);
        this.Head.addChild(this.Nook_TN);
        this.Head.addChild(this.Hook_NW);
    }

}
