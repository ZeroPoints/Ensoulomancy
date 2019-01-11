package com.zeropoints.ensoulomancy.model.husk.attack;

import com.zeropoints.ensoulomancy.model.husk.HuskAttackBase;
import com.zeropoints.ensoulomancy.model.husk.head.ZombieHead;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuardianEye extends HuskAttackBase<GuardianEye> {
	
    private final ModelRenderer guardianEye;
	
	public GuardianEye() {
		this(new Vec3d(0,0,0));
	}
	
    public GuardianEye(Vec3d offset) {
        this.guardianEye = new ModelRenderer(this, 8, 0);
        this.addBox(this.guardianEye, offset, -1, -1, -0.25F, 2, 2, 1);

        this.base = new ModelRenderer(this, 18, 20);
        this.addBaseBox(this.base, offset, -3, -1.5F, -0.05F, 6, 3, 1);
        
        this.base.addChild(this.guardianEye);
    }
 
    // Actions like limb swing, wing flapping etc. should be set in the setRotationAngles() func within each own body part model
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        
    }
    
}