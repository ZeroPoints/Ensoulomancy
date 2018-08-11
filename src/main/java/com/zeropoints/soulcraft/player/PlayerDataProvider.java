package com.zeropoints.soulcraft.player;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;




public class PlayerDataProvider implements ICapabilitySerializable<NBTBase> {

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


