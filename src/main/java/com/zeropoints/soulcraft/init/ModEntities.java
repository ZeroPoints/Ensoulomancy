package com.zeropoints.soulcraft.init;


import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.render.entity.mobs.EntityImp;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static void init() {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":imp"), EntityImp.class, "Imp", -2, Main.instance, 64, 1, true, 0xF52A35, 0x589BCD);
	}
}
