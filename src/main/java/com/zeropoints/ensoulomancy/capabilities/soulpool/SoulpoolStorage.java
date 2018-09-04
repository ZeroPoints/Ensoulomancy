package com.zeropoints.ensoulomancy.capabilities.soulpool;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


public class SoulpoolStorage implements IStorage<ISoulpool>  {
	
	@Override 
	public NBTBase writeNBT(Capability<ISoulpool> capability, ISoulpool instance, EnumFacing side) { 
		return new NBTTagInt(instance.get()); 
	} 

	@Override 
	public void readNBT(Capability<ISoulpool> capability, ISoulpool instance, EnumFacing side, NBTBase nbt)	{ 
		instance.set(((NBTPrimitive) nbt).getInt()); 
	} 
	
}


