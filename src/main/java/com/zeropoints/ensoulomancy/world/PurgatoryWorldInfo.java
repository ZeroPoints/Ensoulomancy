package com.zeropoints.ensoulomancy.world;

import com.zeropoints.ensoulomancy.init.ModDimensions;

import net.minecraft.world.storage.WorldInfo;

public class PurgatoryWorldInfo extends WorldInfo {

	/**
	 * Dunno
	 */
	public PurgatoryWorldInfo() {
		super();
		
		setTerrainType(ModDimensions.purgatoryWorldType);
		//
	}
	
}
