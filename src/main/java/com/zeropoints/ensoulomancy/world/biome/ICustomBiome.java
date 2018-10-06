package com.zeropoints.ensoulomancy.world.biome;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public interface ICustomBiome {

	
	

	/**
	 * Gets the monsters that this biome will spawn based  on blockpos height
	 * Removed for now
	 */
	//public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType, BlockPos pos);
	

	/**
	 * Max height this biome will cut blocks from
	 */
	public int GetMaxHeight() ;

	/**
	 * min height this biome will cut blocks from
	 */
	public int GetMinHeight() ;
	
	
	
	
	

	
	
	/**
	 * Reusable static functions used between the 3 biomes
	 * 
	 * @param topBlock
	 * @param fillerBlock
	 * @param maxHeight
	 * @param minHeight
	 * @param worldIn
	 * @param rand
	 * @param chunkPrimerIn
	 * @param x
	 * @param z
	 * @param noiseVal
	 */
	public static void CustomeGenTerrainBlocks(IBlockState topBlock, IBlockState fillerBlock, int maxHeight, int minHeight, World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		

        //K is noise for how many blocks it will FILL up. Fill goes reverse from air to fill depth

        int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);        
        int j = -1;
        int l = x & 15;
        int i1 = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        IBlockState iPrevBlockState = Blocks.AIR.getDefaultState();
        
        boolean landFound = false;
        
        //Probably doesnt need to go all the way to bedrock...Keep it without  our biomes range
        for (int j1 = maxHeight; j1 >= minHeight-1; --j1)
        {
        	
        	
            IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

            //If block is an air block continue to next
            if (iblockstate2.getMaterial() == Material.AIR)
            {
                iPrevBlockState = Blocks.AIR.getDefaultState();
            }
            else if (iblockstate2.getBlock() == Blocks.STONE)
            {
            	if(!landFound) {
            		//My attempt to make blocks near top of range have larger fill noise as brains not working
            		//Side effect is if the edge of the biome is at a high point it will look weird
            		/*
            		if(minHeight + ((maxHeight - minHeight) / 3) < j1 ) {
                		k = (int)(noiseVal / 3.0D + 6.0D + rand.nextDouble() * 0.25D);
                		//Main.log("Top Height blocks. Try fill more with noise");
                	}
                	*/
                	landFound = true;
            	}
            }
            
            
            if(landFound) {
            	//If previous was air then this emerald placeholder block can be changed to our top soil
            	if (j == -1 && iPrevBlockState.getMaterial() == Material.AIR)
                {
                    j = k;
                    iPrevBlockState = topBlock;
                    chunkPrimerIn.setBlockState(i1, j1, l, iPrevBlockState);
                    
                }
            	//Otherwise we are past top soil and we replace lower blocks with filler soil
                else if (j > 0)
                {
                    --j;
                    iPrevBlockState = fillerBlock;
                    chunkPrimerIn.setBlockState(i1, j1, l, iPrevBlockState);
                }
            	//Replaces any extra past k noise with air...
                else {
                	iPrevBlockState = Blocks.AIR.getDefaultState();
                    chunkPrimerIn.setBlockState(i1, j1, l, iPrevBlockState );                    
                }
            }
            
        }
	}

	
	
}
