package com.zeropoints.ensoulomancy.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.blocks.BlockSoulBed;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntityItemSoulBedStackRenderer;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulBed;
import com.zeropoints.ensoulomancy.util.IHasModel;
import com.zeropoints.ensoulomancy.util.Reference;

public class ItemSoulBed extends Item implements IHasModel {
	
    private final String name = "soul_bed"; 

    public ItemSoulBed() {
    	super();
    	this.setRegistryName("item_" + name);
		this.setUnlocalizedName(name);
        this.setCreativeTab(Main.ENSOULOMANCY_TAB);
        this.setMaxDamage(0);
        
        this.setTileEntityItemStackRenderer(new TileEntityItemSoulBedStackRenderer());
        
		ModItems.ITEMS.add(this);
    }
    
    /**
     * Called when a Block is right-clicked with this Item
     * This essentially just makes sure the block can be placed here. 
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }
        
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        boolean canReplaceThis = block.isReplaceable(worldIn, pos);

        if (!canReplaceThis) {
            pos = pos.up();
        }

        int i = MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing enumfacing = EnumFacing.getHorizontal(i);
        BlockPos blockpos = pos.offset(enumfacing);
        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(blockpos, facing, itemstack)) {
            Block endBlock = worldIn.getBlockState(blockpos).getBlock();
            boolean canPlaceEnd = endBlock.isReplaceable(worldIn, blockpos);

            if (canPlaceEnd && worldIn.getBlockState(pos.down()).isTopSolid() && worldIn.getBlockState(blockpos.down()).isTopSolid()) {
                IBlockState iblockstate2 = ModBlocks.SOUL_BED.getDefaultState().withProperty(BlockSoulBed.OCCUPIED, Boolean.valueOf(false)).withProperty(BlockSoulBed.FACING, enumfacing).withProperty(BlockSoulBed.PART, BlockSoulBed.EnumPartType.FOOT);
                worldIn.setBlockState(pos, iblockstate2, 10);
                worldIn.setBlockState(blockpos, iblockstate2.withProperty(BlockSoulBed.PART, BlockSoulBed.EnumPartType.HEAD), 10);
                
                SoundType soundtype = iblockstate2.getBlock().getSoundType(iblockstate2, worldIn, pos, player);
                worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                
                worldIn.notifyNeighborsRespectDebug(pos, block, false);
                worldIn.notifyNeighborsRespectDebug(blockpos, endBlock, false);

                if (player instanceof EntityPlayerMP) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                }

                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

	@Override
	public void registerModels() {
		ModRenderers.registerItemRenderer(this, 0, Reference.MOD_ID + ":" + name + "#inventory");
	}

}
