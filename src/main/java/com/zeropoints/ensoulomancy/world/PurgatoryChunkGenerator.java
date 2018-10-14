package com.zeropoints.ensoulomancy.world;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.world.gen.structure.WorldGenSpiritTemple;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

/**
 * Our custom chunk generator
 * Mob spawning is called in here
 */
public class PurgatoryChunkGenerator implements IChunkGenerator {

    private final World world;
    private Random random;
    private Biome[] biomesForGeneration;
    private NormalTerrainGenerator terraingen = new NormalTerrainGenerator();

   
    
    
    /**
     * Our custom chunk generator
     * Mob spawning is called in here
     */
    public PurgatoryChunkGenerator(World world, long seed, String chunkProviderSettingsString) {
        this.world = world;
        //dunno why peeps dont just use seed as init for random...or just use a timestamp
        this.random = new Random((seed + 516) * 314);
        terraingen.setup(this.world, random);
    }

    

    /**
     * Generates chunk stuff
     */
    @Override
    public Chunk generateChunk(int x, int z) {
    	ChunkPrimer chunkprimer = new ChunkPrimer();
        // Setup biomes for terraingen
    	//CURIOUS  WHY  THIS DOES 10x10...not 16x16
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        terraingen.setBiomesForGeneration(biomesForGeneration);
        //Builds height map stuff for chunk based on biomes
        terraingen.generate(x, z, chunkprimer);
        // Setup biomes again for actual biome decoration
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        // This will replace top layer with biome top block and filler block
        terraingen.replaceBiomeBlocks(x, z, chunkprimer, this, biomesForGeneration);
        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }
        
        //should i use this..since ive actually disabled skylight in  my world due to lag from large air below blocks.
        chunk.generateSkylightMap();
        
        
        return chunk;
    }


    /*
     * Probs gonna change this from default...mc style...
     * 
     */
    @Override
    public void populate(int x, int z) {
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        ChunkPos chunkpos = new ChunkPos(x, z);

        // Add biome decorations (like flowers, grass, trees, ...)
        biome.decorate(this.world, this.random, blockpos);


        // Make sure animals appropriate to the biome spawn here when the chunk is generated
        WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.random);
    }

    
    
    
    

    /**
     *  Gets possible mobs from our biomes
     */
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {

    	if (creatureType == EnumCreatureType.MONSTER)
        {
    		//Spawn in temple
	        long chunkPos = ChunkPos.asLong(pos.getX()/16, pos.getZ()/16);
        	if(PurgatoryWorldSavedData.GetExisting(this.world).spiritTempleLocations.containsKey(chunkPos)) {
        		return WorldGenSpiritTemple.SpawnableMonsterList;
        	}
    		
        }
        
		Biome biome = this.world.getBiome(pos);
    	return biome.getSpawnableList(creatureType);
    	
	    
    }
    
    
    

    
    /**
     * Dont know
     */
    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }
    
    /**
     * Dont know
     */
    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
    	return null;
    }

    /**
     * Dont know
     */
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
    	return false;
    }
    
    /**
     * Dont know
     */
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

	
}
