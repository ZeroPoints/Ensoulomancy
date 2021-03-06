package com.zeropoints.ensoulomancy.entity.ghost;

import com.zeropoints.ensoulomancy.render.entity.mobs.RenderIttanMomen;
import com.zeropoints.ensoulomancy.util.IEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityIttanMomen extends EntityMob implements IEntity {
	
	public EntityIttanMomen(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
		this.moveHelper = new EntityFlyHelper(this);
	}
	
	@Override
	public void RegisterEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityIttanMomen.class, new RenderIttanMomen.RenderFactory());
	}
	
	@Override
	public MobType GetMobType() {
		return MobType.GHOST;
	}
	
	@Override
	protected void initEntityAI() {
		this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D); // Only flies or is still as cube
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1.0D);
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
	
}
