package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.world.biome.HallowedBiome;
import com.zeropoints.ensoulomancy.world.biome.ProfaneBiome;
import com.zeropoints.ensoulomancy.world.biome.SpiritBiome;
import com.zeropoints.ensoulomancy.world.biome.VoidBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBiomes {


    public static final HallowedBiome HALLOWED_BIOME = new HallowedBiome();
    public static final SpiritBiome SPIRIT_BIOME = new SpiritBiome();
    public static final ProfaneBiome PROFANE_BIOME = new ProfaneBiome();
    public static final VoidBiome VOID_BIOME = new VoidBiome();

    //public static final RiverStyxBiome RIVERSTYX_BIOME = new RiverStyxBiome(new Biome.BiomeProperties("RiverStyxBiome"));

    
    
    /**
     * Used by genlayer to randomly pick from our known biome list - the void its a special case
     */
    public static Biome[] PurgatoryBiomesList;

    
    /**
     * Initialize our biomes
     */
	public static void init() {
		
		//Cant remember what this did
		ForgeRegistries.BIOMES.register(PROFANE_BIOME);
		ForgeRegistries.BIOMES.register(HALLOWED_BIOME);
		ForgeRegistries.BIOMES.register(SPIRIT_BIOME);
		ForgeRegistries.BIOMES.register(VOID_BIOME);
		
		//Cant remember what this did
		BiomeDictionary.addTypes(PROFANE_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(HALLOWED_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(SPIRIT_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(VOID_BIOME, Type.MAGICAL);
		

		
		PurgatoryBiomesList = new Biome[3];
		PurgatoryBiomesList[0] = PROFANE_BIOME;
		PurgatoryBiomesList[1] = HALLOWED_BIOME;
		PurgatoryBiomesList[2] = SPIRIT_BIOME;
		
		
	}
	
	
}