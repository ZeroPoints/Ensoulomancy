package com.zeropoints.soulcraft.api.morphs.helpers.actions;

import javax.annotation.Nullable;

import com.zeropoints.soulcraft.api.morphs.AbstractMorph;
import com.zeropoints.soulcraft.api.morphs.abilities.IAction;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Small fireball action
 * 
 * This action is responsible for shooting a fireball from player's face. Used 
 * by blaze morph.
 */
public class SmallFireball implements IAction {
	
    @Override
    public void execute(EntityLivingBase target/*, @Nullable AbstractMorph morph*/) {
        World world = target.world;

        if (world.isRemote) {
            return;
        }

        if (target instanceof EntityPlayer && ((EntityPlayer) target).getCooledAttackStrength(0.0F) < 1) {
            return;
        }

        Vec3d vec3d = target.getLook(1.0F);

        double d1 = 4.0D;
        double d2 = vec3d.x * d1;
        double d3 = vec3d.y * d1;
        double d4 = vec3d.z * d1;

        world.playEvent((EntityPlayer) null, 1016, new BlockPos(target), 0);

        EntitySmallFireball fireball = new EntitySmallFireball(world, target, d2, d3, d4);

        fireball.posX = target.posX;
        fireball.posY = target.posY + target.height * 0.9;
        fireball.posZ = target.posZ;

        world.spawnEntity(fireball);

        if (target instanceof EntityPlayer) {
            ((EntityPlayer) target).resetCooldown();
        }
    }
}