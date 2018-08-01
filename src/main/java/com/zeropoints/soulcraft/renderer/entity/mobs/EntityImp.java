package com.zeropoints.soulcraft.renderer.entity.mobs;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityImp extends EntityLiving {

	private int homeCheckTimer;
	private int attackTimer;
	
	public EntityImp(World world) {
		super(world);
		this.setSize(0.5F, 0.9F);
	}
	
	protected void initEntityAI() {
		//this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(2,  new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	}
	
	protected void entityInit() {
		super.entityInit();
	}
	
	protected void updateAITasks() {
		
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		//this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(1.0D);
	}
	
	protected int decreaseAirSupply(int air) {
		return air;
	}
	
	protected void collideWithEntity(Entity entity) {
		if(entity instanceof IMob && !(entity instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
			this.setAttackTarget((EntityLivingBase)entity);
		}
		super.collideWithEntity(entity);
	}
	
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		/*
		if(this.attackTimer > 0) {
			--this.attackTimer;
		}
		
		if(this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201) {
			int i = MathHelper.floor(this.posX);
			int j = MathHelper.floor(this.posY - 0.20000000298023224D);
			int k = MathHelper.floor(this.posZ);
			IBlockState iblockstate = this.world.getBlockState(new BlockPos(i,j,k));
			if(iblockstate.getMaterial() != Material.AIR) {
				//this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
			}
		}
		*/
	}
	
	/*
	public boolean canAttackClass(Class <? extends EntityLivingbase> cls) {
		if(this.isPlayerCreated() && EntityPlayer.class.isAssignableFrom(cls)) {
			return false;
		}
		else {
			return cls == EntityCreeper.class ? false : super.canAttackClass(cls);
		}
	}
	*/
	
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_BAT_HURT;
	}
	
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BAT_DEATH;
	}
	
	protected void playStepSound(BlockPos pos, Block block) {
		this.playSound(SoundEvents.ENTITY_PARROT_STEP, 1.0F, 1.0F);
	}
	
	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_BAT;
	}
	
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}
	
}
