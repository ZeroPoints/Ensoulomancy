package com.zeropoints.ensoulomancy.network.common;

import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketGhost implements IMessage {
	
    public GhostSettings settings;

    public PacketGhost() {}

    public PacketGhost(GhostSettings settings) {
        this.settings = settings;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	GhostSettings setting = new GhostSettings();
        setting.fromBytes(buf);
        this.settings = setting;
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	this.settings.toBytes(buf);
    }
}