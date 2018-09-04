
package com.zeropoints.ensoulomancy.items;


import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;


public class SoulSeedsStem extends Item implements IHasModel, IPlantable { 

	public SoulSeedsStem() {
		super();
		
		setUnlocalizedName("soul_seeds_stem");
		setRegistryName("soul_seeds_stem");
		
		setCreativeTab(Main.ENSOULOMANCY_TAB);
		
		ModItems.ITEMS.add(this);	
	}
	
	 
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "soul_seeds_stem");
	}


	//Had to override this with pretty much same code as in itemseed class...
	//ALL BECAUSE MODBLOCK.CORN_CROP isn't defined when if i use super(modblock.corn_crop)
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		IBlockState state = worldIn.getBlockState(pos);		
		if(facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && 
				state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up())) {
			worldIn.setBlockState(pos.up(), ModBlocks.SOUL_ESSENCE_STEM.getDefaultState());
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
    }

	
    @Override
    public net.minecraft.block.state.IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return ModBlocks.SOUL_ESSENCE_STEM.getDefaultState();
    }
	
    /**
     * Set the crop to be able to grow on soul sand as a 'nether' type crop. 
     */
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Nether;
	}
	
	/*
	 * Able to make custom item interchangeable with this in crafting recipe
	 * Left as reference
	public void initOreDict() {
		OreDictionary.registerOre("ruby", this);
	}
	*/

	
}

