package com.zeropoints.soulcraft.capabilities.morphing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.api.morphs.AbstractMorph;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Default implementation of {@link IMorphing} interface.
 *
 * This class is responsible for storing current morphing, setting and retrieval
 * of current morphing.
 */
public class Morphing implements IMorphing {
	
    /**
     * Current used morph
     */
    private AbstractMorph morph;

    /**
     * Used for animation
     */
    private AbstractMorph previousMorph;

    /**
     * Animation timer 
     */
    private int animation;

    /**
     * (health / max health) is stored here when the new max health ends up
     * very close to zero, and retrieved when the fraction is meaningful again
     */
    private float lastHealthRatio;

    public static IMorphing get(EntityPlayer player) {
        return player.getCapability(MorphingProvider.MORPHING_CAP, null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isAnimating() {
        if (Main.proxy.config.disable_morph_animation) {
            return false;
        }

        return this.animation != -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimation() {
        return this.animation;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AbstractMorph getPreviousMorph() {
        return this.previousMorph;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderPlayer(EntityPlayer player, double x, double y, double z, float yaw, float partialTick) {
        if (this.morph == null && !this.isAnimating()) {
            return false;
        }

        if (this.morph == null && this.animation <= 10 || this.previousMorph == null && this.animation > 10) {
            return false;
        }

        if (!this.isAnimating()) {
            this.morph.render(player, x, y, z, yaw, partialTick);
            return true;
        }

        GlStateManager.pushMatrix();

        /* Render morph in after transition */
        if (this.animation <= 10) {
            float anim = (this.animation - partialTick) / 10.0F;
            float offset = 0;

            if (anim >= 0) {
                offset = -anim * anim * 2F;
            }

            GlStateManager.translate(x, y + offset, z);

            if (anim >= 0) {
                GlStateManager.rotate(anim * -90.0F, 1, 0, 0);
                GlStateManager.scale(1 - anim, 1 - anim, 1 - anim);
            }

            this.morph.render(player, 0, 0, 0, yaw, partialTick);
        }
        else if (this.previousMorph != null) {
            /* Render morph before the transition */
            float anim = (this.animation - 10 - partialTick) / 10.0F;
            float offset = 0;

            if (anim >= 0) {
                offset = (1 - anim);
            }

            GlStateManager.translate(x, y + offset, z);

            if (anim >= 0) {
                GlStateManager.rotate((1 - anim) * 90.0F, 1, 0, 0);
                GlStateManager.scale(anim, anim, anim);
            }

            this.previousMorph.render(player, 0, 0, 0, yaw, partialTick);
        }

        GlStateManager.popMatrix();

        return true;
    }

    @Override
    public AbstractMorph getCurrentMorph() {
        return this.morph;
    }

    @Override
    public boolean setCurrentMorph(AbstractMorph morph, EntityPlayer player, boolean force) {
        if (morph == null) {
            this.demorph(player);
            return true;
        }

        boolean creative = player != null ? player.isCreative() : false;

        //if (force || creative || this.acquiredMorph(morph)) {
            if (player != null && this.morph != null) {
                this.morph.demorph(player);
            }

            this.setMorph(morph);

            if (player != null) {
                this.morph.morph(player);
            }

            return true;
        //}

        //return false;
    }

    @Override
    public void demorph(EntityPlayer player) {
        if (player != null && this.morph != null) {
            this.morph.demorph(player);
        }

        this.setMorph(null);
    }

    /**
     * Set current morph, as well as update animation information  
     */
    protected void setMorph(AbstractMorph morph) {
        if (!Main.proxy.config.disable_morph_animation) {
            this.animation = 20;
        }

        this.previousMorph = this.morph;
        this.morph = morph;
    }

    @Override
    public boolean isMorphed() {
        return this.morph != null;
    }

    @Override
    public void copy(IMorphing morphing, EntityPlayer player) {
        this.setCurrentMorph(morphing.getCurrentMorph(), player, true);
    }

    @Override
    public float getLastHealthRatio() {
        return lastHealthRatio;
    }

    @Override
    public void setLastHealthRatio(float lastHealthRatio) {
        this.lastHealthRatio = lastHealthRatio;
    }

    @Override
    public void update(EntityPlayer player) {
        if (this.animation >= 0) {
            this.animation--;
        }

        if (this.animation == 16 && !player.world.isRemote && !Main.proxy.config.disable_morph_animation) {
            /* Pop! */
            ((WorldServer) player.world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, false, player.posX, player.posY + 0.5, player.posZ, 25, 0.5, 0.5, 0.5, 0.05);
            player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        }

        if (this.morph != null) {
            this.morph.update(player, this);
        }
    }
}