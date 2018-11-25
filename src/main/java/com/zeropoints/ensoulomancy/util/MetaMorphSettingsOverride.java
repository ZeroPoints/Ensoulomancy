package com.zeropoints.ensoulomancy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.zeropoints.ensoulomancy.Main;

import mchorse.metamorph.api.MorphSettings;
import mchorse.metamorph.api.events.RegisterSettingsEvent;
import mchorse.metamorph.api.json.MorphSettingsAdapter;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;





/**
 * Couldnt think of a better name of a class that adds ensoulomancies settings ontop of normal.
 * @author ZeroPoints
 *
 */
public class MetaMorphSettingsOverride {

	
    

    public static File morphsJson = new File(Config.directory, "morphs.json");
    
    public static String defaultMorphJson = "assets/" + Main.MOD_ID + "/defaults_morphs.json";
  
    
    
    /**
     * GSON instance that is responsible for deserializing morph settings
     */
    private final static Gson GSON = new GsonBuilder().registerTypeAdapter(MorphSettings.class, new MorphSettingsAdapter()).create();

    
	
    @SubscribeEvent
    public void onSettingsReload(RegisterSettingsEvent event)
    {
    	
    	
    	try
        {

        	Main.log("Ensoulomancy Metamorph Defaults From Asset Resources - " + defaultMorphJson);
            loadMorphSettings(event.settings, this.getClass().getClassLoader().getResourceAsStream(defaultMorphJson));

        	Main.log("Ensoulomancy Metamorph Overrides From User Definitions - " + morphsJson.getAbsolutePath());
    		loadMorphSettings(event.settings, new FileInputStream(morphsJson));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    	    	
    	
    }
    
    
    /**
     * Taken from metamorphs. Woulda been nice if they just made this public :S
     * Maybe other mods have a better way to add there stuff...ask them later...
     * Maybe write ensoulomancies so it doesnt override if it exists???
     * 
     * @param settings
     * @param input
     */
    public void loadMorphSettings(Map<String, MorphSettings> settings, InputStream input)
    {
        Scanner scanner = new Scanner(input, "UTF-8");

        @SuppressWarnings("serial")
        Type type = new TypeToken<Map<String, MorphSettings>>()
        {}.getType();

        Map<String, MorphSettings> data = GSON.fromJson(scanner.useDelimiter("\\A").next(), type);

        scanner.close();

        for (Map.Entry<String, MorphSettings> entry : data.entrySet())
        {
            String key = entry.getKey();
            MorphSettings morphSettings = entry.getValue();

            if (settings.containsKey(key))
            {
                settings.get(key).merge(morphSettings);
            }
            else
            {
                settings.put(key, morphSettings);
            }
        }
    }
    
    
    
	
}





