package com.zeropoints.ensoulomancy.entity.profane;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.entity.ai.EntityAIFindItem;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.items.tools.ReapingScythe;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderImp;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderImp.RenderFactory;
import com.zeropoints.ensoulomancy.util.IEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityImp extends EntityMob implements IEntity {

	public static final DataParameter<Boolean> flying = EntityDataManager.<Boolean>createKey(EntityImp.class, DataSerializers.BOOLEAN);
	
	public EntityImp(World world) {
		super(world);
		this.setSize(0.5F, 0.9F);
		this.setCanPickUpLoot(true);
	}
	
	@Override
	public void RegisterEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, new RenderImp.RenderFactory());
	}
	
	protected void entityInit() {
        super.entityInit();
        this.dataManager.register(flying, true);
    }
	
	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(2, new EntityAIFindItem(this, 1.3D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(4, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
		
		// The imps attack each other if they walk into each other
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.3D, false));
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}
	
	/*@Override
	protected void collideWithEntity(Entity entity) {
		// 1 in 20 chance they will attack mob they are next to
		if (entity instanceof IMob && !(entity instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
			this.setAttackTarget((EntityLivingBase)entity);
		}
		super.collideWithEntity(entity);
	}*/
	
	/*
	public boolean canAttackClass(Class <? extends EntityLivingbase> cls) {
		if (this.isPlayerCreated() && EntityPlayer.class.isAssignableFrom(cls)) {
			return false;
		}
		else {
			return cls == EntityCreeper.class ? false : super.canAttackClass(cls);
		}
	}
	*/
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_BAT_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BAT_DEATH;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block block) {
		this.playSound(SoundEvents.ENTITY_PARROT_STEP, 1.0F, 1.0F);
	}
	
	@Nullable
	@Override
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_BAT;
	}
	
}
