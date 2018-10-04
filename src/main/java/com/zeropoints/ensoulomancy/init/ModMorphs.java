package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.metamorph.actions.Frostshot;

import mchorse.metamorph.api.MorphManager;

public class ModMorphs {

	public static void init() {

		MorphManager.INSTANCE.actions.put("frostshot", new Frostshot());
		
    }
	
}
