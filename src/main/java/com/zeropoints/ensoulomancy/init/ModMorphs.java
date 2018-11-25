package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.metamorph.actions.Frostshot;
import com.zeropoints.ensoulomancy.metamorph.actions.LightningBolt;

import mchorse.metamorph.api.MorphManager;

public class ModMorphs {

	public static void init() {

		MorphManager.INSTANCE.actions.put("frostshot", new Frostshot());
		MorphManager.INSTANCE.actions.put("lightningbolt", new LightningBolt());
    }
	
	
	
	
}






