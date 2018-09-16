package com.zeropoints.ensoulomancy.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.UUID;

import com.zeropoints.ensoulomancy.render.player.RenderGhost;

public class EntityPlayerCorpse extends EntityLiving implements RenderGhost {

	public boolean isAlyx = false;
	
	private EntityPlayer player;
	private UUID playerUUID = null;
	private ThreadDownloadImageData downloadImageSkin;
	private ResourceLocation locationSkin;
	private final boolean isGhost;
	
	private static final EntityEquipmentSlot[] armorSlots = { 
		EntityEquipmentSlot.FEET,
		EntityEquipmentSlot.LEGS,
		EntityEquipmentSlot.CHEST,
		EntityEquipmentSlot.HEAD
	};

	public EntityPlayerCorpse(World world, EntityPlayer player, boolean isGhost) {
		super(world);
		this.setSize(1.0F, 0.4F);
		
		this.playerUUID = player.getGameProfile().getId();
		this.player = player;
		this.isGhost = isGhost;
		
		// Set custom attributes to the relative player
		this.setHealth(player.getHealth());
        this.setCustomNameTag(player.getName());
        //this.setAlwaysRenderNameTag(false);
        
        // Create a duplicate of armor and place it on the 'sleeping' player
        int armorIdx = 0;
    	for (ItemStack item : this.player.getArmorInventoryList()) {
    		this.setItemStackToSlot(armorSlots[armorIdx], item);
    		armorIdx++;
    	}
    	
        if (player.world.isRemote) {
        	this.isAlyx = ((AbstractClientPlayer)player).getSkinType() == "slim"; // Is it the default skin type?
        }
	}
	
	@Override
	public float getEyeHeight() {
		return super.getEyeHeight();
	}
	
	@Override
	public boolean isGhost() {
		return this.isGhost;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}
	
	@Override
	public boolean isAIDisabled() {
		return true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void move(MoverType type, double x, double y, double z) {}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		// Invulnerable. Might change this later
		return false;
	}
   
	protected void setupCustomSkin() {
		String username = this.getOwnerEntity().getName();
		this.locationSkin = AbstractClientPlayer.getLocationSkin(username);
		this.downloadImageSkin = AbstractClientPlayer.getDownloadImageSkin(this.locationSkin, username);
	}

	public EntityPlayer getOwnerEntity() {
		if (this.playerUUID != null) {
			return this.world.getPlayerEntityByUUID(playerUUID);
		}
		return null;
	}

	public ResourceLocation getSkinLocation() {
		if (this.locationSkin == null) {
			this.setupCustomSkin();
		}

		return this.locationSkin != null ? this.locationSkin : new ResourceLocation("minecraft:textures/entity/steve.png");
	}
	
}
