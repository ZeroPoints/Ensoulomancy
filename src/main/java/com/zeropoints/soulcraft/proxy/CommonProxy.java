package com.zeropoints.soulcraft.proxy;

import java.util.concurrent.Callable;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModBiomes;
import com.zeropoints.soulcraft.init.ModDimensions;
import com.zeropoints.soulcraft.init.ModEntities;
import com.zeropoints.soulcraft.init.ModEvents;
import com.zeropoints.soulcraft.init.ModGuiHandler;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.player.ISoulpool;
import com.zeropoints.soulcraft.player.PlayerData;
import com.zeropoints.soulcraft.player.PlayerDataFactory;
import com.zeropoints.soulcraft.player.Soulpool;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.util.text.translation.I18n;

/**
 * CommonProxy makes sure initialisation certain registry/events are triggered both client and server side.
 */
@Mod.EventBusSubscriber
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		
    }

    public void init(FMLInitializationEvent e) {
    	ModDimensions.init();
		ModBiomes.init();
		CapabilityManager.INSTANCE.register(ISoulpool.class, new PlayerData(), new PlayerDataFactory());
		
		ModEvents.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
    	
    }
    
    public void load(FMLInitializationEvent e) {
    	//ModEntities.init();
    }
    
    public void registerItemRenderer(Item item, int meta, String id) {	

    }
    
    public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}

}
