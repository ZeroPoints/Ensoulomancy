package com.zeropoints.ensoulomancy.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.zeropoints.ensoulomancy.api.morphs.abilities.IAbility;
//import com.zeropoints.ensoulomancy.api.morphs.abilities.IAction;
//import com.zeropoints.ensoulomancy.api.morphs.abilities.IAttackAbility;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Morph settings
 * 
 * An instance of this class is responsible for storing information about 
 * morph's configurable settings.
 */
public class DefaultSettings  {
	
    public static final DefaultSettings DEFAULT = new DefaultSettings();

    public void merge(DefaultSettings setting) {}

    public DefaultSettings clone() {
    	return this.DEFAULT;
    }

    /**
     * Write settings to the network buffer
     */
    public void toBytes(ByteBuf buf) {}

    /**
     * Read settings from the network buffer 
     */
    public void fromBytes(ByteBuf buf) {}

    /**
     * Get key of given value in given map 
     */
    public static <T> String getKey(Map<String, T> map, T value) {
        if (value == null) {
            return null;
        }

        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }

        return null;
    }
    
}