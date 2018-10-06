package com.zeropoints.ensoulomancy.world.biome;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;

import com.google.common.collect.Lists;
import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.ghost.EntityIttanMomen;
import com.zeropoints.ensoulomancy.entity.hallowed.EntityPixie;
import com.zeropoints.ensoulomancy.init.ModBlocks;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.ChunkPrimer;


public class HallowedBiome extends Biome implements ICustomBiome {

	/**
	 * Initiates the purgatories Hallowed biome 
	 */
	public HallowedBiome() {
		super(new Biome.BiomeProperties("HallowedBiome").setBaseHeight(7.7F).setHeightVariation(0.4F));
		this.setRegistryName("ensoulomancy", "hallowed");
		this.topBlock = Blocks.END_STONE.getDefaultState(); 
		this.fillerBlock = ModBlocks.SOUL_STONE.getDefaultState();
		
		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityPixie.class, 1, 1, 1));
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
		return 240;
	}
	
	@Override
	public int GetMinHeight() {
		return 180;
	}
	
	
	
}
