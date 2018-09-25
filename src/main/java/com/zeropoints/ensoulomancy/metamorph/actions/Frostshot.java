package com.zeropoints.ensoulomancy.metamorph.actions;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.action.EntityFrostshot;

import mchorse.metamorph.api.abilities.IAction;
import mchorse.metamorph.api.morphs.AbstractMorph;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Frostshot implements IAction
{
    @Override
    public void execute(EntityLivingBase target, @Nullable AbstractMorph morph)
    {
    	Main.log("Frostshot Executed: " + target.dimension);
    	
        World world = target.world;

        if (world.isRemote)
        {
            return;
        }

        if (target instanceof EntityPlayer && ((EntityPlayer) target).getCooledAttackStrength(0.0F) < 1)
        {
            return;
        }

        Vec3d vec3d = target.getLook(1.0F);

        double d1 = 4.0D;
        double d2 = vec3d.x * d1;
        double d3 = vec3d.y * d1;
        double d4 = vec3d.z * d1;

        world.playEvent((EntityPlayer) null, 1016, new BlockPos(target), 0);

        EntityFrostshot fireball = new EntityFrostshot(world, target, d2, d3, d4);

        fireball.posX = target.posX;
        fireball.posY = target.posY + target.height * 0.9;
        fireball.posZ = target.posZ;

        if(world.spawnEntity(fireball)) {
        	Main.log("Frostshot Yay");
        }
        else {
        	Main.log("Frostshot No");
        }

        if (target instanceof EntityPlayer)
        {
            ((EntityPlayer) target).resetCooldown();
        }
    }
}