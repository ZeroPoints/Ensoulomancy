package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.world.biome.VoidBiome;
import com.zeropoints.ensoulomancy.world.biome.hallowed.CloudBiome;
import com.zeropoints.ensoulomancy.world.biome.hallowed.HesperidesBiome;
import com.zeropoints.ensoulomancy.world.biome.hallowed.OlympusBiome;
import com.zeropoints.ensoulomancy.world.biome.profane.PandemoniumBiome;
import com.zeropoints.ensoulomancy.world.biome.profane.ShadowDenBiome;
import com.zeropoints.ensoulomancy.world.biome.profane.TartarusBiome;
import com.zeropoints.ensoulomancy.world.biome.spirit.DeadPlainsBiome;
import com.zeropoints.ensoulomancy.world.biome.spirit.DryadForestBiome;
import com.zeropoints.ensoulomancy.world.biome.spirit.RiverStyxBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBiomes {


    public static final CloudBiome CLOUD_BIOME = new CloudBiome();
    public static final HesperidesBiome HESPERIDES_BIOME = new HesperidesBiome();
    public static final OlympusBiome OLYMPUS_BIOME = new OlympusBiome();

    public static final DeadPlainsBiome DEADPLAINS_BIOME = new DeadPlainsBiome();
    public static final DryadForestBiome DRYADFOREST_BIOME = new DryadForestBiome();
    public static final RiverStyxBiome RIVERSTYX_BIOME = new RiverStyxBiome();

    public static final TartarusBiome TARTARUS_BIOME = new TartarusBiome();
    public static final PandemoniumBiome PANDEMONIUM_BIOME = new PandemoniumBiome();
    public static final ShadowDenBiome SHADOWDEN_BIOME = new ShadowDenBiome();
    
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
		ForgeRegistries.BIOMES.register(TARTARUS_BIOME);
		ForgeRegistries.BIOMES.register(PANDEMONIUM_BIOME);
		ForgeRegistries.BIOMES.register(SHADOWDEN_BIOME);
		ForgeRegistries.BIOMES.register(CLOUD_BIOME);
		ForgeRegistries.BIOMES.register(HESPERIDES_BIOME);
		ForgeRegistries.BIOMES.register(OLYMPUS_BIOME);
		ForgeRegistries.BIOMES.register(DEADPLAINS_BIOME);
		ForgeRegistries.BIOMES.register(DRYADFOREST_BIOME);
		ForgeRegistries.BIOMES.register(RIVERSTYX_BIOME);
		ForgeRegistries.BIOMES.register(VOID_BIOME);
		
		//Cant remember what this did
		BiomeDictionary.addTypes(TARTARUS_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(PANDEMONIUM_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(SHADOWDEN_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(CLOUD_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(HESPERIDES_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(OLYMPUS_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(DEADPLAINS_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(DRYADFOREST_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(RIVERSTYX_BIOME, Type.MAGICAL);
		BiomeDictionary.addTypes(VOID_BIOME, Type.MAGICAL);
		

		
		PurgatoryBiomesList = new Biome[9];
		PurgatoryBiomesList[0] = TARTARUS_BIOME;
		PurgatoryBiomesList[1] = PANDEMONIUM_BIOME;
		PurgatoryBiomesList[2] = SHADOWDEN_BIOME;
		PurgatoryBiomesList[3] = CLOUD_BIOME;
		PurgatoryBiomesList[4] = HESPERIDES_BIOME;
		PurgatoryBiomesList[5] = OLYMPUS_BIOME;
		PurgatoryBiomesList[6] = DEADPLAINS_BIOME;
		PurgatoryBiomesList[7] = DRYADFOREST_BIOME;
		PurgatoryBiomesList[8] = RIVERSTYX_BIOME;
		
		
	}
	
	
}