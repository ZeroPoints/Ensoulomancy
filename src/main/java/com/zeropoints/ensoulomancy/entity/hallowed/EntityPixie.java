package com.zeropoints.ensoulomancy.entity.hallowed;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.entity.ai.EntityAIFindItem;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderPixie;
import com.zeropoints.ensoulomancy.render.particle.ParticlePixieDust;
import com.zeropoints.ensoulomancy.util.IEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityPixie extends EntityMob implements IEntity {

	private static final DataParameter<Integer> variant = EntityDataManager.<Integer>createKey(EntityPixie.class, DataSerializers.VARINT);
	private int droppedItemTimer = 0;
	
	public EntityPixie(World world) {
		super(world);
		this.setSize(0.5F, 0.9F);
		this.setCanPickUpLoot(true);
		this.moveHelper = new EntityFlyHelper(this);
	}
	
	/**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc.
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setVariant(this.rand.nextInt(4)); // Variant 4 is only obtained special
        return super.onInitialSpawn(difficulty, livingdata);
    }
	
	@Override
	public void RegisterEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityPixie.class, new RenderPixie.RenderFactory());
	}
	
	@Override
	public MobType GetMobType() {
		return MobType.HALLOWED;
	}
	
	// TODO: Will have to create a different entity for pixie barbarian (different AI)
	public enum PixieType {
		PINK, BLUE, GREEN, BARBARIAN;
	}
	private static final PixieType[] pixieVals = PixieType.values();
	
	public PixieType getPixieType() {
		return pixieVals[MathHelper.clamp(this.getVariant(), 0, pixieVals.length)];
	}
	
	protected void entityInit() {
        super.entityInit();
        this.dataManager.register(variant, 0);
    }
	
	public int getVariant() {
		return MathHelper.clamp(this.dataManager.get(variant).intValue(), 0, 4);
	}
	
	public void setVariant(int meta) {
        this.dataManager.set(variant, meta);
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("variant", this.getVariant());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setVariant(compound.getInteger("variant"));
	}
	
	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(1, new EntityAIFindItem(this, 1.3F));
		this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		
		// Make sure the entity cannot immeadiately pickup the item once dropped
		if (!this.world.isRemote && !this.canPickUpLoot() && droppedItemTimer++ >= 100) {
			droppedItemTimer = 0;
			this.setCanPickUpLoot(true);
		}
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.8D);
	}
	
	/**
     * Called frequently so the entity can update its state
     */
	@Override
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            if (rand.nextInt(this.onGround ? 20 : 2) == 0) {
            	// Set particle colour based on pixie type
            	int rgb = 0xFFFFFF;
            	switch(this.getPixieType()) {
					case BARBARIAN:
						rgb = 0xd3fffc;
						break;
					case BLUE:
						rgb = 0xd0eafd;
						break;
					case GREEN:
						rgb = 0xBBFFBB;
						break;
					case PINK:
						rgb = 0xf0d9fb;
						break;
            	}
            	
            	// Create the particle
            	double yaw = Math.toRadians(this.getRotationYawHead() - 90);
            	
            	Particle particle = new ParticlePixieDust(this.world, 
                		this.posX + Math.cos(yaw) * rand.nextFloat() * 0.3F, 
                		this.posY + 0.2F + rand.nextFloat() * 0.6F, 
                		this.posZ + Math.sin(yaw) * rand.nextFloat() * 0.3F, 
                		rand.nextGaussian() * 0.005D, 
                		rand.nextGaussian() * 0.005D, 
                		rand.nextGaussian() * 0.005D,
                		rgb, 0.5F);
            	
            	// Render the particle
            	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
            }
        }

        //this.isJumping = false;
        super.onLivingUpdate();
    }
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		// When get hit drop the item
		if (!this.world.isRemote) {
			this.entityDropItem(getHeldItemMainhand(), 1.0F); // Drop currently held item
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.9F;
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) { }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) { }
	
    protected PathNavigate createNavigator(World worldIn) {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
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
