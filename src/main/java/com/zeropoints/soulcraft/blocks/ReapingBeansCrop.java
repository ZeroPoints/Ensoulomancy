package com.zeropoints.soulcraft.blocks;


import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModBlocks;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.util.IHasModel;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


public class ReapingBeansCrop extends BlockCrops {

	// Outline to show when hovered over
	/*
	private static final AxisAlignedBB[] REAPING_BEANS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D)
			};
	*/
	
	public ReapingBeansCrop() {
		//super();
		
		setUnlocalizedName("reaping_beans_crop");
		setRegistryName("reaping_beans_crop");
		
		ModBlocks.BLOCKS.add(this);
	}
	
	/*
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return REAPING_BEANS_AABB[((Integer)state.getValue(AGE)).intValue()];
    }
    */
	
	@Override
	protected Item getSeed() {
		return ModItems.REAPING_SEEDS;
	}
	
	@Override
	protected Item getCrop() {
		return ModItems.REAPING_BEANS;
	}
	
	/*
	 * This does not actually change the block it can grow on. See @com.zeropoints.soulcraft.items.ReapingSeeds#getPlantType
	 */
	@Override
	protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.SOUL_SAND;
    }

	
}
