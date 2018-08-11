package com.zeropoints.soulcraft.api.morphs;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.zeropoints.soulcraft.Main;
//import com.zeropoints.soulcraft.api.morphs.events.SpawnGhostEvent;
import com.zeropoints.soulcraft.api.morphs.AbstractMorph;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * Server event handler
 * 
 * This event handler (or rather listener) is responsible for morphings. In 
 * essence, there are few things going on over here:
 * 
 * 1. Update morphs in the player's loop
 * 2. Acquiring morphs from killed entities
 * 3. Grant additional attack effect while morphed (more damage, explosions, 
 *    potion effects, etc.)
 * 4. Cancel attack targeting for hostile morphs 
 */
public class MorphHandler {
	
    /* Next tick tasks (used for "knockback" attack) */
    public static List<Runnable> FUTURE_TASKS_CLIENT = new ArrayList<Runnable>();
    public static List<Runnable> FUTURE_TASKS_SERVER = new ArrayList<Runnable>();
    
    /**
     * When player is morphed, its morphing abilities are executed over here.
     * 
     * Stuff like gliding, allergies, climbing, swiming and other stuff are 
     * get applied on the player over here.
     */
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.START) {
            return;
        }

        EntityPlayer player = event.player;
        IMorphing capability = Morphing.get(player);

        this.runFutureTasks(player);

        // A sanity check to prevent "healing" health when morphing to and from a mob
        // with essentially zero health
        // We have to do it every tick because you never know when another mod could
        // change the max health
        if (capability != null) {
            // If the current health ratio makes sense, store that ratio in the capability
            float maxHealth = player.getMaxHealth();

            if (maxHealth > IMorphing.REASONABLE_HEALTH_VALUE) {
                float healthRatio = player.getHealth() / maxHealth;
                capability.setLastHealthRatio(healthRatio);
            }
        }

        if (capability == null || !capability.isMorphed()) {
            /* Restore default eye height */
            if (!Main.proxy.config.disable_pov) {
                player.eyeHeight = player.getDefaultEyeHeight();
            }
        }

        try {
            capability.update(player);
        }
        catch (Exception e) {
            e.printStackTrace();

            if (!player.world.isRemote) {
                MorphAPI.demorph(player);
            }
        }
    }

    /**
     * When a player kills an entity, he gains a morph based on the properties 
     * of this entity.
     * 
     * I think I need to implement some mechanism or a callback to map some 
     * entities with the same name onto different morphs. 
     */
    // I will use this event for now to morph the player. 
    @SubscribeEvent
    public void onPlayerKillEntity(LivingDeathEvent event) {
        Entity source = event.getSource().getTrueSource(); // getEntity()
        Entity target = event.getEntity();

        if (target.world.isRemote || source instanceof FakePlayer) {
            return;
        }

        if (!(source instanceof EntityPlayer) || target instanceof EntityPlayer) {
            return;
        }

        EntityPlayer player = (EntityPlayer)source;
        IMorphing capability = Morphing.get(player);

        if (capability == null) {
            return;
        }

        String name = MorphManager.INSTANCE.morphNameFromEntity(target);

        if (!MorphManager.INSTANCE.hasMorph(name)) {
            Main.log(Level.WARN, "Morph by key '" + name + "' doesn't exist!");
            return;
        }

        NBTTagCompound tag = new NBTTagCompound();

        tag.setString("Name", name);
        tag.setTag("EntityData", EntityUtils.stripEntityNBT(target.serializeNBT()));

        AbstractMorph morph = MorphManager.INSTANCE.morphFromNBT(tag);
        
        MorphAPI.morph(player, morph, true); 
    }

    /**
     * When player is morphed, he can deal an damage or effect onto the enemy. 
     * 
     * For example, if player is morphed into the wither skeleton, he also 
     * grants a target wither potion effect. Pretty realistic, however I don't 
     * really know what that does. 
     */
    @SubscribeEvent
    public void onPlayerAttack(LivingAttackEvent event) {
        Entity source = event.getSource().getTrueSource(); //.getEntity();
        Entity target = event.getEntity();

        if (source instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) source;
            IMorphing capability = Morphing.get(player);

            if (capability == null || !capability.isMorphed()) {
                return;
            }

            capability.getCurrentMorph().attack(target, player);
        }
    }

    /**
     * Another morphing handler.
     * 
     * This handler is responsible for canceling setting attack target for 
     * hostile morphs.
     */
    @SubscribeEvent
    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (Main.proxy.config.disable_morph_disguise) {
            return;
        }

        Entity target = event.getTarget();
        EntityLivingBase source = event.getEntityLiving();

        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            IMorphing morphing = Morphing.get(player);

            if (morphing == null || !morphing.isMorphed()) {
                return;
            }

            if (morphing.getCurrentMorph().settings.hostile && source.getAttackingEntity() != target) {
                if (source instanceof EntityLiving) {
                    ((EntityLiving) event.getEntity()).setAttackTarget(null);
                }
            }
        }
    }

    /**
     * Run future tasks on both client and server. 
     */
    private void runFutureTasks(EntityPlayer player) {
        if (player.world.isRemote && !FUTURE_TASKS_CLIENT.isEmpty()) {
            FUTURE_TASKS_CLIENT.remove(0).run();
        }

        if (!player.world.isRemote && !FUTURE_TASKS_SERVER.isEmpty()) {
            FUTURE_TASKS_SERVER.remove(0).run();
        }
    }
}