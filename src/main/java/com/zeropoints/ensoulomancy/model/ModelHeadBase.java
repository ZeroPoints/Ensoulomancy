package com.zeropoints.ensoulomancy.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHeadBase extends ModelBase {
	
	public float scale = 0.0625F;
	
	public ModelRenderer Head; // This should always exist, objects should join onto it as childs
	
	protected static void setRotationAngles(ModelRenderer modelRenderer, float netHeadYaw, float headPitch) {
		modelRenderer.rotateAngleY = netHeadYaw * 0.017453292F;
		modelRenderer.rotateAngleX = headPitch * 0.017453292F;
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		setRotationAngles(this.Head, netHeadYaw, headPitch);
		this.Head.render(this.scale);
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
