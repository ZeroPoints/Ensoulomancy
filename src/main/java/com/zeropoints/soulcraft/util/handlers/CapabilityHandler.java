package com.zeropoints.soulcraft.util.handlers;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.player.PlayerDataProvider;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class CapabilityHandler
{
    public static final ResourceLocation SOULPOOL_CAPABILITY = new ResourceLocation(Reference.MOD_ID, "_soulpool_capability");

    @SubscribeEvent
    public void addCapability(AttachCapabilitiesEvent<Entity> event)
    {
    	
    	if (!(event.getObject() instanceof EntityPlayer)) return;
    	
        event.addCapability(SOULPOOL_CAPABILITY, new PlayerDataProvider());
    }
}

