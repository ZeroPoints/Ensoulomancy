package com.zeropoints.ensoulomancy.network.common;

import com.zeropoints.ensoulomancy.api.morphs.AbstractMorph;
import com.zeropoints.ensoulomancy.api.morphs.MorphManager;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMorphPlayer implements IMessage {
	
    public int id;
    public AbstractMorph morph;

    public PacketMorphPlayer() {}

    public PacketMorphPlayer(int id, AbstractMorph morph) {
        this.id = id;
        this.morph = morph;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();

        if (buf.readBoolean()) {
            this.morph = MorphManager.INSTANCE.morphFromNBT(ByteBufUtils.readTag(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeBoolean(this.morph != null);

        if (this.morph != null) {
            NBTTagCompound tag = new NBTTagCompound();

            this.morph.toNBT(tag);
            ByteBufUtils.writeTag(buf, tag);
        }
    }
}