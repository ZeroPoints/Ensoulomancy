package com.zeropoints.ensoulomancy.api;

import java.util.Map;


import io.netty.buffer.ByteBuf;

/**
 * Base settings class
 * 
 * An instance of this class is responsible for storing information about shit
 * 
 * Copied from metamorphs class.
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