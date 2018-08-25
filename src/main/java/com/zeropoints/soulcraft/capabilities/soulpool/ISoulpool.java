package com.zeropoints.soulcraft.capabilities.soulpool;


public interface ISoulpool { 
	
	/**
	 * Subtracts value from soul pool.
	 * returns false if it cant consume enough or if you provide a negative value
	 */
	public boolean subtract(int value);
	
	/**
	 * Add value to soulpool
	 * returns false if hits upper limit 
	 */
	public boolean add(int value);

	/**
	 * Set value of soulpool
	 */
	public boolean set(int value);
	
	/**
	 * Returns current value of soulpool
	 */
	public int get();
	
}
