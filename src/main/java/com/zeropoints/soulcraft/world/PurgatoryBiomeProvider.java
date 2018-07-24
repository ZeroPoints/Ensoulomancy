package com.zeropoints.soulcraft.world;

import java.util.List;

import com.google.common.collect.Lists;
import com.zeropoints.soulcraft.init.ModDimensions;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.BiomeManager.BiomeEntry;


public class PurgatoryBiomeProvider extends BiomeProvider {


	
    private BiomeCache biomeCache;
	private List<BiomeEntry> biomes;
    
    
	
	public PurgatoryBiomeProvider(World world) {
		super(ModDimensions.purgatoryWorldInfo);
		
		
		//this.
		
		//allowedBiomes = Lists.newArrayList(HALLOWED_BIOME, PROFANE_BIOME, STYX_BIOME);
		
		//biomeCache = new BiomeCache(this);

		
	}
	
	
}
