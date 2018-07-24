package com.zeropoints.soulcraft.init;

import com.zeropoints.soulcraft.world.HallowedBiome;
import com.zeropoints.soulcraft.world.ProfaneBiome;
import com.zeropoints.soulcraft.world.PurgatoryWorldType;
import com.zeropoints.soulcraft.world.StyxBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBiomes {


    public static final ProfaneBiome PROFANE_BIOME = new ProfaneBiome(new Biome.BiomeProperties("ProfaneBiome"));
    public static final HallowedBiome HALLOWED_BIOME = new HallowedBiome(new Biome.BiomeProperties("HallowedBiome"));
    public static final StyxBiome STYX_BIOME = new StyxBiome(new Biome.BiomeProperties("StyxBiome"));

    
	
	public static void init() {

		ForgeRegistries.BIOMES.register(PROFANE_BIOME);
		ForgeRegistries.BIOMES.register(HALLOWED_BIOME);
		ForgeRegistries.BIOMES.register(STYX_BIOME);

		
		//Biome.registerBiome(2000, "Profane", PROFANE_BIOME);
		//Biome.registerBiome(2001, "Hallowed", HALLOWED_BIOME);
		//Biome.registerBiome(2002, "Styx", STYX_BIOME);
	}
	
	
}
