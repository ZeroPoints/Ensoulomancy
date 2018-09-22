package com.zeropoints.ensoulomancy.proxy;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModEvents;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSubPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;


@Mod.EventBusSubscriber(net.minecraftforge.fml.relauncher.Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        
        //ModRenderers.register(); // client-side only
        OBJLoader.INSTANCE.addDomain(Reference.MOD_ID); // client-side only
    }
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		
		ModEvents.initClientEvents();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		
		/* Rendering stuff */
        this.substitutePlayerRenderers(Minecraft.getMinecraft().getRenderManager());
	}
	
	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}

	

    
    /**
     * Substitute default player renders to get the ability to render the hand.
     *
     * Please, kids, don't do that at home. This was made by an expert in
     * his field, so please, don't override skinMap the way I did. Don't break
     * the compatibility with this mod (already confirmed breaking while 
     * using Metamorph and Blockbuster together).
     */
    private void substitutePlayerRenderers(RenderManager manager) {
        Map<String, net.minecraft.client.renderer.entity.RenderPlayer> skins = null;

        // Iterate over all render manager fields and get access to skinMap 
        for (Field field : manager.getClass().getDeclaredFields()) {
            if (field.getType().equals(Map.class)) {
                field.setAccessible(true);

                try {
                    Map map = (Map)field.get(manager);
                    if (map.get("default") instanceof net.minecraft.client.renderer.entity.RenderPlayer) {
                        skins = map;
                        break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Replace player renderers with Blockbuster substitutes 
        if (skins != null) {
            RenderPlayer slim = skins.get("slim");
            RenderPlayer def = skins.get("default");

            skins.put("slim", new RenderSubPlayer(manager, slim, true));
            skins.put("default", new RenderSubPlayer(manager, def, false));

            Main.log(Level.INFO, "Skin map renderers were successfully replaced with Metamorph substitutes!");
        }
    }
    
    
    /**
     * Get game mode of a player 
     */
    public static GameType getGameMode(EntityPlayer player) {
        NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(player.getGameProfile().getId());
        return networkplayerinfo != null ? networkplayerinfo.getGameType() : GameType.CREATIVE;
    }
}

