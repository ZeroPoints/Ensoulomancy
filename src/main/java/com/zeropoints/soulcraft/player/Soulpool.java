package com.zeropoints.soulcraft.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class Soulpool implements ISoulpool {

	private int souls = 0; 

	
	
	@Override
	public boolean subtract(int value) {
		if(value < 0 || (souls - value) < 0) {
			return false;
		}
		
		souls -= value;
		return true;
	
		
	}

	@Override
	public boolean add(int value) {
		if(value < 0) {
			return false;
		}
		souls += value;
		return true;
	}

	@Override
	public boolean set(int value) {
		if(value < 0) {
			return false;
		}
		souls += value;
		return true;
	}

	@Override
	public int get() {
		return souls;
	}

	
	
	
	
}
