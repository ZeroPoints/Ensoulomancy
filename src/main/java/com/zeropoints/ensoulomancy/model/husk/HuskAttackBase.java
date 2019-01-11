package com.zeropoints.ensoulomancy.model.husk;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.model.husk.HuskBase.PartType;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

/**
 * The shared class for all 'attacking' body parts. This includes arms.
 * @author ChickenMobile
 */
public abstract class HuskAttackBase<T> extends HuskBase<T> {
	
	/*
	 * The body part this utility part will attach to.
	 * By default this is the body unless otherwise specified - NOTE: parts cannot be attached to itself
	 * TODO: the order of parts may need to be special in this case 
	 */
	public static HuskBase.PartType attachesTo = HuskBase.PartType.BODY;
	
	/*
	 * Which face this utility item attaches to on the given part
	 */
	public static EnumFacing facing = EnumFacing.DOWN;
	
	
	@Override
	public HuskPart DeserializeFromClass(String data, HuskModelHelper helper) {
		return new HuskPart(this, helper);
	}
	
	@Override
	public PartType GetPartType() {
		return PartType.ATTACK;
	}
	
}
