package com.zeropoints.ensoulomancy.init;


import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.profane.*;
import com.zeropoints.ensoulomancy.entity.hallowed.*;
import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.entity.EntityPlayerCorpse;
import com.zeropoints.ensoulomancy.entity.action.EntityFrostshot;
import com.zeropoints.ensoulomancy.entity.action.EntityLightningBolt;
import com.zeropoints.ensoulomancy.entity.ghost.*;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	//public static final List<Entity> ENTITIES = new ArrayList<Entity>();
	
	public static void init() {
		int id = 0;
		
		// Fake player corpse entity
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":player_corpse"), EntityPlayerCorpse.class, "PlayerCorpse", id++, Main.instance, 0, 1, false);
		// Husk
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":husk"), EntityHusk.class, "Husky", id++, Main.instance, 64, 1, true);
		
		// Hallowed
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":pixie"), EntityPixie.class, "Pixie", id++, Main.instance, 64, 1, true, 0xC1A2D0, 0xE5D5D9);
		// Profane
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":imp"), EntityImp.class, "Imp", id++, Main.instance, 64, 1, true, 0xF52A35, 0x589BCD);
		// Ghost
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":ittan_momen"), EntityIttanMomen.class, "IttanMomen", id++, Main.instance, 64, 1, true, 0xF9FAFA, 0xE41545);
		

		// Action
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":textures/items/soul_essence.png"), EntityFrostshot.class, "Frostshot", id++, Main.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":textures/items/spirit_guide.png"), EntityLightningBolt.class, "LightningBolt", id++, Main.instance, 64, 1, true);
	}
}
