package com.zeropoints.ensoulomancy.model.husk;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHusk extends ModelBase {
	
	public float scale = 0.0625F;
	
	// These should always exist. Customisable based on the mixup of souls in the husk
	public ModelRenderer Head; 
	public ModelRenderer Body;
	
	protected static void setRotationAngles(ModelRenderer modelRenderer, float netHeadYaw, float headPitch) {
		modelRenderer.rotateAngleY = netHeadYaw * 0.017453292F;
		modelRenderer.rotateAngleX = headPitch * 0.017453292F;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		/*if (entity instanceof EntityHusk) {
			EntityHusk husk = (EntityHusk)entity;
			// Other stuff. 
		}*/
		
		//setRotationAngles(this.Head, netHeadYaw, headPitch);
		//this.Head.render(this.scale);
		//this.Body.render(this.scale);
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
