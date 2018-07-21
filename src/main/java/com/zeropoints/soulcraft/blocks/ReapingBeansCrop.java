package com.zeropoints.soulcraft.blocks;


import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModBlocks;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.util.IHasModel;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;


public class ReapingBeansCrop extends BlockCrops {

	public ReapingBeansCrop() {
		//this wasnt in tute but i thought i would try it
		//super();
		
		setUnlocalizedName("reapingbeanscrop");
		setRegistryName("reapingbeanscrop");
		
		ModBlocks.BLOCKS.add(this);
	}
	

	@Override
	protected Item getSeed() {
		return ModItems.REAPINGSEEDS;
	}
	
	@Override
	protected Item getCrop() {
		return ModItems.REAPINGBEANS;
	}

	
}
