package com.zeropoints.soulcraft.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.commons.lang3.time.StopWatch;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModBiomes;
import com.zeropoints.soulcraft.world.biome.ICustomBiome;

import net.minecraft.entity.EnumCreatureType;
//import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

/**
 * Our custom chunk generator
 * Mob spawning is called in here
 */
public class PurgatoryChunkGenerator implements IChunkGenerator {

    private final World worldObj;
    private Random random;
    private Biome[] biomesForGeneration;

    private NormalTerrainGenerator terraingen = new NormalTerrainGenerator();

    /**
     * Our custom chunk generator
     */
    public PurgatoryChunkGenerator(World world, long seed, boolean mapFeaturesEnabled, String chunkProviderSettingsString) {

        this.worldObj = world;
        this.random = new Random((seed + 516) * 314);
        terraingen.setup(worldObj, random);
        
    }

    

    /**
     * Stuff i played with...i think
     */
    @Override
    public Chunk generateChunk(int x, int z) {
        
    	
    	ChunkPrimer chunkprimer = new ChunkPrimer();

    	
        // Setup biomes for terraingen
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        terraingen.setBiomesForGeneration(biomesForGeneration);
        //Builds height map stuff for chunk based on biomes
        terraingen.generate(x, z, chunkprimer);

        // Setup biomes again for actual biome decoration
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        // This will replace top layer with biome top block and filler block
        terraingen.replaceBiomeBlocks(x, z, chunkprimer, this, biomesForGeneration);

        // Generate caves
        //this.caveGenerator.generate(this.worldObj, x, z, chunkprimer);

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        
        
        return chunk;
    }


    /*
     * Dont know
     */
    @Override
    public void populate(int x, int z) {
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.worldObj.getBiome(blockpos.add(16, 0, 16));

        // Add biome decorations (like flowers, grass, trees, ...)
        biome.decorate(this.worldObj, this.random, blockpos);

        // Make sure animals appropriate to the biome spawn here when the chunk is generated
        WorldEntitySpawner.performWorldGenSpawning(this.worldObj, biome, i + 8, j + 8, 16, 16, this.random);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    /**
     * 
     *  Gets possible mobs from our biomes
     * 
     *
     */
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
    	return null;

    	/*
    	Biome biome = this.worldObj.getBiome(pos);
    	if(biome instanceof ICustomBiome) {
    		return ((ICustomBiome)biome).getSpawnableList(creatureType, pos);	
		}
    	
    	
    	//DEFAULT RETURN SPIRIT BIOME MOD. This shouldnt fire...
		return ModBiomes.SPIRIT_BIOME.getSpawnableList(creatureType, pos);
	
	    */
    }
    
    
    /*
     * Dont know
     */
    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }
    
    /*
     * Dont know
     */
    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
    
    /*
     * Dont know
     */
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

	
}
