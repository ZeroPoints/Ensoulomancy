package com.zeropoints.soulcraft.world;

import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.world.biome.Biome;


public class HallowedBiome extends Biome {

	public HallowedBiome(BiomeProperties properties) {
		super(properties);


		this.setRegistryName("sc", "hallowed");
		
		
		this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(net.minecraft.entity.passive.EntityMooshroom.class, 1, 1, 2));

        this.spawnableMonsterList.add(new Biome.SpawnListEntry(net.minecraft.entity.monster.EntityStray.class, 80, 4, 4));
        
        
	}
	
	
	

}
