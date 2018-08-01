package com.zeropoints.soulcraft;

import com.zeropoints.soulcraft.init.ModEntities;
import com.zeropoints.soulcraft.proxy.CommonProxy;
import com.zeropoints.soulcraft.util.ConfigurationHandler;
import com.zeropoints.soulcraft.util.Reference;
import com.zeropoints.soulcraft.util.SoulcraftTab;
import com.zeropoints.soulcraft.world.PurgatoryWorldProvider;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;


@Mod(modid= Reference.MOD_ID, name=Reference.NAME, version=Reference.VERSION)
public class Main {
    
	public static final CreativeTabs SOULCRAFT_TAB = new SoulcraftTab("soulcraft_tab");
	
    public static Logger logger;
    
    public static void LogMesssage(String type, String message) {
    	logger.info(type + " ----- " + message);
    } 

	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();

        new ConfigurationHandler();
		
		proxy.preInit(e);
    }

	@EventHandler
    public void init(FMLInitializationEvent e) {
		proxy.init(e);
		ModEntities.init();
    }

	@EventHandler
    public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
    }

}


