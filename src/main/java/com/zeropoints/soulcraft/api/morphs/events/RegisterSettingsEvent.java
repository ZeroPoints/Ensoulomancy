package com.zeropoints.soulcraft.api.morphs.events;

import java.util.HashMap;
import java.util.Map;

import com.zeropoints.soulcraft.api.morphs.MorphSettings;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Register settings event
 * 
 * This event should be fired when Metamorph reloads morph settings 
 * (this happens once the server is starting up or when admin executes 
 * "/metamorph reload settings" command).
 * 
 * Use this event to add your own custom morph settings.  
 */
public class RegisterSettingsEvent extends Event {
    public Map<String, MorphSettings> settings = new HashMap<String, MorphSettings>();
}