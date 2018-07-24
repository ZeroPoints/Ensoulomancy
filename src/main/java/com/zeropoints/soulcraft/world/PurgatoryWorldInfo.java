package com.zeropoints.soulcraft.world;

import com.zeropoints.soulcraft.init.ModDimensions;

import net.minecraft.world.storage.WorldInfo;

public class PurgatoryWorldInfo extends WorldInfo {

	public PurgatoryWorldInfo() {
		super();
		
		setTerrainType(ModDimensions.purgatoryWorldType);
		//
	}
	
}
