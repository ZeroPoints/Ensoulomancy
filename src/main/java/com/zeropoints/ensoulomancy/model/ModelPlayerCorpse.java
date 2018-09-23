package com.zeropoints.ensoulomancy.model;

import com.zeropoints.ensoulomancy.entity.EntityPlayerCorpse;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPlayerCorpse extends ModelBiped {
	
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;
    
    private boolean smallArms = false;

    public ModelPlayerCorpse() {
        super(0.0F, 0.0F, 64, 64);

        this.bipedLeftArm = new ModelRenderer(this, 32, 48);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
        this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
        this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	//super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	
    	EntityPlayerCorpse entity = entityIn instanceof EntityPlayerCorpse 
    			? (EntityPlayerCorpse)entityIn : null;

		// Just in case I suppose
		if (entity == null) {
			return;
		}
    			
    	if (entity.isAlyx) {
    		this.smallArms = true;
    		
    		this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
    	}
    	else {
    		this.smallArms = false;
    	}
    	
    	if (entity.isGhost()) {
    		// Render the entity ghostly
    		GlStateManager.enableBlend();
    		
    		// Render get up animation
    		ghostGetUpAnim(entity, ageInTicks);
    	}
    	
    	this.bipedHead.render(scale);
        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);
        this.bipedHeadwear.render(scale);

        this.bipedLeftLegwear.render(scale);
        this.bipedRightLegwear.render(scale);
        this.bipedLeftArmwear.render(scale);
        this.bipedRightArmwear.render(scale);
        this.bipedBodyWear.render(scale);

        if (entity.isGhost()) {
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
        }
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	
    }

    @Override
    public void postRenderArm(float scale, EnumHandSide side) {
        ModelRenderer modelrenderer = this.getArmForSide(side);

        if (this.smallArms) {
            float f = 0.5F * (float)(side == EnumHandSide.RIGHT ? 1 : -1);
            modelrenderer.rotationPointX += f;
            modelrenderer.postRender(scale);
            modelrenderer.rotationPointX -= f;
        }
        else {
            modelrenderer.postRender(scale);
        }
    }
    
    /**
     * If the player is a ghost, animate the ghost coming out the bed
     */
    private void ghostGetUpAnim(EntityPlayerCorpse entity, float ageInTicks) {
    	float ageAim = 70;
    	float age = ageInTicks > ageAim ? ageAim : ageInTicks;
    	float p = age / ageAim;
    	
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 0.35F);
    	
    	GlStateManager.rotate(easeInOutCubic(ageInTicks, 0, 1, 90) * 90, 1, 0, 0);
    	GlStateManager.translate(0, p * -0.75, p * -2);
    	GlStateManager.translate(0, -0.2F + MathHelper.cos(ageInTicks / (float)Math.PI / 2) * 0.1F, 0);
    }
    
    /**
     * Cubic easing formula
     * @param t current time
     * @param b start value
     * @param c change in value
     * @param d duration
     * @return eased value
     */
    private float easeInOutCubic(float t, float b, float c, float d) {
    	t /= d/2;
    	if (t < 1) {
    		return c/2*t*t*t + b;
		}
    	t -= 2;
    	return c/2*(t*t*t + 2) + b;
    }
    
}