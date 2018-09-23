package com.zeropoints.ensoulomancy.blocks;


import java.util.Random;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;
import com.zeropoints.ensoulomancy.util.IHasModel;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;



public class SoulEssenceCrop extends BlockCrops {

	// Outline to show when hovered over
	/*
	private static final AxisAlignedBB[] REAPING_BEANS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D)
			};
	*/
	
	public SoulEssenceCrop() {
		//super();
		
		setUnlocalizedName("soul_essence_crop");
		setRegistryName("soul_essence_crop");
		
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
		return ModItems.SOUL_SEEDS;
	}
	
	@Override
	protected Item getCrop() {
		return ModItems.SOUL_ESSENCE;
	}
	
	
	
	/*
	 * This does not actually change the block it can grow on. See @com.zeropoints.ensoulomancy.items.ReapingSeeds#getPlantType
	 */
	@Override
	protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.SOUL_SAND;
    }

	
	
	
	
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		/*
		if (world.isRemote) {
			return;
		}
		
		//Allow player to teleport back through same means of transportation
		int dim = entity.dimension;
		if(entity.dimension == 0) {
			dim = ConfigurationHandler.dimensionId;;
		
		}
		else {
			dim = 0;
		}
		

		//Not sure if this is needed
		//entity.timeUntilPortal = 10;
		
		PurgatoryTeleporter.teleportToDimension(entity, dim, entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ());

*/
	}
	
	
	
	
	
	
	
}
