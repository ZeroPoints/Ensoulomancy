package com.zeropoints.ensoulomancy.capabilities.morphing;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Morhping capability provider
 *
 * Now that I understand capability system, it seems pretty easy to use!
 */
public class MorphingProvider implements ICapabilitySerializable<NBTBase> {
	
    @CapabilityInject(IMorphing.class)
    public static final Capability<IMorphing> MORPHING_CAPABILITY = null;

    private IMorphing instance = MORPHING_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == MORPHING_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == MORPHING_CAPABILITY ? MORPHING_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return MORPHING_CAPABILITY.getStorage().writeNBT(MORPHING_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        MORPHING_CAPABILITY.getStorage().readNBT(MORPHING_CAPABILITY, this.instance, null, nbt);
    }
}