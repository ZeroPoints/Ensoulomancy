package com.zeropoints.ensoulomancy.entity.ai;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIFindItem extends EntityAIBase {
	
    protected final EntityLiving creature;
    protected double speed;
    protected double itemPosX;
    protected double itemPosY;
    protected double itemPosZ;
    
    private static final Random random = new Random();

    public EntityAIFindItem(EntityCreature creature, double speedIn) {
        this.creature = creature;
        this.speed = speedIn;
        this.setMutexBits(1);
    }
    
    public EntityAIFindItem(EntityLiving creature, double speedIn) {
    	this.creature = creature;
    	this.speed = speedIn;
    	this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
    	if (!this.creature.getHeldItemMainhand().isEmpty() || random.nextFloat() < 0.95F) {
    		return false;
    	}
    	
    	Entity nearEntity = getNearestDroppedItem(10);
    	
    	if (nearEntity == null) {
    		return false;
    	}
    	
    	itemPosX = nearEntity.posX;
    	itemPosY = nearEntity.posY;
    	itemPosZ = nearEntity.posZ;
    	return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ(this.itemPosX, this.itemPosY, this.itemPosZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.creature.getNavigator().noPath();
    }
    
    /**
     * Returns the nearest dropped item 
     */
    private Entity getNearestDroppedItem(int radius) {
    	double posX = this.creature.posX,
    		   posY = this.creature.posY,
    		   posZ = this.creature.posZ;
    	
    	return this.creature.getEntityWorld().findNearestEntityWithinAABB(
			EntityItem.class, 
			new AxisAlignedBB(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius), 
			this.creature);
    }
    
}