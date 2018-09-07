package com.zeropoints.ensoulomancy.world;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBiomes;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;
import com.zeropoints.ensoulomancy.world.biome.PurgatoryBiomeProvider;

import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class PurgatoryWorldProvider extends WorldProvider {

	

    @Override
    protected void init () {

        biomeProvider = new PurgatoryBiomeProvider(world);
        
        
        
        hasSkyLight = true;
    	
        setDimension(ConfigurationHandler.dimensionId);
        
        Main.LogMesssage("PurgatoryWorldProvider", "init");
        
    }
    
	
	
    /**
	 * Dunno
	 */
	@Override
	public DimensionType getDimensionType() {
		
        return ModDimensions.purgatoryDimensionType;
	}

	
	
	
	/**
	 * Dunno
	 */
    @Override
    public int getAverageGroundLevel () {

        return 70;
    }

    /**
	 * Dunno
	 */
    @Override
    public boolean isSurfaceWorld () {

        return false;
    }
    
    
    /**
	 * Dunno
	 */
    @Override
    public IChunkGenerator createChunkGenerator() {
        Main.LogMesssage("PurgatoryWorldProvider", "createChunkGenerator");
        return new PurgatoryChunkGenerator(this.world, this.world.getSeed(), false, world.getWorldInfo().getGeneratorOptions());
    }
    
    
    /**
	 * Dunno
	 */
    /*
    @Override
    protected void generateLightBrightnessTable() {
    	float f = 12.0F;
    	for (int i = 0; i <= 15; i++) {
	    	float f1 = 12.0F - i / 15.0F;
	    	this.lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f);
    	}
    }
    	
*/
    
    
}