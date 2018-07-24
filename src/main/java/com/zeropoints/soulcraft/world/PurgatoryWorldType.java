package com.zeropoints.soulcraft.world;


import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;


public class PurgatoryWorldType extends WorldType {

	public PurgatoryWorldType() {
		super("PURGATORY");

	}

	
	 @Override
    public BiomeProvider getBiomeProvider(World world)
    {
		 return new PurgatoryBiomeProvider(world);
    }
	
	 

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
    	//, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions
        return new PurgatoryChunkGenerator(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);

    }
    
    

    
    
	 
}
