package com.zeropoints.ensoulomancy.util;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;

import net.minecraft.util.ResourceLocation;

public class SoulSkullType {
	public final String name;
	public final String entityname;
	public final ResourceLocation texture;
	private final String _texture;
	public final ModelHeadBase model;

	public SoulSkullType(String name, String texture, ModelHeadBase model) {
		this.name = name.toLowerCase();
		this.entityname = name;
		this._texture = texture;
		this.texture = new ResourceLocation(texture + ".png");
		this.model = model;
		
		if (model == null) {
			throw new IllegalArgumentException("Head model for " + this + " cannot be null!");
		}
	}

}