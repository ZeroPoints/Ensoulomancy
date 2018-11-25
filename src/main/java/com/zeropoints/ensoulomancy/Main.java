package com.zeropoints.ensoulomancy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.zeropoints.ensoulomancy.proxy.CommonProxy;
import com.zeropoints.ensoulomancy.util.Config;
import com.zeropoints.ensoulomancy.util.EnsoulomancyTab;



@Mod(modid=Main.MOD_ID, name=Main.NAME, version=Main.VERSION, dependencies = "required-after:metamorph@[1.1.6,)")
public class Main {
    
	public static final CreativeTabs ENSOULOMANCY_TAB = new EnsoulomancyTab();
	public static final String MOD_ID = "ensoulomancy";
	public static final String NAME = "Ensoulomancy";
	public static final String VERSION = "1.1a";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	public static final String CLIENT_PROXY_CLASS = "com.zeropoints.ensoulomancy.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.zeropoints.ensoulomancy.proxy.CommonProxy";
	public static final String SERVER_PROXY_CLASS = "com.zeropoints.ensoulomancy.proxy.ServerProxy";
	
	
	
	
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

    public static void log(String message) {
    	log(null, message);
    } 
    

	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER = e.getModLog();
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

}


