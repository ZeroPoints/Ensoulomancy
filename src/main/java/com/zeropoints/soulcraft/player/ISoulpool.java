package com.zeropoints.soulcraft.player;


public interface ISoulpool { 

	
	
	/*
	 * Subtracts value from soul pool.
	 * returns false if it cant consume enough or if you provide a negative value
	 */
	public boolean subtract(int value);
	
	public boolean add(int value);

	public boolean set(int value);
	
	public int get();
	
	
	
}
