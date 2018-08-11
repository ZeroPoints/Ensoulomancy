package com.zeropoints.soulcraft.init;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.zeropoints.soulcraft.world.PurgatoryWorldType;
import com.zeropoints.soulcraft.world.biome.HallowedBiome;
import com.zeropoints.soulcraft.world.biome.ProfaneBiome;
import com.zeropoints.soulcraft.world.biome.RiverStyxBiome;
import com.zeropoints.soulcraft.world.biome.SpiritBiome;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBiomes {

    public static final ProfaneBiome PROFANE_BIOME = new ProfaneBiome(new Biome.BiomeProperties("ProfaneBiome").setBaseHeight(1.5F).setHeightVariation(-0.5F));
    public static final HallowedBiome HALLOWED_BIOME = new HallowedBiome(new Biome.BiomeProperties("HallowedBiome").setBaseHeight(1.5F).setHeightVariation(0.5F));
    public static final SpiritBiome SPIRIT_BIOME = new SpiritBiome(new Biome.BiomeProperties("SpiritBiome").setBaseHeight(1.5F));

    //public static final RiverStyxBiome RIVERSTYX_BIOME = new RiverStyxBiome(new Biome.BiomeProperties("RiverStyxBiome"));
    
    public static int[] BIOMEIDS;
    
    public static Set<Biome> BIOMES = new HashSet<>();
    
	public static void init() {
		
		ForgeRegistries.BIOMES.register(PROFANE_BIOME);
		ForgeRegistries.BIOMES.register(HALLOWED_BIOME);
		ForgeRegistries.BIOMES.register(SPIRIT_BIOME);
		//ForgeRegistries.BIOMES.register(RIVERSTYX_BIOME);
		
		//Cant remember what this did
		BiomeDictionary.addTypes(PROFANE_BIOME, Type.HOT);
		BiomeDictionary.addTypes(HALLOWED_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(SPIRIT_BIOME, Type.SPOOKY);
		//BiomeDictionary.addTypes(RIVERSTYX_BIOME, Type.OCEAN);
		
		BIOMES.add(PROFANE_BIOME);
		BIOMES.add(HALLOWED_BIOME);
		BIOMES.add(SPIRIT_BIOME);
		//BIOMES.add(RIVERSTYX_BIOME);
		
		
		BIOMEIDS = new int[BIOMES.size()];
		BIOMEIDS[0] = Biome.getIdForBiome(PROFANE_BIOME);
		BIOMEIDS[1] = Biome.getIdForBiome(HALLOWED_BIOME);
		BIOMEIDS[2] = Biome.getIdForBiome(SPIRIT_BIOME);
		//BIOMEIDS[3] = Biome.getIdForBiome(RIVERSTYX_BIOME);
	}
	
	
}