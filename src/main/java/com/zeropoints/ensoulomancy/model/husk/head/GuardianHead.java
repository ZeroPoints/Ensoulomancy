package com.zeropoints.ensoulomancy.model.husk.head;

import java.util.HashMap;
import java.util.Map;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuardianHead extends HuskHeadBase<GuardianHead> {
	
	// Angles
	private static float[] afloat0 = new float[] {1.75F, 0.25F, 0, 0, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0, 0};
	private static float[] afloat1 = new float[] {0, 0, 0, 0, 0.25F, 1.75F, 1.25F, 0.75F, 0, 0, 0, 0};
	private static float[] afloat2 = new float[] {0, 0, 0.25F, 1.75F, 0, 0, 0, 0, 0, 0, 0.75F, 1.25F};
	
	 // Offsets
	private static float[] afloat3 = new float[] {0, 0, 1, -1, -1, 1, 1, -1, 0, 0, 1, -1};
	private static float[] afloat4 = new float[] {-1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1, 1};
	private static float[] afloat5 = new float[] {1, -1, 0, 0, -1, -1, 1, 1, 1, -1, 0, 0};
	
    private final ModelRenderer[] guardianSpines;

    // Blank overhead method for default head behaviour
    // Used for soul skulls and other bottom rotation point heads
    public GuardianHead() {
    	this(new Vec3d(0, 0, 0));
        this.scale = 0.03125F; // The head scale when placed as a soulskull.
    }
    
    public GuardianHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        // Initialize this before the spines otherwise will die
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -6, -14, -8, 12, 12, 16);
        
		// Initialize this before the spines otherwise will die
        this.addBox(this.base.setTextureOffset(0, 28), offset, -8, -14, -6, 2, 12, 12);
        this.addBox(this.base.setTextureOffset(0, 28), offset, 6, -14, -6, 2, 12, 12, true);
        this.addBox(this.base.setTextureOffset(16, 40), offset, -6, -16, -6, 12, 2, 12);
        this.addBox(this.base.setTextureOffset(16, 40), offset, -6, -2, -6, 12, 2, 12);
        
        this.guardianSpines = new ModelRenderer[12];
        for (int i = 0; i < this.guardianSpines.length; i++) {
            this.guardianSpines[i] = new ModelRenderer(this, 0, 0);
            this.addBox(this.guardianSpines[i], offset, -1, -9.5F, -1, 2, 9, 2);
            this.guardianSpines[i].rotateAngleX = (float)Math.PI * afloat0[i];
            this.guardianSpines[i].rotateAngleY = (float)Math.PI * afloat1[i];
            this.guardianSpines[i].rotateAngleZ = (float)Math.PI * afloat2[i];
            this.guardianSpines[i].setRotationPoint(0, -8, 0);
            this.base.addChild(this.guardianSpines[i]);
        }
        
    	this.isAlsoBody = true; // This head is actually a body model for a guardian soul main
    	this.baseHeight = 16; // Set this manually because the body isn't actually as tall as what we want it to be
    	this.initialYOffset = -6; // Move the item to match the 0,0,0 rotation point when this is the body.
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	
        float f = ageInTicks - (float)entityIn.ticksExisted;
        this.base.rotateAngleY = netHeadYaw * 0.017453292F;
        this.base.rotateAngleX = headPitch * 0.017453292F;
        
        // Spike animation
        for (int i = 0; i < this.guardianSpines.length; i++) {;
        	float f2 = entityIn.isInWater() 
    			? (entityIn.onGround ? 1.3F : 1.0F) + MathHelper.sin(ageInTicks * 1.5F + (float)i) * 0.01F - 0.44F
				: 0.8F + MathHelper.sin(ageInTicks) * 0.15F;
			f2 += this.swingProgress;
            this.guardianSpines[i].rotationPointX = afloat3[i] * f2 * 10.5F;
            this.guardianSpines[i].rotationPointY = afloat4[i] * f2 * 10.5F;
            this.guardianSpines[i].rotationPointZ = afloat5[i] * f2 * 10.5F;
        }

        /*
        // Setting the direction of the 'eye' to its' attack target
        this.guardianEye.rotationPointZ = -8.25F;
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();

        if (entityguardian.hasTargetedEntity()) {
            entity = entityguardian.getTargetedEntity();
        }

        if (entity != null) {
            Vec3d vec3d = entity.getPositionEyes(0);
            Vec3d vec3d1 = entityIn.getPositionEyes(0);
            double d0 = vec3d.y - vec3d1.y;

            if (d0 > 0.0D) {
                this.guardianEye.rotationPointY = 0;
            }
            else {
                this.guardianEye.rotationPointY = 1.0F;
            }

            Vec3d vec3d2 = entityIn.getLook(0);
            vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);
            Vec3d vec3d3 = (new Vec3d(vec3d1.x - vec3d.x, 0.0D, vec3d1.z - vec3d.z)).normalize().rotateYaw(((float)Math.PI / 2F));
            double d1 = vec3d2.dotProduct(vec3d3);
            this.guardianEye.rotationPointX = MathHelper.sqrt((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1);
        }

        this.guardianEye.showModel = true;
        */
    }

}
