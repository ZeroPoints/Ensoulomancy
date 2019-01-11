package com.zeropoints.ensoulomancy.model.husk;

import com.zeropoints.ensoulomancy.model.husk.HuskBase.PartType;
import com.zeropoints.ensoulomancy.model.husk.legs.BipedLegs;
import com.zeropoints.ensoulomancy.model.husk.legs.QuadrapedLegs;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * The shared class for all legs
 * @author ChickenMobile
 */
public abstract class HuskLegsBase<T> extends HuskBase<T> {

	// There will always be at least 2 legs
	public ModelRenderer leg1;
    public ModelRenderer leg2;

    public HuskLegsBase() {
    	this.base = new ModelRenderer(this, 0, 0);
        this.base.addBox(0, 0, 0, 0, 0, 0, 0);
        this.base.setRotationPoint(0, 0, 0);
    }
    
    /**
     * Depending on the bounds of the body, place the legs dead center on the bottom face
     */
	public Vec3d GetLegPosFromBodyPart(HuskBase body) {
		return new Vec3d(0, -body.baseHeight / 2.0F, 0);
	}
	
	@Override
	public HuskPart DeserializeFromClass(String data, HuskModelHelper helper) {
		return new HuskPart(this, helper);
	}
	
	@Override
	public PartType GetPartType() {
		return PartType.LEGS;
	}
	
}
