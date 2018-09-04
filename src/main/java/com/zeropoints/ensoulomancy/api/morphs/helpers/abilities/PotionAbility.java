package com.zeropoints.ensoulomancy.api.morphs.helpers.abilities;

import com.zeropoints.ensoulomancy.api.morphs.abilities.Ability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Abstract potion ability
 * 
 * This is class is responsible for adding specific given potion effect from 
 * subclasses. You can also give the duration for the potion effect. 
 */
public abstract class PotionAbility extends Ability {
	
    protected Potion potion;
    protected int duration = 1200;

    @Override
    public void update(EntityLivingBase target) {
        PotionEffect effect = target.getActivePotionEffect(this.potion);

        if (effect == null || effect.getDuration() < 300) {
            this.onDemorph(target);
            this.onMorph(target);
        }
    }

    @Override
    public void onMorph(EntityLivingBase target) {
        target.addPotionEffect(new PotionEffect(this.potion, this.duration, 0, false, false));
    }

    @Override
    public void onDemorph(EntityLivingBase target) {
        target.removePotionEffect(this.potion);
    }
}