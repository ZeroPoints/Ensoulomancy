package com.zeropoints.soulcraft.world.biome;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;

import com.google.common.collect.Lists;
import com.zeropoints.soulcraft.Main;

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
	public HallowedBiome(BiomeProperties properties) {
		super(properties);


		this.setRegistryName("sc", "hallowed");

		this.topBlock = Blocks.END_STONE.getDefaultState(); 
		this.fillerBlock = Blocks.SOUL_SAND.getDefaultState();
		

	}
	
	
	/**
	 * Goes through each block from ceiling to floor replacing blocks related to this biome
	 */
	@Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
		ICustomBiome.CustomeGenTerrainBlocks(this.topBlock, this.fillerBlock, GetMaxHeight(), GetMinHeight(), worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

	
	/**
	 * Gets the monsters that this biome will spawn
	 */
	public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType, BlockPos pos)
    {

    	
    	if(pos.getY() > 50 && pos.getY() <= 90) {
    		return getMiddleSpawn();
    	}
    	if(pos.getY() > 90) {
    		return getLocaleSpawn();
    	}
		return null;

    }

	
	@Override
	public List<SpawnListEntry> getMiddleSpawn() {    
		return Lists.newArrayList(
				new SpawnListEntry(net.minecraft.entity.monster.EntitySilverfish.class, 1, 1, 1)
				) ;	
	}

	@Override
	public List<SpawnListEntry> getLocaleSpawn() {
		return Lists.newArrayList(
				new SpawnListEntry(net.minecraft.entity.monster.EntityEvoker.class, 1, 1, 1)
				) ;	
	}

	

	@Override
	public int GetMaxHeight() {
		return 220;
	}
	
	@Override
	public int GetMinHeight() {
		return 178;
	}
	
	
	
}