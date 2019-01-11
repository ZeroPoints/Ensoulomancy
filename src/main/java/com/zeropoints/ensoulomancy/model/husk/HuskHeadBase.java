package com.zeropoints.ensoulomancy.model.husk;

import com.zeropoints.ensoulomancy.model.husk.HuskBase.PartType;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * The shared class for all head models. 
 * @author ChickenMobile
 */
public abstract class HuskHeadBase<T> extends HuskBase<T> {
	
	public float scale = 0.0625F; // 1/16
 
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.base.rotateAngleY = netHeadYaw * 0.017453292F;
		this.base.rotateAngleX = headPitch * 0.017453292F;
		this.preRender();
		this.base.render(scale);
		this.postRender();
	}

	@Override
	public HuskPart DeserializeFromClass(String data, HuskModelHelper helper) {
		return new HuskPart(this, helper);
	}
	
	@Override
	public PartType GetPartType() {
		return PartType.HEAD;
	}
	
}
