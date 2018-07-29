package com.zeropoints.soulcraft.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import net.minecraftforge.common.config.Configuration;

import org.apache.commons.io.FileUtils;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.world.PurgatoryWorldProvider;

public class ConfigurationHandler {

    public static File directory = new File("config/soulcraft/");
    public static File configFile = new File(directory, "soulcraft.cfg");
    public static File worldPreset = new File(directory, "worldgen.json");

    public static Configuration config;    

    public static String generatorPreset = "";

    public static int dimensionId = 133780085;
    
    public static String defaultBiome = "minecraft:ice_mountains";

    
    
    public ConfigurationHandler() {
    	if (!directory.exists()) {
            Main.LogMesssage("Config","Generating config folder");
            directory.mkdirs();
        }
    	
        config = new Configuration(configFile);
        generatePresets();
        syncConfigData();
    }
    

    private void generatePresets () {

        final File preset = new File(directory, "world_generator_settings.json");

        if (!preset.exists()) {
            Main.LogMesssage("Config","World generator settings does not exist. Generating a new one.");

            try {
                FileUtils.copyURLToFile(PurgatoryWorldProvider.class.getResource("/assets/sc/presets/soulcraft_dimension_generator_settings.json"), preset);
                Main.LogMesssage("Config","Finished generating world generator settings.");
            }
            catch (final IOException e) {
            	Main.LogMesssage("Config","Could not generate world generator settings." + e.getMessage());
            }
        }

        Main.LogMesssage("Config","Reading world generator settings.");

        try {
            generatorPreset = FileUtils.readFileToString(preset, StandardCharsets.UTF_8);
            Main.LogMesssage("Config","World settings loaded: " + generatorPreset.replaceAll("\\R", "").replaceAll("\\s", " "));
        }
        catch (final IOException e) {
        	Main.LogMesssage("Config","Could not read world generator settings! Default will be used! " + e.getMessage());

        }
    }
    

    private void syncConfigData () {

        Main.LogMesssage("Config","Reading config file.");
        dimensionId = config.getInt("dimensionId", Configuration.CATEGORY_GENERAL, 133780085, Integer.MIN_VALUE, Integer.MAX_VALUE, "The id for the soulcraft dimension.");

        defaultBiome = config.getString("initialBiome", Configuration.CATEGORY_GENERAL, "minecraft:ice_mountains", "");

        if (config.hasChanged()) {

            Main.LogMesssage("Config","Saving config file.");
            config.save();
        }
    }    
    
}
