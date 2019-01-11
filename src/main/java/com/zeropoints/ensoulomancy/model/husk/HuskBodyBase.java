package com.zeropoints.ensoulomancy.model.husk;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.model.husk.HuskBase.PartType;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * A shared class for all body. 
 * The body is always the first / parent of all other body parts. Therefore the serializer just needs to return this.
 * @author ChickenMobile
 */
public abstract class HuskBodyBase<T> extends HuskBase<T> {
	
	public Vec3d headPos; // Always have to set this, even if the body doubles as a head. Used Vec3d because it accepts doubles
	public Vec3d headRotationPos; // Only for heads that do not rotate round the center bottom. E.g. blaze head rotates from true center 
	public float scale = 1.0F;
	public BodyType bodyType;
	public boolean bodyIsHead = false; // Used for creatures like guardians who's body is their head
	
	public static enum BodyType {
		UNKONWN,
		BIPED, // Stands on two legs with two arms 
		QUADRAPED, // Stands on four legs - legs could be replaced with 'ability' legs
		BIRD, // Stands on two legs but considered an animal
		FISH, // No arms and a tail or legs
		CENTAUR // Stands on four legs with two arms - not sure if this one will be used unless a custom mob is introduced
	}
	
	//TODO: Not sure if this returns the top type class or just this class
	@Override
	public HuskPart DeserializeFromClass(String data, HuskModelHelper helper) {
		HuskPart huskPart = new HuskPart(this, helper);
		huskPart.scale = this.scale;
		return huskPart;
	}
	
	@Override
	public PartType GetPartType() {
		return PartType.BODY;
	}
}
