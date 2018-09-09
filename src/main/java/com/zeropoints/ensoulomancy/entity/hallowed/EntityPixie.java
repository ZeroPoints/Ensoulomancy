package com.zeropoints.ensoulomancy.entity.hallowed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.entity.ai.EntityAIFindItem;
import com.zeropoints.ensoulomancy.entity.ai.EntityAIWanderWalkingOrFlying;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderPixie;
import com.zeropoints.ensoulomancy.util.IEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityPixie extends EntityCreature implements IEntity {

	public static final DataParameter<Boolean> flying = EntityDataManager.<Boolean>createKey(EntityPixie.class, DataSerializers.BOOLEAN);
	public static final DataParameter<BlockPos> landingPos = EntityDataManager.<BlockPos>createKey(EntityPixie.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Integer> variant = EntityDataManager.<Integer>createKey(EntityParrot.class, DataSerializers.VARINT);
	
	public EntityPixie(World world) {
		super(world);
		this.setSize(0.5F, 0.8F);
		this.setCanPickUpLoot(true);
		this.moveHelper = new EntityFlyHelper(this);
	}
	
	/**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc.
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setVariant(this.rand.nextInt(3));
        return super.onInitialSpawn(difficulty, livingdata);
    }
	
	@Override
	public void RegisterEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityPixie.class, new RenderPixie.RenderFactory());
	}
	
	public enum PixieType {
		PINK, BLUE, GREEN, BARBARIAN
	}
	
	public int getVariant() {
		return MathHelper.clamp(((Integer)this.dataManager.get(variant)).intValue(), 0, 4);
	}
	
	public void setVariant(int meta) {
        this.dataManager.set(variant, Integer.valueOf(meta));
    }
	
	protected void entityInit() {
        super.entityInit();
        this.dataManager.register(variant, Integer.valueOf(0));
        this.dataManager.register(landingPos, new BlockPos(0, 0, 0));
        this.dataManager.register(flying, true);
    }
	
	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIFindItem(this, 1.3F));
		this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		//this.tasks.addTask(7, new EntityAIWanderWalkingOrFlying(this, 1.0F));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4D);
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.9F;
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) { }

	@Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) { }
	
	/**
     * Returns new PathNavigateGround instance
     */
    protected PathNavigate createNavigator(World worldIn) {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }
	
	protected void updateAITasks() {
        super.updateAITasks();
        
    	this.dataManager.set(flying, this.onGround);
    	this.dataManager.setDirty(flying);
        
        /*
        if (this.dataManager == null) {
        	return;
        }
        
    	// This is an example of altering the data manager attributes for AI
    	if (this.dataManager.get(flying)) {
    		// A 1/50 chance that if the pixie is flying it will land if deemed 'safe'
    		BlockPos landing = null;
    		if (this.rand.nextInt(50) == 0) {
    			landing = this.dataManager.get(landingPos);
        		if (landing == null) {
        			landing = getLandingPos(this);
        			if (landing != null) {
        				this.dataManager.set(landingPos, landing);
        				this.dataManager.setDirty(landingPos);
        			}
        		}
    		}
    		
    		if (landing != null) {
    			// The creature has reached the landing position. Set flying to false
    			if (this.onGround) {
    				this.dataManager.set(flying, false);
    	        	this.dataManager.setDirty(flying); // Not sure if need to set dirty on the data attribute all the time?
    	        	return;
    			}
    			
    			this.motionX *= 0.9;
    			if (this.motionY > 0.5F) {
    				this.motionY *= 0.9;
    			}
    	        this.motionZ *= 0.9;
    	        
    	        this.moveVertical = -0.3F;
    		}
    		else {
    			// Just keep flying
    			this.moveForward = 0.5F;
    	        this.rotationYaw += 0.1F;
    		}
        }
        */
    }
	
	// TODO: This should go into a helper class??
	/**
	 * Returns whether the nearest block below the entity is 'landable'
	 */
	private BlockPos getLandingPos(Entity entity) {
		double posY = entity.posY;
		while (entity.world.isAirBlock(new BlockPos(entity.posX, posY, entity.posZ)) && posY > 0) {
			posY--;
		}
		
		BlockPos foundPos = new BlockPos(entity.posX, posY, entity.posZ);
		IBlockState blockState = entity.world.getBlockState(foundPos);
		Block block = blockState.getBlock();
		
		// There is a chance that the entity could be falling into void or on unwanted materials. 
		if (posY == 0 || !block.isCollidable() || !block.isFullCube(blockState)) {
			return null;
		}
		
		return foundPos;
	}
	
}
