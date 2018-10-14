package com.zeropoints.ensoulomancy.proxy;

import java.io.File;



import com.zeropoints.ensoulomancy.Main;
//import com.zeropoints.ensoulomancy.api.morphs.MorphManager;
//import com.zeropoints.ensoulomancy.api.morphs.MorphUtils;
//import com.zeropoints.ensoulomancy.api.morphs.helpers.RegisterHandler;
//import com.zeropoints.ensoulomancy.api.morphs.models.ModelManager;
import com.zeropoints.ensoulomancy.capabilities.ghost.GhostFactory;
import com.zeropoints.ensoulomancy.capabilities.ghost.GhostStorage;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
//import com.zeropoints.ensoulomancy.capabilities.morphing.IMorphing;
//import com.zeropoints.ensoulomancy.capabilities.morphing.MobMorphFactory;
//import com.zeropoints.ensoulomancy.capabilities.morphing.Morphing;
//import com.zeropoints.ensoulomancy.capabilities.morphing.MorphingStorage;
//import com.zeropoints.ensoulomancy.capabilities.morphing.PlayerMorphFactory;
import com.zeropoints.ensoulomancy.capabilities.soulpool.ISoulpool;
import com.zeropoints.ensoulomancy.capabilities.soulpool.SoulpoolFactory;
import com.zeropoints.ensoulomancy.capabilities.soulpool.SoulpoolStorage;
import com.zeropoints.ensoulomancy.init.ModBiomes;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.init.ModEntities;
import com.zeropoints.ensoulomancy.init.ModEvents;
import com.zeropoints.ensoulomancy.init.ModGuiHandler;
import com.zeropoints.ensoulomancy.init.ModMorphs;
import com.zeropoints.ensoulomancy.network.Dispatcher;
import com.zeropoints.ensoulomancy.util.Config;
import com.zeropoints.ensoulomancy.world.gen.structure.WorldGenSpiritTemple;


import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * CommonProxy makes sure initialisation certain registry/events are triggered both client and server side.
 */
@Mod.EventBusSubscriber
public class CommonProxy {

    

	
	public void preInit(FMLPreInitializationEvent e) {

        new Config();
        
		Dispatcher.register();
    }


    public void init(FMLInitializationEvent e) {
    	ModEntities.init();
    	ModDimensions.init();
		ModBiomes.init();
		ModEvents.init();
		ModMorphs.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new ModGuiHandler());
		
		CapabilityManager.INSTANCE.register(ISoulpool.class, new SoulpoolStorage(), new SoulpoolFactory());
		CapabilityManager.INSTANCE.register(IGhost.class, new GhostStorage(), new GhostFactory());
		
    }

    public void postInit(FMLPostInitializationEvent e) {
    	
    }
    
    public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}

}
