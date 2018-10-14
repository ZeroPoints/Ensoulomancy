package com.zeropoints.ensoulomancy.world;

import com.zeropoints.ensoulomancy.init.ModDimensions;

import net.minecraft.world.storage.WorldInfo;

public class PurgatoryWorldInfo extends WorldInfo {

	/**
	 * Im sure this class has a use.
	 * Biome provider uses these  fields from this object info.getSeed(), info.getTerrainType(), info.getGeneratorOptions()
	 * 
	 */
	public PurgatoryWorldInfo() {
		super();
		
		setTerrainType(ModDimensions.purgatoryWorldType);
	}
	
	

	
}
