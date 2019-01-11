package com.zeropoints.ensoulomancy.model.husk.body;

import com.zeropoints.ensoulomancy.model.husk.HuskBase;
import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase;
import com.zeropoints.ensoulomancy.model.husk.head.SquidHead;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ChickenBody extends HuskBodyBase<ChickenBody> {
	
	public ChickenBody() {
		this(new Vec3d(0,0,0));
	}
	
    public ChickenBody(Vec3d offset) {
        this.base = new ModelRenderer(this, 0, 9);
        this.addBaseBox(this.base, offset, -3, -4, -3, 6, 8, 6, 0);
        
        // Set common attachment points. e.g. head. This needs to be (top, center, front)
        this.headPos = new Vec3d(0, 1, 4);
        this.bodyType = BodyType.BIRD;
    }
 
    // Actions like limb swing, wing flapping etc. should be set in the setRotationAngles() func within each own body part model
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.base.rotateAngleX = ((float)Math.PI / 2F);
    }
    
}