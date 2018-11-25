package com.zeropoints.ensoulomancy.api.ghost;

import com.zeropoints.ensoulomancy.api.DefaultSettings;

import io.netty.buffer.ByteBuf;

/**
 * Ghost settings
 * 
 * An instance of this class is responsible for storing information about 
 * morph's configurable settings.
 */
public class GhostSettings extends DefaultSettings {
	
    public static final GhostSettings DEFAULT = new GhostSettings();

    /** Default not ghost */
    public boolean isGhost = false;
    
    /**
     * Health units which are going to be applied
     */
    public int health = 20;

    /**
     * Merge given morph settings with this settings 
     */
    public void merge(GhostSettings setting) {
    	this.isGhost = setting.isGhost;
        this.health = setting.health;
    }

    @Override
    public GhostSettings clone() {
        GhostSettings settings = new GhostSettings();
        settings.merge(this);
        return settings;
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeBoolean(this.isGhost);
        buf.writeInt(this.health);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	this.isGhost = buf.readBoolean();
        this.health = buf.readInt();
    }
    
}

