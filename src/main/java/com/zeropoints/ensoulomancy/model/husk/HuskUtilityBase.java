package com.zeropoints.ensoulomancy.model.husk;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.model.husk.HuskBase.PartType;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

/**
 * The shared class for all utility parts
 * @author ChickenMobile
 * @since 27/10/2018
 */
public abstract class HuskUtilityBase<T> extends HuskBase<T> {
		
	/**
	 * Which face this utility item attaches to on the given part
	 */
	public static EnumFacing facing = EnumFacing.DOWN;
	
	/**
	 * Get the position relative to the given body part.
	 * A biped is something that walks on 2 legs
	 */
	public abstract Vec3d GetPosFromBodyPart(HuskBase body);
	
	
	@Override
	public HuskPart DeserializeFromClass(String data, HuskModelHelper helper) {
		return new HuskPart(this, helper);
	}
	
	@Override
	public PartType GetPartType() {
		return PartType.UTILITY;
	}
	
}
