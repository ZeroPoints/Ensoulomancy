package com.zeropoints.ensoulomancy.blocks;

import com.zeropoints.ensoulomancy.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MysticalBlockPortal extends BlockPortal {

	

    public MysticalBlockPortal () {

        super();
        this.setBlockUnbreakable();
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(0.75F);
        

		setUnlocalizedName("mystical_block_portal");
		setRegistryName("mystical_block_portal");
		
		ModBlocks.BLOCKS.add(this);
		
    }
	
    
    

    @Override
    public boolean trySpawnPortal (World worldIn, BlockPos pos) {

        FrameBuilder frameBuilder = new FrameBuilder(worldIn, pos, EnumFacing.Axis.X);

        if (frameBuilder.isValid() && frameBuilder.portalBlockCount == 0) {

            frameBuilder.placePortalBlocks();
            return true;
        }

        else {

            frameBuilder = new FrameBuilder(worldIn, pos, EnumFacing.Axis.Z);

            if (frameBuilder.isValid() && frameBuilder.portalBlockCount == 0) {

                frameBuilder.placePortalBlocks();
                return true;
            }
        }

        return false;
    }

    
    
    
    
    
    
    
    
	    


    public static class FrameBuilder {
        private final World world;
        private final EnumFacing.Axis axis;
        private final EnumFacing rightDir;
        private final EnumFacing leftDir;
        private int portalBlockCount;
        private BlockPos bottomLeft;
        private int height;
        private int width;

        public FrameBuilder (World worldIn, BlockPos position, EnumFacing.Axis axis) {

            this.world = worldIn;
            this.axis = axis;

            if (axis == EnumFacing.Axis.X) {
                this.leftDir = EnumFacing.EAST;
                this.rightDir = EnumFacing.WEST;
            }
            else {
                this.leftDir = EnumFacing.NORTH;
                this.rightDir = EnumFacing.SOUTH;
            }

            for (final BlockPos blockpos = position; position.getY() > blockpos.getY() - 21 && position.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(position.down())); position = position.down()) {
                ;
            }

            final int i = this.getDistanceUntilEdge(position, this.leftDir) - 1;

            if (i >= 0) {
                this.bottomLeft = position.offset(this.leftDir, i);
                this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

                if (this.width < 2 || this.width > 21) {
                    this.bottomLeft = null;
                    this.width = 0;
                }
            }

            if (this.bottomLeft != null) {
                this.height = this.calculatePortalHeight();
            }
        }

        protected int getDistanceUntilEdge (BlockPos p_180120_1_, EnumFacing p_180120_2_) {

            int i;

            for (i = 0; i < 22; ++i) {
                final BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);

                if (!this.isEmptyBlock(this.world.getBlockState(blockpos)) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.OBSIDIAN) {
                    break;
                }
            }

            final Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
            return block == Blocks.OBSIDIAN ? i : 0;
        }

        public int getHeight () {

            return this.height;
        }

        public int getWidth () {

            return this.width;
        }

        protected int calculatePortalHeight () {

            label56 :

            for (this.height = 0; this.height < 21; ++this.height) {
                for (int i = 0; i < this.width; ++i) {
                    final BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                    Block block = this.world.getBlockState(blockpos).getBlock();

                    if (!this.isEmptyBlock(this.world.getBlockState(blockpos))) {
                        break label56;
                    }

                    if (block == Blocks.PORTAL) {
                        ++this.portalBlockCount;
                    }

                    if (i == 0) {
                        block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    }
                    else if (i == this.width - 1) {
                        block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    }
                }
            }

            for (int j = 0; j < this.width; ++j) {
                if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
                    this.height = 0;
                    break;
                }
            }

            if (this.height <= 21 && this.height >= 3) {
                return this.height;
            }
            else {
                this.bottomLeft = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        protected boolean isEmptyBlock (IBlockState blockIn) {

            return blockIn.getMaterial() == Material.AIR || blockIn.getBlock() == Blocks.FIRE || blockIn.getBlock() == Blocks.PORTAL;
        }

        public boolean isValid () {

            return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void placePortalBlocks () {

            for (int i = 0; i < this.width; ++i) {
                final BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

                for (int j = 0; j < this.height; ++j) {
                    this.world.setBlockState(blockpos.up(j), Blocks.PORTAL.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
                }
            }
        }
    }
    
}
