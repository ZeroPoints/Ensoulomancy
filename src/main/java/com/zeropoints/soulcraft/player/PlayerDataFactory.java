

package com.zeropoints.soulcraft.player;
 

import java.util.concurrent.Callable;

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



public class PlayerDataFactory implements Callable<ISoulpool> {

  @Override
  public ISoulpool call() throws Exception {
	  Main.LogMesssage("factory", "new");
    return new Soulpool();
  }
}


	


