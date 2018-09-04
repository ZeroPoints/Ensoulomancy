package com.zeropoints.ensoulomancy.entity;

import java.util.Arrays;
import java.util.UUID;

import com.zeropoints.ensoulomancy.api.morphs.AbstractMorph;
import com.zeropoints.ensoulomancy.api.morphs.MorphAPI;
import com.zeropoints.ensoulomancy.api.morphs.MorphManager;
import com.zeropoints.ensoulomancy.api.morphs.models.IMorphProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

/**
 * Entity morph
 * 
 * This entity is responsible for showing up the morph which will player 
 * acquire.
 * 
 * This entity is similar to {@link EntityXPOrb} or {@link EntityItem}, in terms 
 * of picking up.
 */
public class EntityMorph extends EntityLivingBase implements IEntityAdditionalSpawnData, IMorphProvider {
	
    private String username;
    private UUID owner;
    private boolean ownerless = true;

    private EntityPlayer player;

    public int timer = 30;
    public int lifetime = 2400;
    public AbstractMorph morph;

    /**
     * Initiate the morph and make this entity invulnerable
     */
    public EntityMorph(World worldIn) {
        super(worldIn);

        this.setEntityInvulnerable(true);
        this.setCustomNameTag("Morph");
    }

    /**
     * Initiate the morph with morph and owner's UUID 
     */
    public EntityMorph(World worldIn, UUID owner, AbstractMorph morph) {
        this(worldIn);

        this.owner = owner;
        this.morph = morph;
        this.ownerless = false;

        this.setSize(morph);
    }

    @Override
    public AbstractMorph getMorph() {
        return this.morph;
    }

    /**
     * Get display name
     */
    @Override
    public ITextComponent getDisplayName() {
        if (this.morph != null) {
            return new TextComponentTranslation("entity." + this.morph.name + ".name");
        }

        return super.getDisplayName();
    }

    /**
     * Set size based on the morph's characteristics
     */
    private void setSize(AbstractMorph morph) {
        if (morph != null) {
            this.setSize(MathHelper.clamp(morph.getWidth(this), 0, 1.5F), MathHelper.clamp(morph.getHeight(this), 0, 2.0F));
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    /**
     * Can't collide with other entities 
     */
    @Override
    protected void collideWithNearbyEntities() {}

    /**
     * Update method
     * 
     * This method is responsible for looking for player owner and advancing 
     * itself toward the player. It will also die and grant the morph to the 
     * player when it will be very close to player.
     */
    @Override
    public void onUpdate() {
        /* Don't allow it move horizontally */
        this.motionX = this.motionZ = 0;

        super.onUpdate();

        if (this.timer > 0) {
            this.timer--;
            return;
        }

        /* Find an owner */
        if (!this.world.isRemote && !this.isDead) {
            if (this.lifetime > 0) {
                this.lifetime--;
            }
            else if (this.lifetime == 0) {
                /* I.e. kill only if lifetime was above 0 (in this case
                 * only /summon'd morph with custom name tag can have 
                 * negative lifetime) */
                this.setDead();
            }
        }
    }

    /* Read / write */

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("LifeTime", this.lifetime);
        compound.setBoolean("Ownerless", this.ownerless);

        if (this.username != null && !this.username.isEmpty()) {
            compound.setString("Username", this.username);
        }

        if (this.owner != null) {
            compound.setString("Owner", this.owner.toString());
        }

        if (this.morph != null) {
            NBTTagCompound tag = new NBTTagCompound();

            this.morph.toNBT(tag);
            compound.setTag("Morph", tag);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        String owner = compound.getString("Owner");

        this.owner = owner.isEmpty() ? null : UUID.fromString(owner);

        if (compound.hasKey("LifeTime", 99)) {
            this.lifetime = compound.getInteger("LifeTime");
        }

        if (this.owner != null) {
            this.ownerless = false;
        }

        if (compound.hasKey("Username", 8)) {
            this.username = compound.getString("Username");
            this.ownerless = false;
        }
        else if (compound.hasKey("Ownerless")) {
            this.ownerless = compound.getBoolean("Ownerless");
        }

        if (compound.hasKey("Morph", 10)) {
            this.morph = MorphManager.INSTANCE.morphFromNBT(compound.getCompoundTag("Morph"));
        }

        this.setSize(morph);
    }

    /* Spawn data read / write */

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, this.owner != null ? this.owner.toString() : "");

        if (this.morph != null) {
            NBTTagCompound tag = new NBTTagCompound();

            this.morph.toNBT(tag);

            boolean hasData = tag != null && !tag.hasNoTags();

            buffer.writeBoolean(hasData);

            if (hasData) {
                ByteBufUtils.writeTag(buffer, tag);
            }
        }
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        String owner = ByteBufUtils.readUTF8String(buffer);

        this.owner = owner.isEmpty() ? null : UUID.fromString(owner);

        if (buffer.readBoolean()) {
            NBTTagCompound tag = ByteBufUtils.readTag(buffer);
            this.morph = MorphManager.INSTANCE.morphFromNBT(tag);
        }

        this.setSize(morph);
    }

    /* Unused methods */

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Arrays.<ItemStack>asList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return null;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {}

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }
}