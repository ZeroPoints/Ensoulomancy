package com.zeropoints.ensoulomancy.init;


import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.profane.*;
import com.zeropoints.ensoulomancy.entity.hallowed.*;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	//public static final List<Entity> ENTITIES = new ArrayList<Entity>();
	
	public static void init() {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":pixie"), EntityPixie.class, "Pixie", -2, Main.instance, 64, 1, true, 0xC1A2D0, 0xE5D5D9);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":imp"), EntityImp.class, "Imp", -3, Main.instance, 64, 1, true, 0xF52A35, 0x589BCD);
	}
}
