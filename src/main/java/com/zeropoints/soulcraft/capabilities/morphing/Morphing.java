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
     * (health / max health) is stored here when the new max health ends up
     * very close to zero, and retrieved when the fraction is meaningful again
     */
    private float lastHealthRatio;

    public static IMorphing get(EntityPlayer player) {
        return player.getCapability(MorphingProvider.MORPHING_CAP, null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderPlayer(EntityPlayer player, double x, double y, double z, float yaw, float partialTick) {
        if (this.morph == null) {
            return false;
        }

        this.morph.render(player, x, y, z, yaw, partialTick);
        return true;
    }

    @Override
    public AbstractMorph getCurrentMorph() {
        return this.morph;
    }

    @Override
    public boolean setCurrentMorph(AbstractMorph morph, EntityPlayer player) {
    	if (player != null && !player.world.isRemote) {
	        /* Poof! */
	        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, false, player.posX, player.posY + 0.5, player.posZ, 25, 0.5, 0.5, 0.5, 0.05);
        }
    	
        if (morph == null) {
            this.demorph(player);
            return true;
        }
        
        if (player != null && this.morph != null) {
            this.morph.demorph(player);
        }

        this.setMorph(morph);

        if (player != null) {
            this.morph.morph(player);
        }
        
        return true;
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
        this.morph = morph;
    }

    @Override
    public boolean isMorphed() {
        return this.morph != null;
    }

    @Override
    public void copy(IMorphing morphing, EntityPlayer player) {
        this.setCurrentMorph(morphing.getCurrentMorph(), player);
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
        if (this.morph != null) {
            this.morph.update(player, this);
        }
    }
}