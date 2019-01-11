package com.zeropoints.ensoulomancy.model.husk.utility;

import com.zeropoints.ensoulomancy.model.husk.HuskBase;
import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase;
import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase.BodyType;
import com.zeropoints.ensoulomancy.model.husk.head.SquidHead;
import com.zeropoints.ensoulomancy.model.husk.HuskUtilityBase;
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

@SideOnly(Side.CLIENT)
public class ChickenWings extends HuskUtilityBase<ChickenWings> {
	
	private final ModelRenderer rightWing;
	private final ModelRenderer leftWing;
	
	public ChickenWings() {
		this(new Vec3d(0,0,0));
	}
	
    public ChickenWings(Vec3d offset) {
        this.rightWing = new ModelRenderer(this, 24, 13);
        this.rightWing.setRotationPoint(-4, 0, 0);
        this.addBox(this.rightWing, offset, 0, 0, -3, 1, 4, 6);
        
        this.leftWing = new ModelRenderer(this, 24, 13);
        this.leftWing.setRotationPoint(4, 0, 0);
        this.addBox(this.leftWing, offset, -1, 0, -3, 1, 4, 6);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0, 0, 0);
        this.addBaseBox(this.base, offset, 0, 0, 0, 0, 0, 0);
        
        this.base.addChild(this.rightWing);
        this.base.addChild(this.leftWing);
        
        this.facing = EnumFacing.UP; // This isn't always the case, bipeds will have wings on the back face (South)
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	// Chickens have function to specifically handle chicken flapping. 
    	// I just checked if the entity is currently grounded
    	if (entityIn.onGround) {
    		this.rightWing.rotateAngleZ = 0;
    		this.leftWing.rotateAngleZ = 0;
    	}
    	else {
	    	float ang = (float)Math.cos(ageInTicks * 1.6) + (float)Math.PI / 2F;
	    	this.rightWing.rotateAngleZ = ang;
	        this.leftWing.rotateAngleZ = -ang;
    	}
    }
    
    @Override
	public Vec3d GetPosFromBodyPart(HuskBase body) {
		if (body instanceof HuskBodyBase && ((HuskBodyBase)body).bodyType == BodyType.BIPED) {
			return new Vec3d(0, body.baseHeight * 0.75F, body.baseDepth / 2F);
		}
		return new Vec3d(0, body.baseHeight / 2F, 0);
	}
    
    @Override
	public HuskPart DeserializeFromClass(String data, HuskModelHelper helper) {
		return new HuskPart(this, helper);
	}

}