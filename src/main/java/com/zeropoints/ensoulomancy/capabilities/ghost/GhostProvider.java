package com.zeropoints.ensoulomancy.capabilities.ghost;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class GhostProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IGhost.class) 
	public static final Capability<IGhost> GHOST_CAPABILITY = null; 

	private IGhost instance = GHOST_CAPABILITY.getDefaultInstance(); 

	
	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == GHOST_CAPABILITY; 
	} 

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)  { 
		return capability == GHOST_CAPABILITY ? GHOST_CAPABILITY.<T> cast(this.instance) : null; 
	} 

	@Override 
	public NBTBase serializeNBT() { 
		return GHOST_CAPABILITY.getStorage().writeNBT(GHOST_CAPABILITY, this.instance, null); 
	} 

	@Override 
	public void deserializeNBT(NBTBase nbt) { 
		GHOST_CAPABILITY.getStorage().readNBT(GHOST_CAPABILITY, this.instance, null, nbt); 
	} 
	
}


