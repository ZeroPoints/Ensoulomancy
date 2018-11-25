package com.zeropoints.ensoulomancy.network.common;

import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Packet that sents settings 
 */
public class PacketSettings implements IMessage {
	
    public GhostSettings ghostSettings = null;

    public PacketSettings() {}

    public PacketSettings(GhostSettings ghost) {
        
    	this.ghostSettings = ghost;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        

    	
        GhostSettings setting = new GhostSettings();
        setting.fromBytes(buf);
        this.ghostSettings = setting;
    }

    @Override
    public void toBytes(ByteBuf buf) {
       
        this.ghostSettings.toBytes(buf);
    }
}