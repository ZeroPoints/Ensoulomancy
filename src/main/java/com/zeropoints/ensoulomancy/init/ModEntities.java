package com.zeropoints.ensoulomancy.init;


import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.render.entity.mobs.EntityImp;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static void init() {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":imp"), EntityImp.class, "Imp", -2, Main.instance, 64, 1, true, 0xF52A35, 0x589BCD);
	}
}
