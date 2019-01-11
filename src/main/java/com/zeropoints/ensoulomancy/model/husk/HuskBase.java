package com.zeropoints.ensoulomancy.model.husk;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.zeropoints.ensoulomancy.model.husk.head.GuardianHead;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public abstract class HuskBase<T> extends ModelBase {

	protected Map<String, T> INSTANCE = new HashMap<String, T>();
	private static final Vec3d DEFAULT_VECTOR = new Vec3d(0,0,0);
	
	public T Get(Class<T> clazz, Vec3d key) {
		if (key != null && this.INSTANCE.containsKey(key.toString())) {
			return this.INSTANCE.get(key.toString());
		}
		try {	
			if (key.equals(DEFAULT_VECTOR) || key == null) {
				return (T)this;
			}
			
			T item = clazz.getDeclaredConstructor(Vec3d.class).newInstance(key);
			INSTANCE.put(key.toString(), item);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * The part of the model all others in this model are attached to (even if it is invisible).
	 * The rotation point of this item is where it will attach to parent model and is worked out from type of model
	 */
	public ModelRenderer base; 
	
	/* 
	 * The parent renderer this shall attach onto. Should only be referred to once other bad things.
	 * This value doesn't need to be set 
	 */
	public ModelRenderer parent = null;
	
	/*
	 * Type of part this is. Generally decided by the extending of other base abstract husk classes
	 */
	public abstract PartType GetPartType();
	
	// TODO: extra options, perhaps things like decoration?
	public enum PartType {
		EXTRA, HEAD, BODY, LEGS, ARMS, UTILITY, ATTACK
	}
	
	/* Specific case where this body part is treated as the body when the main soul in the husk. */
	public boolean isAlsoBody;
	
	public int initialYOffset = 0;
	public int baseWidth;
	public int baseHeight = 24;
	public int baseDepth;
	
	/** rotation point offset */
	public Vec3d pos = null;
	
	/**
	 * Overrideable function to attach models to this
	 * Child renderers will mock the angles of the parent item
	 */
	//public abstract void AttachModelRenderers(ModelRenderer parentModel);
	
	/**
	 * Overrideable function that must be included on every Model's page
	 * Often the base class below the top level will handle this method for each common type but may not always be the case. E.g. ModelHeadBase 
	 */
	public abstract HuskPart DeserializeFromClass(String data, HuskModelHelper helper);
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.preRender();
		this.base.render(scale);
		this.postRender();
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
	
	/** Method that calls special methods before render is done */
	public void preRender() {}
	
	/** Method that calls special methods after render is done. Can be used to undo GLStates set in the preRender() method. */
	public void postRender() {}
	
	/** Insert base box and set HuskBase variables with it */
	protected void addBaseBox(ModelRenderer part, Vec3d offset, float offX, float offY, float offZ, int width, int depth, int height, float scaleFactor) {
		part.addBox((float)offset.x + offX, (float)offset.y + offY, (float)offset.z + offZ, width, depth, height, scaleFactor);
		this.baseWidth = width;
		this.baseHeight = height;
		this.baseDepth = depth;
	}
	/** Insert base box and set HuskBase variables with it */
	protected void addBaseBox(ModelRenderer part, Vec3d offset, float offX, float offY, float offZ, int width, int depth, int height) {
		this.addBaseBox(part, offset, offX, offY, offZ, width, depth, height, 0.0F);
	}
	
	/** A way to insert a box into the document while caring about initial offset variables */
	protected void addBox(ModelRenderer part, Vec3d offset, float offX, float offY, float offZ, int width, int depth, int height, float scaleFactor) {
		part.addBox((float)offset.x + offX, (float)offset.y + offY, (float)offset.z + offZ, width, depth, height, scaleFactor);
	}
	/** A way to insert a box into the document while caring about initial offset variables */
	protected void addBox(ModelRenderer part, Vec3d offset, float offX, float offY, float offZ, int width, int depth, int height) {
		part.addBox((float)offset.x + offX, (float)offset.y + offY, (float)offset.z + offZ, width, depth, height);
	}
	/** A way to insert a box into the document while caring about initial offset variables */
	protected void addBox(ModelRenderer part, Vec3d offset, float offX, float offY, float offZ, int width, int depth, int height, boolean mirrored) {
		part.addBox((float)offset.x + offX, (float)offset.y + offY, (float)offset.z + offZ, width, depth, height, mirrored);
	}
	
}
