package com.zeropoints.ensoulomancy.blocks;

import com.zeropoints.ensoulomancy.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MysticalBlock extends Block {

	public MysticalBlock(Material materialIn) {
		super(materialIn);
		// TODO Auto-generated constructor stub

		setUnlocalizedName("mystical_block");
		setRegistryName("mystical_block");
		
		ModBlocks.BLOCKS.add(this);
		
	}

	
	
	@Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {

            return false;
        }

        final ItemStack stack = playerIn.getHeldItem(hand);
        final Item item = stack.getItem();

        ModBlocks.portal.trySpawnPortal(worldIn, pos.offset(facing));
        
        
        //if (item instanceof ItemSword || item instanceof ItemBow || Loader.isModLoaded("tconstruct") && TconUtils.isTconWeapon(item)) {

            //((BlockHuntingPortal) HuntingDimension.portal).trySpawnPortal(worldIn, pos.offset(facing));
        //}

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

	
	
	
}
