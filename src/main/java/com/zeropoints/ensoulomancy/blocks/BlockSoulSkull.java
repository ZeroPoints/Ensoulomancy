package com.zeropoints.ensoulomancy.blocks;

import java.util.Random;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockSoulSkull extends BlockContainer {
	
    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final PropertyBool NODROP = PropertyBool.create("nodrop");
    protected static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.5D, 0.75D, 0.75D, 1.0D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 0.5D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.25D, 0.5D, 0.75D, 0.75D);

    public BlockSoulSkull() {
        super(Material.CIRCUITS);
        this.setDefaultState(this.blockState.getBaseState()
    		.withProperty(FACING, EnumFacing.NORTH)
    		.withProperty(NODROP, Boolean.valueOf(false)));
        
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(Main.ENSOULOMANCY_TAB);
        
        this.setUnlocalizedName("soul_skull");
    	this.setRegistryName("soul_skull"); 
    	
    	ModBlocks.BLOCKS.add(this);
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch ((EnumFacing)state.getValue(FACING)) {
            case UP: default: return DEFAULT_AABB;
            case NORTH: return NORTH_AABB;
            case SOUTH: return SOUTH_AABB;
            case WEST: 	return WEST_AABB;
            case EAST: 	return EAST_AABB;
        }
    }
    
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(NODROP, Boolean.valueOf(false));
    }
    
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode) {
            state = state.withProperty(NODROP, Boolean.valueOf(true));
            worldIn.setBlockState(pos, state, 4);
        }
        this.dropBlockAsItem(worldIn, pos, state, 0);

        super.onBlockHarvested(worldIn, pos, state, player);
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(NODROP, Boolean.valueOf((meta & 8) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();

        if (((Boolean)state.getValue(NODROP)).booleanValue()) {
            i |= 8;
        }

        return i;
    }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, NODROP});
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
    

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySoulSkull();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        int i = 0;
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntitySoulSkull) {
            i = ((TileEntitySoulSkull)tileentity).getSkullType();
        }

        return new ItemStack(ModItems.SOUL_SKULL, 1, i);
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess worldIn, BlockPos pos, IBlockState state, int fortune) {
        if (!((Boolean)state.getValue(NODROP)).booleanValue()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntitySoulSkull) {
                TileEntitySoulSkull tileentityskull = (TileEntitySoulSkull)tileentity;
                ItemStack itemstack = new ItemStack(ModItems.SOUL_SKULL, 1, tileentityskull.getSkullType());
                drops.add(itemstack);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.SOUL_SKULL;
    }

    //@Override
    public boolean canDispenserPlace(World worldIn, BlockPos pos, ItemStack stack) {
        return false;
    }
}