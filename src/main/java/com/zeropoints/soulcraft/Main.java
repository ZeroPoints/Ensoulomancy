package com.zeropoints.soulcraft;

import com.zeropoints.soulcraft.proxy.CommonProxy;
import com.zeropoints.soulcraft.util.Reference;
import com.zeropoints.soulcraft.util.SoulcraftTab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;


@Mod(modid= Reference.MOD_ID, name=Reference.NAME, version=Reference.VERSION)
public class Main {

	@Mod.Instance
	public static Main instance;
	
	public static final CreativeTabs SOULCRAFT_TAB = new SoulcraftTab("soulcraft_tab");
	
	
    public static Logger logger;
    public static void LogMesssage(String type, String message) {
    	logger.info(type + " ----- " + message);
    } 
    
    
    
    
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		
        proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
	}
	 
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		
	}
	
	
	
}


