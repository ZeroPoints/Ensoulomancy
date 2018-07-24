package com.zeropoints.soulcraft.world;

import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.world.biome.Biome;


public class StyxBiome extends Biome {

	public StyxBiome(BiomeProperties properties) {
		super(properties);

		

		this.setRegistryName("sc", "styx");
		
		this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPolarBear.class, 1, 1, 2));

        this.spawnableMonsterList.add(new Biome.SpawnListEntry(net.minecraft.entity.monster.EntityGhast.class, 80, 4, 4));
        
        
	}
	
	
	

}
