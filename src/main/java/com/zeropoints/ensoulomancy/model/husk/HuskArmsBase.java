package com.zeropoints.ensoulomancy.model.husk;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * The shared class for all Arms -- does attack conflict with this?
 * @author ChickenMobile
 */
public abstract class HuskArmsBase extends ModelBase {
	
	public Vec3d headPos;
	public ModelRenderer body;
	
	public HuskArmsBase() {
		this.headPos = new Vec3d(0, 0, 0);
	}
	
}
