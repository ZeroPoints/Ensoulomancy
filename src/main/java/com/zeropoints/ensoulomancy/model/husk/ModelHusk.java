package com.zeropoints.ensoulomancy.model.husk;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHusk extends ModelBase {
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		// Render the husk based on all parts within the entity
		if (entityIn instanceof EntityHusk) {
			EntityHusk entity = (EntityHusk)entityIn;
			TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
			
			// If this is the first time rendering: setup the entity properly before render
			if (entity.partList.size() == 0) {
				entity.setupModelRender();
			}
			
			// First loop to set height offset before rendering
			if (entity.body == null) {
				for (HuskPart part : entity.partList) {
					if (part.model instanceof HuskBodyBase || part.model.isAlsoBody) {
						entity.offsetHeight = (int)(part.model.baseHeight / 2F + part.model.initialYOffset);
						if (part.model.isAlsoBody && part.model instanceof HuskHeadBase) {
							part.rotationPos = new Vec3d(0, part.model.baseHeight / 2F, 0); // If is body - set rotation point to middle
						}
						entity.body = part;
					}
					else if (part.model instanceof HuskLegsBase) {
						entity.offsetHeight += part.model.baseHeight;
					}
					
					// Has rotation point, therefore retrieve existing or initialize new model with rotation point
					if (part.rotationPos != null) {
						part.model = (HuskBase) part.model.Get(part.model.getClass(), part.rotationPos);
						part.pos = new Vec3d(part.pos.x + part.rotationPos.x, part.pos.y + part.rotationPos.y, part.pos.z + part.rotationPos.z);
						//part.pos = new Vec3d(part.rotationPos.x, part.rotationPos.y, part.rotationPos.z);
					}
				}
				entity.offsetHeight = 24 - entity.offsetHeight;
				entity.height = entity.body.model.baseHeight * 0.0625F;
				entity.width = entity.height / 2F;
			}
			
			// Loop through again to render
			for (HuskPart part : entity.partList) {
				GlStateManager.pushMatrix();
				
				if (entity.body.model.isAlsoBody) {
					// TODO: This might not work always and seamless
					part.model.copyModelAngles(entity.body.model.base, part.model.base); 
				}
				part.model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
				textureManager.bindTexture(part.helper.texture);
				
				// TODO: Offset all models to where they belong and figure out scale
				this.setRenderOffset(part, entity.offsetHeight); // 24 is ground level
				part.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
				
				GlStateManager.popMatrix();
			}
		}
	}
	
	/*
	 * Set the rotation point relative to that of (0,0,0). Sometimes positions are dynamic
	 */
	private void setRenderOffset(HuskPart part, float offsetHeight) {
		if (part.pos != null) {
			part.model.base.rotationPointX = -(float)part.pos.x;
			part.model.base.rotationPointY = -(float)part.pos.y + offsetHeight;
			part.model.base.rotationPointZ = -(float)part.pos.z;
		}
		else {
			part.model.base.rotationPointY = offsetHeight;
		}
		
		if (part.rotationPos != null) {
			part.model.base.rotationPointX += (float)part.rotationPos.x;
			part.model.base.rotationPointY += (float)part.rotationPos.y;
			part.model.base.rotationPointZ += (float)part.rotationPos.z;
		}
	}

}
