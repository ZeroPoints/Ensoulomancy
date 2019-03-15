package com.zeropoints.ensoulomancy.world.biome.hallowed;

import java.util.Random;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.hallowed.EntityPixie;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.world.biome.ICustomBiome;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;


public class HesperidesBiome extends Biome implements ICustomBiome {

	/**
	 * Initiates the purgatories Hallowed biome 
	 */
	public HesperidesBiome() {
		super(new Biome.BiomeProperties("HesperidesBiome").setBaseHeight(7.7F).setHeightVariation(0.4F));

		//May aswell register it i guess
		this.setRegistryName(Main.MOD_ID, "hesperides");

		//Set some default blocks for this biome
		this.topBlock = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GREEN); 
		this.fillerBlock = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GREEN);

		//Clear and set new defaults
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

	
	
	


	/**
	 * Height limitation for this biome
	 */
	@Override
	public int GetMaxHeight() {
		return 240;
	}
	
	


	/**
	 * Height limitation for this biome
	 */
	@Override
	public int GetMinHeight() {
		return 180;
	}
	
	
	
}
