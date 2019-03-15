package com.zeropoints.ensoulomancy.world.biome.profane;

import java.util.Random;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.profane.EntityImp;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.world.biome.ICustomBiome;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;


public class TartarusBiome extends Biome implements ICustomBiome {




	/**
	 * Initiates the purgatories Profane biome 
	 */
	public TartarusBiome() {
		super(new Biome.BiomeProperties("TartarusBiome").setBaseHeight(-1.999F).setHeightVariation(0.4F));
		

		//May aswell register it i guess
		this.setRegistryName(Main.MOD_ID, "tartarus");
		

		//Set some default blocks for this biome
		this.topBlock = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
		this.fillerBlock = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);

		//Clear and set new defaults
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



	/**
	 * Height limitation for this biome
	 */
	@Override
	public int GetMaxHeight() {
		return 80;
	}


	/**
	 * Height limitation for this biome
	 */
	@Override
	public int GetMinHeight() {
		return 20;
	}
	
	
	
	
	
}
