package com.zeropoints.soulcraft.world;

import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.world.biome.Biome;


public class ProfaneBiome extends Biome {

	public ProfaneBiome(BiomeProperties properties) {
		super(properties);

		this.setRegistryName("sc", "profane");


		this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(net.minecraft.entity.passive.EntitySkeletonHorse.class, 1, 1, 2));

        this.spawnableMonsterList.add(new Biome.SpawnListEntry(net.minecraft.entity.monster.EntityPigZombie.class, 80, 4, 4));
        
        
	}
	
	
	

}
