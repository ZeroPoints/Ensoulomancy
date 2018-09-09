package com.zeropoints.ensoulomancy.entity.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIWanderWalkingOrFlying extends EntityAIBase {
	
    protected final EntityLiving creature;
    protected final float probability = 0.001F;
    protected double speed;
    
    private static final Random random = new Random();

    public EntityAIWanderWalkingOrFlying(EntityLiving creature, double speedIn) {
        this.creature = creature;
        this.speed = speedIn;
        this.setMutexBits(1);
    }
    
    @Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		return false;
	}
    
    /*
    @Nullable
    protected Vec3d getPosition() {
        Vec3d vec3d = null;

        if (this.creature.isInWater() || this.creature.isOverWater()) {
            vec3d = RandomPositionGenerator.getLandPos(this.creature, 15, 15);
        }

        if (this.creature.getRNG().nextFloat() >= this.probability) {
            vec3d = this.getTreePos();
        }

        return vec3d == null ? super.getPosition() : vec3d;
    }

    @Nullable
    private Vec3d getTreePos() {
        BlockPos blockpos = new BlockPos(this.entity);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
        Iterable<BlockPos.MutableBlockPos> iterable = BlockPos.MutableBlockPos.getAllInBoxMutable(MathHelper.floor(this.entity.posX - 3.0D), MathHelper.floor(this.entity.posY - 6.0D), MathHelper.floor(this.entity.posZ - 3.0D), MathHelper.floor(this.entity.posX + 3.0D), MathHelper.floor(this.entity.posY + 6.0D), MathHelper.floor(this.entity.posZ + 3.0D));
        Iterator iterator = iterable.iterator();
        BlockPos blockpos1;

        while (true) {
            if (!iterator.hasNext()) {
                return null;
            }

            blockpos1 = (BlockPos)iterator.next();

            if (!blockpos.equals(blockpos1)) {
                Block block = this.entity.world.getBlockState(blockpos$mutableblockpos1.setPos(blockpos1).move(EnumFacing.DOWN)).getBlock();
                boolean flag = block instanceof BlockLeaves || block == Blocks.LOG || block == Blocks.LOG2;

                if (flag && this.entity.world.isAirBlock(blockpos1) && this.entity.world.isAirBlock(blockpos$mutableblockpos.setPos(blockpos1).move(EnumFacing.UP))) {
                    break;
                }
            }
        }

        return new Vec3d((double)blockpos1.getX(), (double)blockpos1.getY(), (double)blockpos1.getZ());
    }
    */

	
    
}