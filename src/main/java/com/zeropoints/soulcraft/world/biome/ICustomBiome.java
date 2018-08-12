package com.zeropoints.soulcraft.world.biome;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public interface ICustomBiome {

	public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType, BlockPos pos);
	
	

	
	
	public IBlockState getBaseBlock();
	
	
	
	
}
