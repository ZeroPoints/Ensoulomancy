package com.zeropoints.soulcraft.api.morphs;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.api.morphs.abilities.IAbility;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Base class for all different types of morphs
 * 
 * This is an abstract morph. It contains all needed properties for a basic 
 * morph such as abilities, action, attack, health, speed and hotstyle flag.
 * 
 * This class is also responsible for rendering operations. Oh boy, this class 
 * is so huge. I'll have to decompose this thing onto rendering and logic code.
 */
public abstract class AbstractMorph {
	
    /* Abilities */

	/**
     * Morph settings 
     */
    public MorphSettings settings = MorphSettings.DEFAULT;

    /* Meta information */

    /**
     * Morph's name
     */
    public String name = "";

    /**
     * Health when the player morphed into this morph 
     */
    protected float lastHealth;

    /* Rendering */

    /**
     * Client morph renderer. It's for {@link EntityPlayer} only, don't try 
     * using it with other types of entities.
     */
    @SideOnly(Side.CLIENT)
    public Render<? extends Entity> renderer;

    /* Render methods */

    /**
     * Render the entity (in the world) 
     */
    @SideOnly(Side.CLIENT)
    public abstract void render(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks);

    /**
     * Render the arm for given hand 
     */
    @SideOnly(Side.CLIENT)
    public boolean renderHand(EntityPlayer player, EnumHand hand) {
        return false;
    }

    /* Update loop */

    /**
     * Update the player based on its morph abilities and properties. This 
     * method also responsible for updating AABB size. 
     */
    public void update(EntityLivingBase target, IMorphing cap) {
        this.setMaxHealth(target, this.settings.health);

        if (this.settings.speed != 0.1F) {
            target.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.settings.speed);
        }

        for (IAbility ability : this.settings.abilities) {
            ability.update(target);
        }
    }

    /* Morph and demorph handlers */

    /**
     * Morph into the current morph
     * 
     * This method responsible for setting up the health of the player to 
     * morph's health and invoke ability's onMorph methods.
     */
    public void morph(EntityLivingBase target) {
        this.lastHealth = target.getMaxHealth();
        this.setHealth(target, this.settings.health);

        for (IAbility ability : this.settings.abilities) {
            ability.onMorph(target);
        }
    }

    /**
     * Demorph from the current morph
     * 
     * This method responsible for setting up the health back to player's 
     * default health and invoke ability's onDemorph methods.
     */
    public void demorph(EntityLivingBase target) {
        /* 20 is default player's health */
        this.setHealth(target, this.lastHealth < 20 ? 20 : this.lastHealth);

        for (IAbility ability : this.settings.abilities) {
            ability.onDemorph(target);
        }
    }

    /* Adjusting size */

    /**
     * Update player's size based on given width and height.
     * 
     * This method is responsible for doing trickshots, 360 noscopes while being 
     * morped in a morph. Probably...
     */
    protected void updateSize(EntityLivingBase target, float width, float height) {
        if (target instanceof EntityPlayer && !Main.proxy.config.disable_pov) {
            ((EntityPlayer) target).eyeHeight = height * 0.9F;
        }

        /* This is a total rip-off of EntityPlayer#setSize method */
        if (width != target.width || height != target.height) {
            AxisAlignedBB aabb = target.getEntityBoundingBox();

            target.width = width;
            target.height = height;
            target.setEntityBoundingBox(new AxisAlignedBB(target.posX - width / 2, aabb.minY, target.posZ - width / 2, target.posX + width / 2, aabb.minY + height, target.posZ + width / 2));
        }
    }

    /* Adjusting health */

    /**
     * Set player's health proportional to the current health with given max 
     * health.
     * 
     * @author asanetargoss
     */
    protected void setHealth(EntityLivingBase target, float health) {
        float maxHealth = target.getMaxHealth();
        float currentHealth = target.getHealth();
        float ratio = currentHealth / maxHealth;

        // A sanity check to prevent "healing" health when morphing to and from a mob
        // with essentially zero health
        if (target instanceof EntityPlayer) {
            IMorphing capability = Morphing.getCapability((EntityPlayer) target);
            if (capability != null) {
                // Check if a health ratio makes sense for the old health value
                if (maxHealth > IMorphing.REASONABLE_HEALTH_VALUE) {
                    // If it makes sense, store that ratio in the capability
                    capability.setLastHealthRatio(ratio);
                }
                else {
                    if (health > IMorphing.REASONABLE_HEALTH_VALUE) {
                        // If it doesn't make sense, BUT the new max health makes
                        // sense, retrieve the
                        // ratio from the capability and use that instead
                        ratio = capability.getLastHealthRatio();
                    }
                }
            }
        }

        this.setMaxHealth(target, health);
        // We need to retrieve the max health of the target after modifiers are applied
        // to get a sensible value
        float proportionalHealth = Math.round(target.getMaxHealth() * ratio);
        target.setHealth(proportionalHealth <= 0 ? 1 : proportionalHealth);
    }

    /**
     * Set player's max health
     */
    protected void setMaxHealth(EntityLivingBase target, float health) {
        if (target.getMaxHealth() != health) {
            target.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health);
        }
    }

    /* Safe shortcuts for activating action and attack */

    /**
     * Execute action with (or on) given player 
     */
    public void action(EntityLivingBase target) {
        if (this.settings.action != null) {
            this.settings.action.execute(target);
        }
    }

    /**
     * Attack a target 
     */
    public void attack(Entity target, EntityLivingBase source) {
        if (this.settings.attack != null) {
            this.settings.attack.attack(target, source);
        }
    }

    /**
     * <p>Clone a morph.</p>
     * <p><b>IMPORTANT</b>: when you subclass other morphs, don't forget to override 
     * their method with your own, because otherwise its going to create 
     * another {@link CustomMorph} instance, for example, instead of 
     * MyCustomMorph instance.</p>
     */
    public abstract AbstractMorph clone(boolean isRemote);

    /**
     * Get width of this morph 
     */
    public abstract float getWidth(EntityLivingBase target);

    /**
     * Get height of this morph 
     */
    public abstract float getHeight(EntityLivingBase target);

    /**
     * Check either if given object is the same as this morph 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractMorph) {
            AbstractMorph morph = (AbstractMorph) obj;
            return morph.name.equals(this.name);
        }

        return super.equals(obj);
    }

    /* Reading / writing to NBT */

    /**
     * Save abstract morph's properties to NBT compound 
     */
    public void toNBT(NBTTagCompound tag) {
        tag.setString("Name", this.name);
        tag.setFloat("LastHealth", this.lastHealth);
    }

    /**
     * Read abstract morph's properties from NBT compound 
     */
    public void fromNBT(NBTTagCompound tag) {
        this.name = tag.getString("Name");
        this.lastHealth = tag.getFloat("LastHealth");
    }
}