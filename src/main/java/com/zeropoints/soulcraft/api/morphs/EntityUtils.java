package com.zeropoints.soulcraft.api.morphs;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import com.zeropoints.soulcraft.api.morphs.models.IMorphProvider;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.MorphingProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Entity utilities methods
 * 
 * Methods that are related to {@link EntityMorph} are going here.
 */
public class EntityUtils {
	
    /**
     * List of attributes to remove
     */
    private static List<String> removeAttributes = Lists.newArrayList("generic.followRange");

    /**
     * Strip some common {@link Entity} related tags, so there won't be 
     * interference with comparing two tags on  
     */
    public static NBTTagCompound stripEntityNBT(NBTTagCompound tag) {
        /* Custom displayed name */
        if (tag.hasKey("CustomName", 8)) {
            String name = tag.getString("CustomName");

            if (!name.equals("jeb_") && !name.equals("Grumm") && !name.equals("Dinnerbone") && !name.equals("Toast")) {
                tag.removeTag("CustomName");
            }
        }

        /* Meta stuff */
        tag.removeTag("Dimension");
        tag.removeTag("HurtTime");
        tag.removeTag("DeathTime");
        tag.removeTag("HurtByTimestamp");
        tag.removeTag("Health");
        tag.removeTag("PortalCooldown");
        tag.removeTag("Leashed");
        tag.removeTag("Air");
        tag.removeTag("id");
        tag.removeTag("Invulnerable");

        /* Inventory and equipment */
        tag.removeTag("ArmorDropChances");
        tag.removeTag("HandDropChances");
        tag.removeTag("HandItems");
        tag.removeTag("Inventory");
        tag.removeTag("LeftHanded");
        tag.removeTag("CanPickUpLoot");

        /* Space data */
        tag.removeTag("Pos");
        tag.removeTag("Motion");
        tag.removeTag("Rotation");
        tag.removeTag("FallDistance");
        tag.removeTag("FallFlying");
        tag.removeTag("OnGround");
        tag.removeTag("Fire");
        tag.removeTag("ArmorItems");

        /* UUID */
        tag.removeTag("UUIDLeast");
        tag.removeTag("UUIDMost");

        /* Attributes */
        if (tag.hasKey("Attributes")) {
            NBTTagList attributes = tag.getTagList("Attributes", 10);

            for (int i = attributes.tagCount() - 1; i >= 0; i--) {
                if (removeAttributes.contains(attributes.getCompoundTagAt(i).getString("Name"))) {
                    attributes.removeTag(i);
                }
            }
        }

        /* Shulker tags stripping */
        tag.removeTag("Peek");
        tag.removeTag("AttachFace");
        tag.removeTag("APX");
        tag.removeTag("APY");
        tag.removeTag("APZ");

        return tag;
    }

    /**
     * Compare two {@link NBTTagCompound}s for morphing acquiring
     */
    public static boolean compareData(NBTTagCompound a, NBTTagCompound b) {
        /* Different count of tags? They're different */
        if (a.getSize() != b.getSize()) {
            return false;
        }

        for (String key : a.getKeySet()) {
            NBTBase aTag = a.getTag(key);
            NBTBase bTag = b.getTag(key);

            /* Supporting condition for size check above, in case if the size 
             * the same, but different keys are missing */
            if (bTag == null) {
                return false;
            }

            /* We check only strings and primitives, lists and compounds aren't 
             * concern of mine */
            if (!(aTag instanceof NBTPrimitive) && !(aTag instanceof NBTTagString)) {
                continue;
            }

            if (!aTag.equals(bTag)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Get slot for given index of {@link Entity#getEquipmentAndArmor()}. I 
     * assume that it would be the same all the time, across all of the 
     * subclasses of {@link Entity}.
     */
    public static EntityEquipmentSlot slotForIndex(int index) {
        EntityEquipmentSlot slot = EntityEquipmentSlot.MAINHAND;

        switch (index) {
            case 1:
                slot = EntityEquipmentSlot.OFFHAND;
                break;
            case 2:
                slot = EntityEquipmentSlot.FEET;
                break;
            case 3:
                slot = EntityEquipmentSlot.LEGS;
                break;
            case 4:
                slot = EntityEquipmentSlot.CHEST;
                break;
            case 5:
                slot = EntityEquipmentSlot.HEAD;
                break;
        }

        return slot;
    }

    /**
     * Get morph from an entity 
     */
    public static AbstractMorph getMorph(EntityLivingBase entity) {
        if (entity instanceof IMorphProvider) {
            return ((IMorphProvider) entity).getMorph();
        }
        else {
            IMorphing cap = entity.getCapability(MorphingProvider.MORPHING_CAP, null);

            if (cap != null) {
                return cap.getCurrentMorph();
            }
        }

        return null;
    }

    /**
     * Get string pose for entity based on his attributes
     */
    public static String getPose(EntityLivingBase entity) {
        if (entity.isElytraFlying()) {
            return "flying";
        }
        else if (entity.isRiding()) {
            return "riding";
        }
        else if (entity.isSneaking()) {
            return "sneaking";
        }

        return "standing";
    }
    


    /**
     * Get the entity at which given player is looking at.
     * Taken from EntityRenderer class.
     *
     * That's a big method... Why Minecraft has lots of these big methods?
     */
    public static Entity getTargetEntity(Entity input, double maxReach) {
        double blockDistance = maxReach;

        RayTraceResult result = rayTrace(input, maxReach, 1.0F);
        Vec3d eyes = new Vec3d(input.posX, input.posY + input.getEyeHeight(), input.posZ);

        if (result != null) {
            blockDistance = result.hitVec.distanceTo(eyes);
        }

        Vec3d look = input.getLook(1.0F);
        Vec3d max = eyes.addVector(look.x * maxReach, look.y * maxReach, look.z * maxReach);
        Entity target = null;

        float area = 1.0F;

        List<Entity> list = input.world.getEntitiesInAABBexcluding(input, input.getEntityBoundingBox()/*.addCoord(look.x * maxReach, look.y * maxReach, look.z * maxReach).expand(area, area, area)*/, new Predicate<Entity>() {
            @Override
            public boolean apply(@Nullable Entity entity) {
                return entity != null && entity.canBeCollidedWith();
            }
        });

        double entityDistance = blockDistance;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);

            if (entity == input) {
                continue;
            }

            float expand = entity.getCollisionBorderSize();
            AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(expand, expand, expand);
            RayTraceResult intercept = aabb.calculateIntercept(eyes, max);

            if (aabb.contains(eyes)) { // intersects??
                if (entityDistance >= 0.0D) {
                    target = entity;
                    entityDistance = 0.0D;
                }
            }
            else if (intercept != null) {
                double eyesDistance = eyes.distanceTo(intercept.hitVec);

                if (eyesDistance < entityDistance || entityDistance == 0.0D) {
                    if (entity.getLowestRidingEntity() == input.getLowestRidingEntity() && !input.canRiderInteract()) {
                        if (entityDistance == 0.0D) {
                            target = entity;
                        }
                    }
                    else {
                        target = entity;
                        entityDistance = eyesDistance;
                    }
                }
            }
        }

        return target;
    }

    /**
     * This method is extracted from {@link Entity} class, because it was marked
     * as client side only code.
     */
    public static RayTraceResult rayTrace(Entity input, double blockReachDistance, float partialTicks) {
        Vec3d eyePos = new Vec3d(input.posX, input.posY + input.getEyeHeight(), input.posZ);
        Vec3d eyeDir = input.getLook(partialTicks);
        Vec3d eyeReach = eyePos.addVector(eyeDir.x * blockReachDistance, eyeDir.y * blockReachDistance, eyeDir.z * blockReachDistance);

        return input.world.rayTraceBlocks(eyePos, eyeReach, false, false, true);
    }
}