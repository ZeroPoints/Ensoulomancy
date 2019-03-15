package com.zeropoints.ensoulomancy.world.gen.layer;
import com.zeropoints.ensoulomancy.init.ModBiomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;


public class GenLayerVoid extends GenLayer
{
	
	
	/**
	 * Inits the river styx...Which is just the void biome. It places void between all different biome edges.
	 * @param seed
	 * @param parentIn
	 */
    public GenLayerVoid(long seed, GenLayer parentIn)
    {
        super(seed);
        parent = parentIn;
    }

  
	/*
	 * Goes through chunk and seperates biomes with a void biome. * 
	 * Returns the int values for biome locations in a chunk 
	 */
    @Override
    public int[] getInts(int x, int y, int width, int depth) {


        int[] aint = this.parent.getInts(x,y,width,depth);

    	int voidBiome = Biome.getIdForBiome(ModBiomes.VOID_BIOME);
    	
    	
        for (int dz = 0; dz < depth; dz++) {
            for (int dx = 0; dx < width; dx++) {
            	//dunno if i need this
                //initChunkSeed(dx + x, dz + y);
                
            	//East is pos X
            	int east = aint[(dx+1) + dz * depth];
            	//West is neg X
            	int west = voidBiome;
            	int westOffset = (dx-1) + dz * depth;
            	if(westOffset >= 0) {
            		 west = aint[westOffset];
            	}
            	
            	//North is neg Z
            	int north = voidBiome;
            	int northOffset = dx + (dz-1) * depth;
            	if(northOffset >= 0) {
            		north = aint[northOffset];
            	}
            	//South is pos Z
            	int south = aint[dx + (dz+1) * depth];
            	
            	int middleOffset = dx + dz * depth;
            	int middle =  aint[middleOffset];

            	//Checks all blocks next to this block and changes this block if its different
            	
            	if(middle != east && east != voidBiome) {
            		aint[middleOffset] = voidBiome;
            	}
            	else if(middle != west && west != voidBiome) {
            		aint[middleOffset] = voidBiome;
            	}
            	else if(middle != north && north != voidBiome) {
            		aint[middleOffset] = voidBiome;
            	}
            	else if(middle != south && south != voidBiome) {
            		aint[middleOffset] = voidBiome;
            	}
            	
                
            }
        }
        return aint;
        
        
    }
    

}