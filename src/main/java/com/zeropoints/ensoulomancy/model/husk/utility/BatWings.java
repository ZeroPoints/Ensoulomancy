package com.zeropoints.ensoulomancy.model.husk.utility;

import com.zeropoints.ensoulomancy.model.husk.HuskBase;
import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase;
import com.zeropoints.ensoulomancy.model.husk.HuskUtilityBase;
import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase.BodyType;
import com.zeropoints.ensoulomancy.model.husk.head.SquidHead;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@SideOnly(Side.CLIENT)
public class BatWings extends HuskUtilityBase<BatWings> {

    public BatWings Get(Vec3d offset) {
		return this.INSTANCE.getOrDefault(offset.toString(), new BatWings(offset));
	}
	
    private final ModelRenderer batRightWing;
    private final ModelRenderer batLeftWing;
    private final ModelRenderer batOuterRightWing;
    private final ModelRenderer batOuterLeftWing;
    
	public BatWings() {
		this(new Vec3d(0,0,0));
	}
	
    public BatWings(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.batRightWing = new ModelRenderer(this, 42, 0);
        this.addBox(this.batRightWing, offset, -12, 1, 1.5F, 10, 16, 1);
        
        this.batOuterRightWing = new ModelRenderer(this, 24, 16);
        this.batOuterRightWing.setRotationPoint(-12, 1, 1.5F);
        this.addBox(this.batOuterRightWing, offset, -8, 1, 0, 8, 12, 1);
        
        this.batLeftWing = new ModelRenderer(this, 42, 0);
        this.batLeftWing.mirror = true;
        this.addBox(this.batLeftWing, offset, 2, 1, 1.5F, 10, 16, 1);
        
        this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
        this.batOuterLeftWing.mirror = true;
        this.batOuterLeftWing.setRotationPoint(12, 1, 1.5F);
        this.addBox(this.batOuterLeftWing, offset, 0, 1, 0, 8, 12, 1);
        
        this.base = new ModelRenderer(this, 0, 0);
        
        this.batRightWing.addChild(this.batOuterRightWing);
        this.batLeftWing.addChild(this.batOuterLeftWing);
        this.base.addChild(this.batRightWing);
        this.base.addChild(this.batLeftWing);
        
        this.facing = EnumFacing.UP; // This isn't always the case, bipeds will have wings on the back face (South)
        
        this.INSTANCE.put(offset.toString(), this);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.batRightWing.setRotationPoint(0, 0, 0);
        this.batLeftWing.setRotationPoint(0, 0, 0);
        
        this.batRightWing.rotateAngleY = MathHelper.cos(ageInTicks * 1.3F) * (float)Math.PI * 0.25F;
        this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
        
        this.batOuterRightWing.rotateAngleY = this.batRightWing.rotateAngleY * 0.5F;
        this.batOuterLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY * 0.5F;
    }
    
    @Override
    public Vec3d GetPosFromBodyPart(HuskBase body) {
		if (body instanceof HuskBodyBase && ((HuskBodyBase)body).bodyType == BodyType.BIPED) {
			return new Vec3d(0, body.baseHeight * 0.7F, body.baseDepth / 2);
		}
		return new Vec3d(0, body.baseHeight / 2, body.baseDepth * 0.3F);
	}
    
    @Override
	public HuskPart DeserializeFromClass(String data, HuskModelHelper helper) {
		return new HuskPart(this, helper);
	}

}