package com.zeropoints.ensoulomancy.capabilities.ghost;

import com.zeropoints.ensoulomancy.api.ghost.GhostSettings;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * Ghost storage
 * 
 * Save NBT data for this capability
 */
public class GhostStorage implements IStorage<IGhost>  {
	
	@Override 
	public NBTBase writeNBT(Capability<IGhost> capability, IGhost instance, EnumFacing side) { 
		GhostSettings settings = instance.getSettings();
		
		NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("health", new NBTTagInt(settings.health));
        tag.setTag("isGhost", new NBTTagByte((byte)(settings.isGhost ? 1 : 0)));

        return tag;
	} 

	@Override 
	public void readNBT(Capability<IGhost> capability, IGhost instance, EnumFacing side, NBTBase nbt) {
		if (nbt instanceof NBTTagCompound) {
            NBTTagCompound tag = (NBTTagCompound) nbt;
            
            GhostSettings settings = new GhostSettings();
            settings.health = tag.getInteger("health");
            settings.isGhost = tag.getBoolean("isGhost");
            
            instance.setSettings(settings);

            //if (!tag.hasNoTags()) {}
        }
	} 
	
}


