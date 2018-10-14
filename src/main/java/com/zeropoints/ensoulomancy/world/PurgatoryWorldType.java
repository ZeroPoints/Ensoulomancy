package com.zeropoints.ensoulomancy.world;


import com.zeropoints.ensoulomancy.world.biome.PurgatoryBiomeProvider;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;


public class PurgatoryWorldType extends WorldType {

	
	/**
	 * Type...Dunno
	 */
	public PurgatoryWorldType() {
		super("PURGATORY");
	}

	/**
	 * Our biomes
	 */
	 @Override
    public BiomeProvider getBiomeProvider(World world)
    {
		 return new PurgatoryBiomeProvider();
    }
	
	 
	 /**
	 * Our chunks
	 */
    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {

        return new PurgatoryChunkGenerator(world, world.getSeed(), generatorOptions);

    }
    
    

    
	 
}
