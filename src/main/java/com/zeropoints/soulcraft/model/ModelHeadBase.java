package com.zeropoints.soulcraft.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHeadBase extends ModelBase {
	
	public ModelRenderer Head; // This should always exist, objects should join onto it as childs
	
	protected void setHeadRotationAngles(float netHeadYaw, float headPitch) {
		this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.Head.rotateAngleX = headPitch * 0.017453292F;
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		setHeadRotationAngles(netHeadYaw, headPitch);
		this.Head.render(scale);
	}
	
}
