package com.zeropoints.ensoulomancy.world.biome;

import java.util.Random;


import com.zeropoints.ensoulomancy.entity.profane.EntityImp;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;


public class ProfaneBiome extends Biome implements ICustomBiome {


	/**
	 * Initiates the purgatories Profane biome 
	 */
	public ProfaneBiome() {
		super(new Biome.BiomeProperties("ProfaneBiome").setBaseHeight(-1.999F).setHeightVariation(0.4F));
		this.setRegistryName(Reference.MOD_ID, "profane");
		this.topBlock = Blocks.NETHERRACK.getDefaultState(); 
		this.fillerBlock = ModBlocks.SOUL_STONE.getDefaultState();
		
		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityImp.class, 1, 1, 1));
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
		ICustomBiome.CustomeGenTerrainBlocks(this.topBlock, this.fillerBlock, GetMaxHeight(), GetMinHeight(), worldIn, rand, chunkPrimerIn, x, z, noiseVal);

    }

	
	@Override
	public int GetMaxHeight() {
		return 80;
	}
	
	@Override
	public int GetMinHeight() {
		return 20;
	}
	
	
	
	
	
}
