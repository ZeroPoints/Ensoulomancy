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

    private final World worldObj;
    private Random random;
    private Biome[] biomesForGeneration;

    private NormalTerrainGenerator terraingen = new NormalTerrainGenerator();

    
    //public PurgatoryWorldSavedData purgatoryWorldSavedData;

    //private MapGenSpiritTemple spiritTempleGen = new MapGenSpiritTemple(this);

    
    
    /**
     * Our custom chunk generator
     */
    public PurgatoryChunkGenerator(World world, long seed, boolean mapFeaturesEnabled, String chunkProviderSettingsString) {

        this.worldObj = world;
        this.random = new Random((seed + 516) * 314);
        terraingen.setup(worldObj, random);
        
        /*
        if(purgatoryWorldSavedData == null) {
        	Main.log("Getting Existing World Saved Data");
        	purgatoryWorldSavedData = PurgatoryWorldSavedData.GetExisting(world);
        }
        else {
        	Main.log("Already Existing World Saved Data");
        }
        */

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

        //this.spiritTempleGen.generate(this.worldObj, x, z, chunkprimer);

        
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
        ChunkPos chunkpos = new ChunkPos(x, z);

        // Add biome decorations (like flowers, grass, trees, ...)
        biome.decorate(this.worldObj, this.random, blockpos);

        //this.spiritTempleGen.generateStructure(this.worldObj, this.random, chunkpos);

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

    	if (creatureType == EnumCreatureType.MONSTER)
        {
    		
	        long chunkPos = ChunkPos.asLong(pos.getX()/16, pos.getZ()/16);
        	if(PurgatoryWorldSavedData.GetExisting(worldObj).spiritTempleLocations.containsKey(chunkPos)) {
        		//Main.log("Spawn me some temple mobs...");
        		return WorldGenSpiritTemple.SpawnableMonsterList;
        	}
    		
    		/*
	    	if (this.spiritTempleGen.isInsideStructure(pos))
	        {
	            return this.spiritTempleGen.getSpawnList();
	        }
	
	        if (this.spiritTempleGen.isPositionInStructure(this.worldObj, pos) && this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK)
	        {
	            return this.spiritTempleGen.getSpawnList();
	        }
	        */
        }
        
		Biome biome = this.worldObj.getBiome(pos);
    	return biome.getSpawnableList(creatureType);
    	
	    
    }
    
    
    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        //return this.spiritTempleGen.getStructureName().equals(structureName) && this.spiritTempleGen != null ? this.spiritTempleGen.getNearestStructurePos(worldIn, position, findUnexplored) : null;
    	return null;
    }

    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        //return this.spiritTempleGen.getStructureName().equals(structureName) && this.spiritTempleGen != null ? this.spiritTempleGen.isInsideStructure(pos) : false;
    	return false;
    }
    
    /*
     * Dont know
     */
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
        //this.spiritTempleGen.generate(this.worldObj, x, z, (ChunkPrimer)null);

    }

	
}
