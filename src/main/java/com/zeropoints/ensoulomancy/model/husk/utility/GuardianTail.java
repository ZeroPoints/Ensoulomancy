package com.zeropoints.ensoulomancy.model.husk.utility;

import com.zeropoints.ensoulomancy.model.husk.HuskBase;
import com.zeropoints.ensoulomancy.model.husk.HuskUtilityBase;
import com.zeropoints.ensoulomancy.model.husk.head.SquidHead;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuardianTail extends HuskUtilityBase<GuardianTail> {
	
	private final ModelRenderer tail1;
	private final ModelRenderer tail2;
	
	public GuardianTail() {
		this(new Vec3d(0,0,0));
	}
	
    public GuardianTail(Vec3d offset) {
    	this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.tail1 = new ModelRenderer(this, 0, 54);
        this.tail1.setRotationPoint(0, 0, 7.5F);
        this.addBox(this.tail1, offset,-1.5F, -1.5F, 0, 3, 3, 7);
        
        this.tail2 = new ModelRenderer(this);
        this.tail2.setRotationPoint(0, 0, 6);
        this.addBox(this.tail2.setTextureOffset(41, 32), offset, -1, -1, 0, 2, 2, 6);
        this.addBox(this.tail2.setTextureOffset(25, 19), offset, 0, -4.5F, 3, 1, 9, 9);
        
        this.base = new ModelRenderer(this, 40, 0);
        this.addBaseBox(this.base, offset, -2, -2, 0, 4, 4, 8);
        
        this.tail1.addChild(this.tail2);
        this.base.addChild(this.tail1);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	// Behaviour: animation ramps up faster they go, minimum tail speed. Frantic out of water
    	float f2 = entityIn.isInWater()
    			? MathHelper.sin(ageInTicks / 10 + limbSwing) * 3 
				: MathHelper.sin(ageInTicks * 2) * 3;
    	this.base.rotateAngleY = f2 * 0.05F;
        this.tail1.rotateAngleY = f2 * 0.1F;
        this.tail2.rotateAngleY = f2 * 0.15F;
    }

	@Override
	public Vec3d GetPosFromBodyPart(HuskBase body) {
		return new Vec3d(0, 0, body.baseDepth / 2);
	}
}