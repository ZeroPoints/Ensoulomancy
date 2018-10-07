package com.zeropoints.ensoulomancy.blocks;

import java.util.Random;
import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.api.ghost.GuiSoulSleep;
import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulBed;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSoulBed extends BlockBed {
	
    public BlockSoulBed() {
        super();
        //ModBlocks.BLOCKS.add(this);
    }
    
     // Called when the block is right clicked by a player.
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    	facing = (EnumFacing)state.getValue(FACING);
    	
    	// If the block clicked isn't the head piece, change it to be so
        if (state.getValue(PART) != BlockBed.EnumPartType.HEAD) {
            pos = pos.offset(facing);
            state = world.getBlockState(pos);

            if (state.getBlock() != this) {
                return true;
            }
        }
    	
        EntityPlayer.SleepResult entityplayer$sleepresult = trySleep(pos, player, state);

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
        
        player.sendStatusMessage(new TextComponentTranslation(message, new Object[0]), true);
        return true;
    }
    
    /**
     * Custom method for spirit bed interactivity
     * This should only be called client-side
     */
    private EntityPlayer.SleepResult trySleep(BlockPos bedLocation, EntityPlayer player, IBlockState state){
    	
    	IGhost ghost = Ghost.getCapability(player);
    	
        if (ghost == null || player.isPlayerSleeping() || !player.isEntityAlive()) {
            return EntityPlayer.SleepResult.OTHER_PROBLEM;
        }
        
        if (((Boolean)state.getValue(OCCUPIED)).booleanValue()) {
            if (getPlayerInSoulBed(player.world, bedLocation) != null) {
            	return EntityPlayer.SleepResult.NOT_POSSIBLE_NOW;
            }

            state = state.withProperty(OCCUPIED, Boolean.valueOf(false));
            player.world.setBlockState(bedLocation, state, 4);
        }
		
    	if (player.isRiding()) {
        	player.dismountRidingEntity();
        }

	    displayGUI(bedLocation);
        
        return EntityPlayer.SleepResult.OK;
    }
    
    @SideOnly(Side.CLIENT)
    private void displayGUI(BlockPos bedLocation) {
    	Minecraft.getMinecraft().displayGuiScreen(new GuiSoulSleep(bedLocation));
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

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(PART) == BlockBed.EnumPartType.FOOT ? Items.AIR : ModItems.SOUL_BED;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            spawnAsEntity(worldIn, pos, new ItemStack(ModItems.SOUL_BED, 1));
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos blockpos = pos;

        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT) {
            blockpos = pos.offset((EnumFacing)state.getValue(FACING));
        }

        TileEntity tileentity = worldIn.getTileEntity(blockpos);
        return new ItemStack(ModItems.SOUL_BED, 1);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD && te instanceof TileEntitySoulBed) {
            spawnAsEntity(worldIn, pos, new ItemStack(ModItems.SOUL_BED, 1, 0));
        }
        //else {
        //    super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        //}
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySoulBed();
    }

    
}