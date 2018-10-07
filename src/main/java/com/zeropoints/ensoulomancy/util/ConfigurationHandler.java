package com.zeropoints.ensoulomancy.util;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.world.PurgatoryTeleporter;

public class ConfigurationHandler {

    public static File directory = new File("config/ensoulomancy/");
    public static File configFile = new File(directory, "ensoulomancy.cfg");
    public static File worldPreset = new File(directory, "worldgen.json");

    public static Configuration config;    

    public static String generatorPreset = "";

    public static int dimensionId = 133780085;
    
    public static String defaultBiome = "minecraft:ice_mountains";

    
    public static PurgatoryTeleporter PURGATORY_TELEPORTER = null;
    
    
    public ConfigurationHandler() {
    	if (!directory.exists()) {
            Main.log(Level.INFO, "Generating config folder");
            directory.mkdirs();
        }
    	
        config = new Configuration(configFile);
        generatePresets();
        syncConfigData();
    }
    

    private void generatePresets () {

        
    }
    

    private void syncConfigData () {
    	Main.log(Level.INFO, "Reading config file.");
        dimensionId = config.getInt("dimensionId", Configuration.CATEGORY_GENERAL, 133780085, Integer.MIN_VALUE, Integer.MAX_VALUE, "The id for the ensoulomancy dimension.");

        defaultBiome = config.getString("initialBiome", Configuration.CATEGORY_GENERAL, "minecraft:ice_mountains", "");

        if (config.hasChanged()) {
        	Main.log(Level.INFO, "Saving config file.");
            config.save();
        }
    }    
    
}
