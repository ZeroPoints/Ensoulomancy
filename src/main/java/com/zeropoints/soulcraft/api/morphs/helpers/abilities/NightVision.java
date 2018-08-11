package com.zeropoints.soulcraft.api.morphs.helpers.abilities;

import net.minecraft.init.MobEffects;

/**
 * Night vision ability
 * 
 * Grants night vision effect for given morph. Mostly used by bat.
 */
public class NightVision extends PotionAbility {
    public NightVision() {
        this.potion = MobEffects.NIGHT_VISION;
    }
}