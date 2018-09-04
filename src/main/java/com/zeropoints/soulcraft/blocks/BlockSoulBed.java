package com.zeropoints.soulcraft.blocks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.zeropoints.soulcraft.capabilities.ghost.Ghost;
import com.zeropoints.soulcraft.capabilities.ghost.IGhost;
import com.zeropoints.soulcraft.init.ModBlocks;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.tileentity.TileEntitySoulBed;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockSoulBed extends BlockBed {
	
    public BlockSoulBed() {
        super();
    }

    /**
     * Return just normal cloth type
     */
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.CLOTH;
    }

    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	
    	// If that player is currently a ghost, immeadiately deghost the player client and server-side
    	IGhost ghost = Ghost.getCapability(playerIn);
        if (ghost != null && ghost.isGhost()) {
        	ghost.deGhost(playerIn);
        	return true;
        }
    	
        // Don't make the server do anything until timer for sleep is up
        if (world.isRemote) {
            return true;
        }
        
        // If the block clicked isn't the head piece, change it to be so
        if (state.getValue(PART) != BlockSoulBed.EnumPartType.HEAD) {
            pos = pos.offset((EnumFacing)state.getValue(FACING));
            state = world.getBlockState(pos);

            if (state.getBlock() != this) {
                return true;
            }
        }

        // 
        EntityPlayer.SleepResult entityplayer$sleepresult = trySleep(pos, playerIn);

        // If the result is anything but OK, display a message to the player
        String message;
        switch(entityplayer$sleepresult) {
            case OK:
            	return true;
            case NOT_SAFE: // This shouldn't hit. Ignore hostile mobs
            	message = "tile.bed.notSafe";
            	break;
            case NOT_POSSIBLE_NOW:
            	message = "tile.bed.occupied";
            	break;
        	default:
        	//case TOO_FAR_AWAY: message = "tile.bed.tooFarAway";
        	//case OTHER_PROBLEM:
        	//case NOT_POSSIBLE_HERE:
        		message = "tile.bed.notPossibleHere";
            	break;
        }
            
        playerIn.sendStatusMessage(new TextComponentTranslation(message, new Object[0]), true);
        return true;
    }
    
    /**
     * Custom method for spirit bed interactivity
     * This should only be called client-side
     */
    private EntityPlayer.SleepResult trySleep(BlockPos bedLocation, EntityPlayer player){
    	
    	IGhost ghost = Ghost.getCapability(player);
    	
        if (ghost == null || player.isPlayerSleeping() || !player.isEntityAlive()) {
            return EntityPlayer.SleepResult.OTHER_PROBLEM;
        }
        
        EntityPlayer sleepingPlayer = getPlayerInSoulBed(player.world, bedLocation);
        if (sleepingPlayer != null) {
        	return EntityPlayer.SleepResult.NOT_POSSIBLE_NOW;
        }
		
    	if (player.isRiding()) {
        	player.dismountRidingEntity();
        }

    	// Not sure what this does. Will test
    	try {
    		ReflectionHelper.findMethod(EntityPlayer.class, "spawnShoulderEntities", "func_192030_dh").invoke(player);
		} 
    	catch (Exception e) {
			e.printStackTrace();
		}
        
        final IBlockState state = player.world.isBlockLoaded(bedLocation) ? player.world.getBlockState(bedLocation) : null;
        final boolean isBed = state != null && state.getBlock() == ModBlocks.SOUL_BED;
        final EnumFacing enumfacing = isBed && state.getBlock() instanceof BlockHorizontal ? (EnumFacing)state.getValue(BlockHorizontal.FACING) : null;
        
        float f1 = 0.5F;
        float f2 = 0.5F;
        
        if (enumfacing != null) {
            f1 = 0.5F + (float)enumfacing.getFrontOffsetX() * 0.4F;
            f2 = 0.5F + (float)enumfacing.getFrontOffsetZ() * 0.4F;            
            player.renderOffsetX = -1.8F * (float)enumfacing.getFrontOffsetX();
            player.renderOffsetZ = -1.8F * (float)enumfacing.getFrontOffsetZ();
        }
        
        player.setPosition(bedLocation.getX() + f1, bedLocation.getY() + 0.6875F, bedLocation.getZ() + f2);

        player.motionX = 0.0D;
        player.motionY = 0.0D;
        player.motionZ = 0.0D;
    	
    	ghost.sleep(bedLocation);
    	
        return EntityPlayer.SleepResult.OK;
    }
    
    /**
     * Create an AxisAlignedBB coordinates extended from a given BlockPos
     * TODO: This would be better in a helper
     * @param eX extend along x-axis from position
     * @param eY extend along y-axis from position
     * @param eZ extend along z-axis from position
     */
    private AxisAlignedBB extendFrom(BlockPos pos, int eX, int eY, int eZ) {
    	int x = pos.getX(), 
			y = pos.getY(), 
			z = pos.getZ();
    	return new AxisAlignedBB(x - eX, y - eY, z - eZ, x + eX, y + eY, z + eZ);
    }
    
    /**
     * Get the player currently sleeping in this bed.
     */
    @Nullable
    private EntityPlayer getPlayerInSoulBed(World worldIn, BlockPos pos) {
        for (EntityPlayer entityplayer : worldIn.playerEntities) {
            IGhost ghost = Ghost.getCapability(entityplayer);
            if (ghost.isGhost() && ghost.getBedPosition() == pos) {
            	return entityplayer;
            }
        }

        return null;
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        if (state.getValue(PART) == BlockSoulBed.EnumPartType.FOOT) {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
                worldIn.setBlockToAir(pos);
            }
        }
        else if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this) {
            if (!worldIn.isRemote) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
            }

            worldIn.setBlockToAir(pos);
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(PART) == BlockSoulBed.EnumPartType.FOOT ? Items.AIR : ModItems.SOUL_BED;
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getValue(PART) == BlockSoulBed.EnumPartType.HEAD) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            spawnAsEntity(worldIn, pos, new ItemStack(ModItems.SOUL_BED, 1));
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos blockpos = pos;

        if (state.getValue(PART) == BlockSoulBed.EnumPartType.FOOT) {
            blockpos = pos.offset((EnumFacing)state.getValue(FACING));
        }

        TileEntity tileentity = worldIn.getTileEntity(blockpos);
        return new ItemStack(ModItems.SOUL_BED, 1);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockSoulBed.EnumPartType.FOOT) {
            BlockPos blockpos = pos.offset((EnumFacing)state.getValue(FACING));

            if (worldIn.getBlockState(blockpos).getBlock() == this) {
                worldIn.setBlockToAir(blockpos);
            }
        }
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        if (state.getValue(PART) == BlockSoulBed.EnumPartType.HEAD && te instanceof TileEntitySoulBed) {
            TileEntitySoulBed tileentitybed = (TileEntitySoulBed)te;
            ItemStack itemstack = tileentitybed.getItemStack();
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
        return (meta & 8) > 0 
    		? this.getDefaultState().withProperty(PART, BlockSoulBed.EnumPartType.HEAD).withProperty(FACING, enumfacing).withProperty(OCCUPIED, Boolean.valueOf((meta & 4) > 0)) 
			: this.getDefaultState().withProperty(PART, BlockSoulBed.EnumPartType.FOOT).withProperty(FACING, enumfacing);
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (state.getValue(PART) == BlockSoulBed.EnumPartType.FOOT) {
            IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue(FACING)));

            if (iblockstate.getBlock() == this) {
                state = state.withProperty(OCCUPIED, iblockstate.getValue(OCCUPIED));
            }
        }

        return state;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();

        if (state.getValue(PART) == BlockSoulBed.EnumPartType.HEAD) {
            i |= 8;

            if (((Boolean)state.getValue(OCCUPIED)).booleanValue()) {
                i |= 4;
            }
        }

        return i;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySoulBed();
    }
}