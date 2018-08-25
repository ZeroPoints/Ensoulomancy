package com.zeropoints.soulcraft.world.biome;

import java.util.List;
import java.util.Random;

<<<<<<< HEAD
import com.google.common.collect.Lists;

import net.minecraft.block.material.Material;
=======
>>>>>>> 0aa22b7d51e31d84cc16166a5c2098af842a2196
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public interface ICustomBiome {

	
	

	/**
	 * Gets the monsters that this biome will spawn
	 */
	public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType, BlockPos pos);
	
<<<<<<< HEAD
	/**
	 * Max height this biome will cut blocks from
	 */
	public int GetMaxHeight() ;

	/**
	 * min height this biome will cut blocks from
	 */
	public int GetMinHeight() ;
	
	
	
	
	
	/**
	 * Gets the mobs that will be spawned when this biome is at the middle layer
	 */
	public List<Biome.SpawnListEntry> getMiddleSpawn();

	/**
	 * Gets the mobs that will be spawned when this biome is at its natural layer
	 */
	public List<Biome.SpawnListEntry> getLocaleSpawn();
	
	
	
	
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
		
        int j = -1;
        //K is noise for how many blocks it will FILL up. Fill goes reverse from air to fill depth
        //Probbably try use the noiseval passed in. Also try give larger valllue if its first emerald block is at the top of the areas spectrum
        int k = rand.nextInt(5) + 1;

        int l = x & 15;
        int i1 = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        IBlockState iPrevBlockState = Blocks.AIR.getDefaultState();
        
        boolean land = false;
        int counter = 0;
        
        //Probably doesnt need to go all the way to bedrock...as this is a top layer biome
        for (int j1 = maxHeight; j1 >= minHeight-1; --j1)
        {
            
        	
            IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

            //If block is an air block continue to next
            if (iblockstate2.getMaterial() == Material.AIR)
            {
            	
                //Hacky code to try fix problem with floating small land masses.
                
                iPrevBlockState = Blocks.AIR.getDefaultState();
                //we didnt fill up enough land blocks to create a nice land mass.
                if(j > 0) {
                	//Loop through the ammount of blocks found and delete them
                	while(counter >= 0 ) {
                    	
                    	//undo blocks as this is most likely floating dirt mass
                        chunkPrimerIn.setBlockState(i1, j1 + (counter) + 1, l, iPrevBlockState);
                    	
                        counter--;
                    }
                	j = 0;
                }
                
            }
            else if (iblockstate2.getBlock() == Blocks.EMERALD_BLOCK)
            {
            	if(!land) {
            		//My attempt to make blocks near top of range have larger fill noise
                	if(maxHeight - j1 < 14) {
                		//k = rand.nextInt(10) + 5;
                	}
                	land = true;
            	}
            }
            
            if(land) {
            	counter++;
            	
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
=======
	

	
	
	public IBlockState getBaseBlock();
	
	
>>>>>>> 0aa22b7d51e31d84cc16166a5c2098af842a2196
	
	
}
