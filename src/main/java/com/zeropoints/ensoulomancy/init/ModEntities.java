package com.zeropoints.ensoulomancy.init;


import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.profane.*;
import com.zeropoints.ensoulomancy.entity.hallowed.*;
import com.zeropoints.ensoulomancy.entity.ghost.*;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	//public static final List<Entity> ENTITIES = new ArrayList<Entity>();
	
	public static void init() {
		int id = 0;
		
		// Fake player corpse entity
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":player_corpse"), EntityIttanMomen.class, "PlayerCorpse", id++, Main.instance, 0, 1, false);
		
		// Hallowed
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":pixie"), EntityPixie.class, "Pixie", id++, Main.instance, 64, 1, true, 0xC1A2D0, 0xE5D5D9);
		// Profane
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":imp"), EntityImp.class, "Imp", id++, Main.instance, 64, 1, true, 0xF52A35, 0x589BCD);
		// Ghost
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":ittan_momen"), EntityIttanMomen.class, "IttanMomen", id++, Main.instance, 64, 1, true, 0xF9FAFA, 0xE41545);
	}
}
