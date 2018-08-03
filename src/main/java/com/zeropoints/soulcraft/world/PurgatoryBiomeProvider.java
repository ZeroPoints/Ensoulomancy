package com.zeropoints.soulcraft.world;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.zeropoints.soulcraft.init.ModDimensions;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;


public class PurgatoryBiomeProvider extends BiomeProvider {


	//private GenLayer genBiomes;

	
	
	
	public PurgatoryBiomeProvider(World world) {
		this(world.getSeed(), world.getWorldInfo().getTerrainType(), world.getWorldInfo().getGeneratorOptions());
	}
	
	public PurgatoryBiomeProvider(long seed, WorldType worldType, String chunkProviderSettings) {
		super(ModDimensions.purgatoryWorldInfo);
		
	}
	
	
	
	

	/*
	 * Gets how this biome provider initiates its GenLayers for spawning biomes
	 * The more zoom the larger the biome size
	 * Ignore genlayerriverstyx for now
	 */
    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
        GenLayer biomes = new GenLayerPurgatory(seed);

        biomes = new GenLayerZoom(1337800851, biomes);
        biomes = new GenLayerZoom(1337800852, biomes);
        biomes = new GenLayerZoom(1337800853, biomes);
        biomes = new GenLayerZoom(1337800854, biomes);

        //biomes = new GenLayerRiverStyx(1337800855, biomes);
        

        GenLayer biomeIndexLayer = new GenLayerVoronoiZoom(10L, biomes);
        biomeIndexLayer.initWorldGenSeed(seed);

        return new GenLayer[]{
                biomes,
                biomeIndexLayer
        };
    }
	
	
	
	
}
