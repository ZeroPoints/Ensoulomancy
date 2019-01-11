package com.zeropoints.ensoulomancy.model.husk.body;

import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase;
import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase.BodyType;
import com.zeropoints.ensoulomancy.model.husk.head.SquidHead;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlazeBody extends HuskBodyBase<BlazeBody> {
	
    /** The sticks that fly around the Blaze. */
    private final ModelRenderer[] blazeSticks = new ModelRenderer[12];
    
	public BlazeBody() {
		this(new Vec3d(0,0,0));
	}
	
    public BlazeBody(Vec3d offset) {
    	this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, 0, 0, 0, 0, 0, 0);
        
        for (int i = 0; i < this.blazeSticks.length; i++) {
            this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
            this.addBox(this.blazeSticks[i], offset, 0, 0, 0, 2, 8, 2);
            this.base.addChild(this.blazeSticks[i]);
        }
        
        // Set common attachment points. e.g. head. This needs to be (top, center, front)
        this.headPos = new Vec3d(0, 0, 0);
        this.headRotationPos = new Vec3d(0, 4, 0); // Center of the head TODO: This needs to be dynamic for smaller / larger heads than default
        this.baseHeight = 20; // 4 units taller than average?
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = ageInTicks * (float)Math.PI * -0.1F;

        for (int i = 0; i < 4; ++i) {
            this.blazeSticks[i].rotationPointY = -2 + MathHelper.cos(((float)(i * 2) + ageInTicks) * 0.25F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f) * 9;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f) * 9;
            ++f;
        }

        f = ((float)Math.PI / 4F) + ageInTicks * (float)Math.PI * 0.03F;

        for (int j = 4; j < 8; ++j) {
            this.blazeSticks[j].rotationPointY = 2 + MathHelper.cos(((float)(j * 2) + ageInTicks) * 0.25F);
            this.blazeSticks[j].rotationPointX = MathHelper.cos(f) * 7;
            this.blazeSticks[j].rotationPointZ = MathHelper.sin(f) * 7;
            ++f;
        }

        f = 0.47123894F + ageInTicks * (float)Math.PI * -0.05F;

        for (int k = 8; k < 12; ++k) {
            this.blazeSticks[k].rotationPointY = 11 + MathHelper.cos(((float)k * 1.5F + ageInTicks) * 0.5F);
            this.blazeSticks[k].rotationPointX = MathHelper.cos(f) * 5;
            this.blazeSticks[k].rotationPointZ = MathHelper.sin(f) * 5;
            ++f;
        }
    }
}