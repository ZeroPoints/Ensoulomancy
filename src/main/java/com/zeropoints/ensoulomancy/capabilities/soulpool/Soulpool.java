package com.zeropoints.ensoulomancy.capabilities.soulpool;

import net.minecraft.entity.player.EntityPlayer;

public class Soulpool implements ISoulpool {

	private int souls = 0; 
	
	public static ISoulpool getCapability(EntityPlayer player) {
		return player.getCapability(SoulpoolProvider.SOULPOOL_CAPABILITY, null);
	}
	
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
