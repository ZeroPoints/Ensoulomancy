package com.zeropoints.ensoulomancy.util;

public interface IHasModel {

	/**
	 * Method responsible for registering and applying model renderers to this item / block
	 * This is automatically called for each IHasModel instance in the init/ModRenderers class
	 */
	public void registerModels();	
	
}
