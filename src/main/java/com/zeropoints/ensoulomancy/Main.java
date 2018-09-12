package com.zeropoints.ensoulomancy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.zeropoints.ensoulomancy.api.morphs.MorphManager;
import com.zeropoints.ensoulomancy.api.morphs.MorphUtils;
import com.zeropoints.ensoulomancy.init.ModEntities;
import com.zeropoints.ensoulomancy.proxy.CommonProxy;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;
import com.zeropoints.ensoulomancy.util.Reference;
import com.zeropoints.ensoulomancy.util.EnsoulomancyTab;
import com.zeropoints.ensoulomancy.world.PurgatoryWorldProvider;


@Mod(modid=Reference.MOD_ID, name=Reference.NAME, version=Reference.VERSION)
public class Main {
    
	public static final CreativeTabs ENSOULOMANCY_TAB = new EnsoulomancyTab("ensoulomancy_tab");
	
    public static boolean DEBUG = true;
    public static Logger LOGGER;
    
    
    /**
     * Log out the message if in DEBUG mode.
     */
    public static void log(Level level, String message) {
        if (DEBUG) {
        	if (level == null) {
        		level = Level.INFO;
        	}
            LOGGER.log(level, message);
        }
    }

    public static void LogMesssage(String message) {
    	log(null, message);
    } 
    
    public static void LogMesssage(String type, String message) {
    	log(null, type + " ----- " + message);
    } 

	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER = e.getModLog();
        new ConfigurationHandler();
        
		proxy.preInit(e);
		

    }
	
	@EventHandler
    public void init(FMLInitializationEvent e) {
		proxy.init(e);
    }

	@EventHandler
    public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
    }

	@EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        /* Setting up the blacklist */
        MorphManager.INSTANCE.setActiveSettings(MorphUtils.reloadMorphSettings());

        /* Register commands */
        //event.registerServerCommand(new CommandMorph());
        //event.registerServerCommand(new CommandAcquireMorph());
        //event.registerServerCommand(new CommandMetamorph());
    }
}


