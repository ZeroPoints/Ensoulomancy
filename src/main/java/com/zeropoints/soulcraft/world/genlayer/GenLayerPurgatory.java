package com.zeropoints.soulcraft.world.genlayer;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModBiomes;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerPurgatory extends GenLayer {
   
    
    

    public GenLayerPurgatory(long seed, GenLayer parentIn) {
        super(seed);
        parent = parentIn;
    }

    public GenLayerPurgatory(long seed) {
        super(seed);
    }

    
    /*
     * Generates the biome placements for the world
     * Uses the 3 biomes to seed world
     */
    @Override
    public int[] getInts(int x, int y, int width, int depth) {
    	
        int dest[] = IntCache.getIntCache(width * depth);
        for (int dz = 0; dz < depth; dz++) {
            for (int dx = 0; dx < width; dx++) {
            	//Dunno what this does ut i think the nextint function relies on it
            	initChunkSeed(dx + x, dz + y);

            	//Gets a random int babsed on chunkseed 
            	
            	dest[dx + dz * depth] = Biome.getIdForBiome(ModBiomes.PurgatoryBiomesList[nextInt(ModBiomes.PurgatoryBiomesList.length)]);
            	
            }
        }
        return dest;
    }
}