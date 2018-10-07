package com.zeropoints.ensoulomancy.world.biome;

import java.util.Random;

import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;


public class VoidBiome extends Biome implements ICustomBiome {

	
	/**
	 * Initiates the purgatories Void biome 
	 */
	public VoidBiome() {
		super(new Biome.BiomeProperties("VoidBiome").setBaseHeight(-30F).setHeightVariation(0.0F));

		

		this.setRegistryName(Reference.MOD_ID, "voidbiome");

		this.topBlock = Blocks.AIR.getDefaultState(); 
		this.fillerBlock = Blocks.AIR.getDefaultState(); 

		

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}
	
	
	

	/**
	 * Goes through each block from ceiling to floor replacing blocks related to this biome
	 */
	@Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        int l = x & 15;
        int i1 = z & 15;

        for (int j1 = 256; j1 >= 0; --j1)
        {
        	//If you want all to be air just get rid of IF
        	
        	chunkPrimerIn.setBlockState(i1, j1, l, AIR);
        	
        }
        
		
    }
	
	



	
	
	

	@Override
	public int GetMaxHeight() {
		return -9999;
	}
	
	@Override
	public int GetMinHeight() {
		return -9999;
	}
	
	
}
