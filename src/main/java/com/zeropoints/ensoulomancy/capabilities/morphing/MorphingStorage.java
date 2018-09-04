package com.zeropoints.ensoulomancy.capabilities.morphing;

import java.util.ArrayList;
import java.util.List;

import com.zeropoints.ensoulomancy.api.morphs.AbstractMorph;
import com.zeropoints.ensoulomancy.api.morphs.MorphManager;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * Morphing storage
 *
 * This class is responsible for saving IMorphing capability to... Hey, Houston,
 * where these data are getting saved? Basically, I don't know.
 *
 * Further research in Minecraft sources shows that capabilities are stored
 * in target's NBT (i.e. ItemStack's, TE's or Entity's NBT) in field "ForgeCaps."
 */
public class MorphingStorage implements IStorage<IMorphing> {
	
    @Override
    public NBTBase writeNBT(Capability<IMorphing> capability, IMorphing instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("lastHealthRatio", new NBTTagFloat(instance.getLastHealthRatio()));

        if (instance.getCurrentMorph() != null) {
            NBTTagCompound morph = new NBTTagCompound();
            instance.getCurrentMorph().toNBT(morph);

            tag.setTag("Morph", morph);
        }

        return tag;
    }

    @Override
    public void readNBT(Capability<IMorphing> capability, IMorphing instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            NBTTagCompound tag = (NBTTagCompound) nbt;
            NBTTagCompound morphTag = tag.getCompoundTag("Morph");
            instance.setLastHealthRatio(tag.getFloat("lastHealthRatio"));

            if (!tag.hasNoTags()) {
                instance.setCurrentMorph(MorphManager.INSTANCE.morphFromNBT(morphTag), null);
            }
        }
    }
    
}
