package com.zeropoints.soulcraft.init;

import com.zeropoints.soulcraft.util.ConfigurationHandler;
import com.zeropoints.soulcraft.util.Reference;
import com.zeropoints.soulcraft.world.PurgatoryWorldInfo;
import com.zeropoints.soulcraft.world.PurgatoryWorldProvider;
import com.zeropoints.soulcraft.world.PurgatoryWorldType;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {


    public static DimensionType purgatoryDimensionType;

	public static PurgatoryWorldType purgatoryWorldType;
	
	public static PurgatoryWorldInfo purgatoryWorldInfo;
    

    public static void init() {
        registerDimensionTypes();
        registerDimensions();
		purgatoryWorldType = new PurgatoryWorldType();
		purgatoryWorldInfo = new PurgatoryWorldInfo();
    }

    private static void registerDimensionTypes() {
    	purgatoryDimensionType = DimensionType.register(Reference.MOD_ID, "_purgatory", ConfigurationHandler.dimensionId, PurgatoryWorldProvider.class, false);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(ConfigurationHandler.dimensionId, purgatoryDimensionType);
    }
    
}
