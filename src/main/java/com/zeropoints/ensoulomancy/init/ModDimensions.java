package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.world.PurgatoryTeleporter;
import com.zeropoints.ensoulomancy.world.PurgatoryWorldInfo;
import com.zeropoints.ensoulomancy.world.PurgatoryWorldProvider;
import com.zeropoints.ensoulomancy.world.PurgatoryWorldType;
import com.zeropoints.ensoulomancy.world.gen.structure.WorldGenSpiritTemple;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;


/**
 * Container for anything to do with dimensions...
 * 
 * Not sure which ones are actually necessary...
 * 
 * @author ZeroPoints
 *
 */
public class ModDimensions {


    
    /**
     * Default Dim ID
     */
    public static int dimensionId = 133780085;
    

    /**
     * Contains  the Teleporter object for this mod. Set once someone first teleports to this dim through a teleporter block...
     */
    public static PurgatoryTeleporter PURGATORY_TELEPORTER = null;
    
	/**
	 * Type for this dimension
	 */
    public static DimensionType purgatoryDimensionType;

    /**
     * Current purgatory world type
     */
	public static PurgatoryWorldType purgatoryWorldType;

    /**
     * Current purgatory world info
     */
	public static PurgatoryWorldInfo purgatoryWorldInfo;
    
    /**
     * Structures for our dim
     */
    public static WorldGenSpiritTemple worldGenSpiritTemple = new WorldGenSpiritTemple();

    
	/**
	 * Init  stuff
	 */
    public static void init() {
    	purgatoryDimensionType = DimensionType.register(Main.MOD_ID, "_purgatory", dimensionId, PurgatoryWorldProvider.class, false);
        DimensionManager.registerDimension(dimensionId, purgatoryDimensionType);

		purgatoryWorldType = new PurgatoryWorldType();
		purgatoryWorldInfo = new PurgatoryWorldInfo();
		

    	GameRegistry.registerWorldGenerator(ModDimensions.worldGenSpiritTemple, 25);
    }

    
}
