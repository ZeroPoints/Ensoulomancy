package com.zeropoints.ensoulomancy.world.gen.structure;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.hallowed.EntityPixie;
import com.zeropoints.ensoulomancy.init.ModBiomes;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;
import com.zeropoints.ensoulomancy.util.Reference;
import com.zeropoints.ensoulomancy.world.PurgatoryWorldSavedData;
import com.zeropoints.ensoulomancy.world.biome.ICustomBiome;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenSpiritTemple implements IWorldGenerator {

    private static final ResourceLocation SPIRIT_TEMPLE = new ResourceLocation(Reference.MOD_ID + ":spirit_temple_centered");

    public static final String Name = Reference.MOD_ID + "_spirit_temple";
    

    public static List<Biome.SpawnListEntry> SpawnableMonsterList = Lists.<Biome.SpawnListEntry>newArrayList(
    		new SpawnListEntry(EntityPixie.class, 1, 1, 1)
    		);

    
    public static List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
    {
        switch (creatureType)
        {
            case MONSTER:
                return SpawnableMonsterList;
            case CREATURE:
                return null;
            case WATER_CREATURE:
                return null;
            case AMBIENT:
                return null;
            default:
            	return null;
        }
    }
    
    
    
    
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		
		
		
		if(world.provider.getDimension() != ConfigurationHandler.dimensionId) {
			return;
		}
	
        if(canSpawnStructureAtCoords(random, world, chunkX, chunkZ)) {
        	//Random block in that chunks 16x16 square
        	int x = chunkX * 16 + random.nextInt(4)+6;
        	int z = chunkZ * 16 + random.nextInt(4)+6;
        	
    		BlockPos basePos = new BlockPos(x, world.getHeight(x, z), z);
    		Biome currentBiome = world.getBiome(basePos);
    		//Biome is  spirit biome only
    		if(currentBiome.getRegistryName() != ModBiomes.SPIRIT_BIOME.getRegistryName()) {
    			return;
    		}
    		
			
    		//Random rotation...
			Rotation rot = Rotation.values()[random.nextInt(Rotation.values().length)];
            final PlacementSettings settings = new PlacementSettings().setRotation(rot);        
            final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), SPIRIT_TEMPLE);
            
            //Structure Size
            BlockPos structureSize = template.transformedSize(rot);
            
            
            //4 Corners
            BlockPos oppCornerTransformedOffset = Template.transformedBlockPos(settings, structureSize);
            BlockPos oppCorner = basePos.add(oppCornerTransformedOffset);
            
            //Reget the height for the center block. Otherwise its always corner origin...
            int centerX = x = x+(oppCornerTransformedOffset.getX()/2);
            int centerZ = z+(oppCornerTransformedOffset.getZ()/2);
            basePos = new BlockPos(x, world.getHeight(centerX, centerZ), z);
            	
            
            if(basePos.getY() < ((ICustomBiome)currentBiome).GetMinHeight()) {
            	Main.log("Spirit temple spawning way to low for biome");
            	return;
            }
            
            
    		StructureBoundingBox boundingBox = new StructureBoundingBox(basePos.getX(), basePos.getY(), basePos.getZ(), oppCorner.getX(), oppCorner.getY(), oppCorner.getZ());
            
    		
            
    		Main.log("SpiritTemple Generated: " + basePos.toString() + ", rotation: " + rot.toString() + ", oppCorner: " + oppCorner.toString() + ", structSize: " + structureSize.toString() + ", boundBox: " + boundingBox);
            template.addBlocksToWorld(world, basePos, settings);
            
           
            //4 Corners as our structure at max can overflow to 4 chunks
            long chunkPos1 = ChunkPos.asLong(basePos.getX()/16, basePos.getZ()/16);
            long chunkPos2 = ChunkPos.asLong(oppCorner.getX()/16, oppCorner.getZ()/16);            

            BlockPos otherCorner3 = basePos.add(oppCornerTransformedOffset.getX(), 0, 0);
            long chunkPos3 = ChunkPos.asLong(otherCorner3.getX()/16, otherCorner3.getZ()/16);

            BlockPos otherCorner4 = basePos.add(0, 0, oppCornerTransformedOffset.getZ());
            long chunkPos4 = ChunkPos.asLong(otherCorner4.getX()/16, otherCorner4.getZ()/16);
            

            //Lookup worldsaveddata. We do this so we can lookup in mobspawning. So we can spawn our mob type inside bounding box area of structure
            
            
            /*
            PurgatoryChunkGenerator.purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos1, boundingBox);      
            
            PurgatoryChunkGenerator.purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos2, boundingBox);
            
            PurgatoryChunkGenerator.purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos3, boundingBox);   
            
            PurgatoryChunkGenerator.purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos4, boundingBox);
            
            PurgatoryChunkGenerator.purgatoryWorldSavedData.markDirty();
            */
            
            
            /*
            ((PurgatoryChunkGenerator)chunkGenerator).purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos1, boundingBox);      
            
            ((PurgatoryChunkGenerator)chunkGenerator).purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos2, boundingBox);
            
            ((PurgatoryChunkGenerator)chunkGenerator).purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos3, boundingBox);   
            
            ((PurgatoryChunkGenerator)chunkGenerator).purgatoryWorldSavedData.spiritTempleLocations.put(chunkPos4, boundingBox);
            
            ((PurgatoryChunkGenerator)chunkGenerator).purgatoryWorldSavedData.markDirty();
            */
            

            PurgatoryWorldSavedData worldData = PurgatoryWorldSavedData.GetExisting(world);
            worldData.spiritTempleLocations.put(chunkPos1, boundingBox);      
            
            worldData.spiritTempleLocations.put(chunkPos2, boundingBox);
            
            worldData.spiritTempleLocations.put(chunkPos3, boundingBox);   
            
            worldData.spiritTempleLocations.put(chunkPos4, boundingBox);
            
            worldData.markDirty();
            
        }
        
        
	}
	
	

	
	
	/*
	 * Found in end structure code.
	 */
	private boolean canSpawnStructureAtCoords(Random random, World world, int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= 19;
        }

        if (chunkZ < 0)
        {
            chunkZ -= 19;
        }

        int k = chunkX / 20;
        int l = chunkZ / 20;
        random = world.setRandomSeed(k, l, 10387313);
        k = k * 20;
        l = l * 20;
        k = k + (random.nextInt(9) + random.nextInt(9)) / 2;
        l = l + (random.nextInt(9) + random.nextInt(9)) / 2;

        return (i == k && j == l);
       
    }
	
}
