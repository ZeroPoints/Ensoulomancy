package com.zeropoints.ensoulomancy.api.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zeropoints.ensoulomancy.api.DefaultSettings;
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