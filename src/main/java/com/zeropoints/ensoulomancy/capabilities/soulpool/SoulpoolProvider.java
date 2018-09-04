package com.zeropoints.ensoulomancy.capabilities.soulpool;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SoulpoolProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(ISoulpool.class) 
	public static final Capability<ISoulpool> SOULPOOL_CAPABILITY = null; 

	private ISoulpool instance = SOULPOOL_CAPABILITY.getDefaultInstance(); 

	
	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == SOULPOOL_CAPABILITY; 
	} 

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)  { 
		return capability == SOULPOOL_CAPABILITY ? SOULPOOL_CAPABILITY.<T> cast(this.instance) : null; 
	} 

	@Override 
	public NBTBase serializeNBT() { 
		return SOULPOOL_CAPABILITY.getStorage().writeNBT(SOULPOOL_CAPABILITY, this.instance, null); 
	} 

	@Override 
	public void deserializeNBT(NBTBase nbt) { 
		SOULPOOL_CAPABILITY.getStorage().readNBT(SOULPOOL_CAPABILITY, this.instance, null, nbt); 
	} 
	
}


