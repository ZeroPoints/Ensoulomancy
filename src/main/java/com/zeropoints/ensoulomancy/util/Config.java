package com.zeropoints.ensoulomancy.util;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.world.PurgatoryTeleporter;


/**
 * Ensoulomancys config stuff...
 * 
 * 
 * @author ZeroPoints
 *
 */
public class Config {

    public static File directory = new File("config/" + Main.MOD_ID + "/");
    public static File configFile = new File(directory, Main.MOD_ID + ".cfg");
    
    public static Configuration CurrentConfig;    
   
    
    
    /**
     * Init for config for this mod
     */
    public Config() {
    	if (!directory.exists()) {
            Main.log(Level.INFO, "Generating config folder");
            directory.mkdirs();
        }
    	
    	//Creates a conf thingy with helpers
    	CurrentConfig = new Configuration(configFile);
        GeneratePresets();
        SyncConfigData();
    }
    

    /**
     * Defaults for our mod.
     * 
     */
    private void GeneratePresets () {

    }
    

    
    /**
     * Read from Configuration into var for ease of access.
     * Resave all presets + overrides back into Config...
     * 
     */
    private void SyncConfigData () {
    	Main.log(Level.INFO, "Reading config file.");
    	
        ModDimensions.dimensionId = CurrentConfig.getInt("dimensionId", Configuration.CATEGORY_GENERAL, 133780085, Integer.MIN_VALUE, Integer.MAX_VALUE, "The id for the ensoulomancy dimension.");

        

        if (CurrentConfig.hasChanged()) {
        	Main.log(Level.INFO, "Saving config file.");
        	CurrentConfig.save();
        }
    }    
    
}
