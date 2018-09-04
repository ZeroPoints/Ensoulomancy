package com.zeropoints.ensoulomancy.blocks;


import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;
import com.zeropoints.ensoulomancy.util.IHasModel;
import com.zeropoints.ensoulomancy.world.PurgatoryTeleporter;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
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



public class SoulEssenceStem extends BlockStem {

	// Outline to show when hovered over
	/*
	private static final AxisAlignedBB[] REAPING_BEANS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D)
			};
	*/
	
	public SoulEssenceStem() {
		super(Blocks.SOUL_SAND);
		
		setUnlocalizedName("soul_essence_stem");
		setRegistryName("soul_essence_stem");
		
		ModBlocks.BLOCKS.add(this);
	}
	
	/*
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return REAPING_BEANS_AABB[((Integer)state.getValue(AGE)).intValue()];
    }
    */
	/*
	@Override
	protected Item getSeed() {
		return ModItems.SOUL_SEEDS2;
	}
	
	@Override
	protected Item getCrop() {
		return ModItems.SOUL_ESSENCE2;
	}*/
	
	/*
	 * This does not actually change the block it can grow on. See @com.zeropoints.ensoulomancy.items.ReapingSeeds#getPlantType
	 */
	@Override
	protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.SOUL_SAND;
    }

	
	
	
	
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		
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


	}

	
	
	
	
	
	
	
}
