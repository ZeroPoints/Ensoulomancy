package com.zeropoints.ensoulomancy.proxy;

import java.io.File;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.api.morphs.MorphManager;
import com.zeropoints.ensoulomancy.api.morphs.MorphUtils;
import com.zeropoints.ensoulomancy.api.morphs.helpers.RegisterHandler;
import com.zeropoints.ensoulomancy.api.morphs.models.ModelManager;
import com.zeropoints.ensoulomancy.capabilities.ghost.GhostFactory;
import com.zeropoints.ensoulomancy.capabilities.ghost.GhostStorage;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
import com.zeropoints.ensoulomancy.capabilities.morphing.IMorphing;
import com.zeropoints.ensoulomancy.capabilities.morphing.MobMorphFactory;
import com.zeropoints.ensoulomancy.capabilities.morphing.Morphing;
import com.zeropoints.ensoulomancy.capabilities.morphing.MorphingStorage;
import com.zeropoints.ensoulomancy.capabilities.morphing.PlayerMorphFactory;
import com.zeropoints.ensoulomancy.capabilities.soulpool.ISoulpool;
import com.zeropoints.ensoulomancy.capabilities.soulpool.SoulpoolFactory;
import com.zeropoints.ensoulomancy.capabilities.soulpool.SoulpoolStorage;
import com.zeropoints.ensoulomancy.init.ModBiomes;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.init.ModEntities;
import com.zeropoints.ensoulomancy.init.ModEvents;
import com.zeropoints.ensoulomancy.network.Dispatcher;
import com.zeropoints.ensoulomancy.util.Reference;
import com.zeropoints.ensoulomancy.util.EnsoulomancyConfig;

import net.minecraft.item.Item;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.text.translation.I18n;

/**
 * CommonProxy makes sure initialisation certain registry/events are triggered both client and server side.
 */
@Mod.EventBusSubscriber
public class CommonProxy {

	/**
     * Model handler. This class is responsible for managing models and more. 
     */
    public ModelManager models = new ModelManager();
    
	/**
     * config filled with cool configuration points
     */
    public EnsoulomancyConfig config;
    
    /**
     * Location of a user morph settings
     */
    public File morphs;
    
    /**
     * Forge config
     */
    public Configuration forge;
    
    
	
	public void preInit(FMLPreInitializationEvent e) {
		Dispatcher.register();
    	
    	/* Attaching model manager and morph factories to the morph manager */
    	MorphManager.INSTANCE.models = this.models;
    	MorphManager.INSTANCE.factories.add(new MobMorphFactory());
    	//MorphManager.INSTANCE.factories.add(new PlayerMorphFactory()); // Ignore player morphing for now. Future feature
    
    	/* Configuration */
    	File config = new File(e.getModConfigurationDirectory(), "ensoulomancy/config.cfg");
    	File morphs = new File(e.getModConfigurationDirectory(), "ensoulomancy/morphs.json");
    	
    	this.forge = new Configuration(config);
    	this.config = new EnsoulomancyConfig(this.forge);
    	this.morphs = morphs;
    }


    public void init(FMLInitializationEvent e) {
    	ModEntities.init();
    	ModDimensions.init();
		ModBiomes.init();
		ModEvents.init();
		
		CapabilityManager.INSTANCE.register(ISoulpool.class, new SoulpoolStorage(), new SoulpoolFactory());
		CapabilityManager.INSTANCE.register(IMorphing.class, new MorphingStorage(), Morphing.class);
		CapabilityManager.INSTANCE.register(IGhost.class, new GhostStorage(), new GhostFactory());
		
    	/* Register factories */
    	RegisterHandler.registerAbilities(MorphManager.INSTANCE);
    	MorphManager.INSTANCE.register();
    	
    	/* User configurations */
    	if(!morphs.exists()) {
    		MorphUtils.generateFile(morphs, "{}");
    	}
    }

    public void postInit(FMLPostInitializationEvent e) {
    	
    }
    
    public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}

}
