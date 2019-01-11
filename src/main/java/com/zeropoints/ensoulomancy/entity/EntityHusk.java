package com.zeropoints.ensoulomancy.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.zeropoints.ensoulomancy.entity.ai.EntityAIFindItem;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.items.tools.ReapingScythe;
import com.zeropoints.ensoulomancy.model.husk.HuskBase;
import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;
import com.zeropoints.ensoulomancy.model.husk.legs.QuadrapedLegs;
import com.zeropoints.ensoulomancy.render.entity.husk.RenderHusk;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderImp;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderImp.RenderFactory;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.IEntity;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskRegistryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHusk extends EntityMob implements IEntity {
	
	/* This contains a comma separated string containing the base souls where the first soul is seen as the 'main' body. e.g. Enderman,Guardian */
	public static final DataParameter<String> souls = EntityDataManager.<String>createKey(EntityHusk.class, DataSerializers.STRING);
	
	/* Save string of all options to do with our husk. Manipulative version out of NBT data is set in the list below */
	public static final DataParameter<String> parts = EntityDataManager.<String>createKey(EntityHusk.class, DataSerializers.STRING);
	
	/* List containing all husk body parts in order of when they shall be created. */
	public List<HuskPart> partList = new ArrayList<HuskPart>();
	
	/*  **/
	public int offsetHeight = 0;
	public HuskPart body = null;
	
	/* First round of drawing is done, rather than loop through again, just reuse elements **/
	public boolean initialized = false;
	
	
	public EntityHusk(World world) {
		super(world);
		this.setSize(0.5F, 1.0F);
	}
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(parts, "");
		this.dataManager.register(souls, "");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setString("parts", this.dataManager.get(parts));
        compound.setString("souls", this.dataManager.get(souls));
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.dataManager.set(souls, compound.getString("souls"));
		this.dataManager.setDirty(souls);
		this.dataManager.set(parts, compound.getString("parts"));
		this.dataManager.setDirty(parts);
	}
	
	/**
	 * Setup entity from data manager attributes. This should only be done client-side 
	 * TODO: Record abilities server-side
	 */
	@SideOnly(Side.CLIENT)
	public void setupModelRender() {
		String partStr = this.dataManager.get(parts);
		String soulItems = this.dataManager.get(souls);
		String[] soulList = soulItems.split(",");
		
		// Empty string is a bad string. We want it based on the heads inserted into it
		// A husk should never be able to be created without any souls
		if (partStr.isEmpty()) {
			partList.clear();
			partList.addAll(HuskPart.GenerateHuskParts(soulList));
		}
		else if (partStr != "") {
			partList.clear();
			// Deserialize parts from data attribute 'parts'
			for (String part : partStr.split(";")) {
				String[] partOpts = part.split("|");
				HuskPart huskPart = HuskPart.DeserializePart(partOpts, HuskRegistryHelper.getHelper(partOpts[0]));
				if (huskPart.parentModelIndex != -1) {
					huskPart.parentModel = partList.get(huskPart.parentModelIndex).model;
				}
				partList.add(huskPart);
			}
		}
	}
	
	@Override
	public void RegisterEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityHusk.class, new RenderHusk.RenderFactory());
	}
	
	@Override
	public MobType GetMobType() {
		return MobType.CUSTOM;
	}
	
	@Override
	protected void initEntityAI() {
		//this.tasks.addTask(0, new EntityAISwimming(this));
		//this.tasks.addTask(1, new EntityAITempt(this, 1.0D, false, Sets.newHashSet(ModItems.SOUL_SEEDS)));
		//this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
		//this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		//this.tasks.addTask(7, new EntityAILookIdle(this));
	}
	
}
