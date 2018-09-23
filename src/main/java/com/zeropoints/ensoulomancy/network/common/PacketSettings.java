package com.zeropoints.ensoulomancy.network.common;

import java.util.HashMap;
import java.util.Map;

import com.zeropoints.ensoulomancy.api.DefaultSettings;
import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;
//import com.zeropoints.ensoulomancy.api.morphs.MorphSettings;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Packet that sents settings 
 */
public class PacketSettings implements IMessage {
	
    //public Map<String, MorphSettings> morphSettings = new HashMap<String, MorphSettings>();
    public GhostSettings ghostSettings = null;

    public PacketSettings() {}

    //Map<String, MorphSettings> morph, 
    public PacketSettings(GhostSettings ghost) {
        //this.morphSettings.clear();
        //this.morphSettings.putAll(morph);
        
    	this.ghostSettings = ghost;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        /*
    	for (int i = 0, c = buf.readInt(); i < c; i++) {
            String key = ByteBufUtils.readUTF8String(buf);
            //MorphSettings setting = new MorphSettings();
            //setting.fromBytes(buf);
            //this.morphSettings.put(key, setting);
        }
        */
    	
        GhostSettings setting = new GhostSettings();
        setting.fromBytes(buf);
        this.ghostSettings = setting;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        /*
  		buf.writeInt(this.morphSettings.size());
         
        for (Map.Entry<String, MorphSettings> setting : this.morphSettings.entrySet()) {
            ByteBufUtils.writeUTF8String(buf, setting.getKey());
            setting.getValue().toBytes(buf);
        }
        */
        this.ghostSettings.toBytes(buf);
    }
}