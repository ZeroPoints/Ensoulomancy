package com.zeropoints.ensoulomancy.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.entity.ai.EntityAIFindItem;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.items.tools.ReapingScythe;
import com.zeropoints.ensoulomancy.render.entity.husk.RenderHusk;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderImp;
import com.zeropoints.ensoulomancy.render.entity.mobs.RenderImp.RenderFactory;
import com.zeropoints.ensoulomancy.util.IEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
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

public class EntityHusk extends EntityLiving implements IEntity {
	
	public HuskOptions options;
	
	public EntityHusk(World world, HuskOptions options) {
		super(world);
		this.setSize(options.height, options.width);
		this.options = options;
	}
	
	public EntityHusk(World world) {
		super(world);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        
        NBTTagCompound optionComp = new NBTTagCompound();
        optionComp.setString("head", options.head);
        optionComp.setString("body", options.body);
        
        // Go through all extra settings and append to tag list
        NBTTagList extraNBT = new NBTTagList();
        for (String item : options.extraOptions) {
        	NBTTagCompound extraOptionComp = new NBTTagCompound();
        	extraOptionComp.setString("key", item);
        	
        	extraNBT.appendTag(extraOptionComp);
        }
        optionComp.setTag("extra", extraNBT);

        // Set settings to NBT compound
        compound.setTag("options", optionComp);
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		
		HuskOptions options = new HuskOptions();
		
		NBTTagCompound optNBT = compound.getCompoundTag("options");
		options.body = optNBT.getString("body");
		options.head = optNBT.getString("head");
		
		NBTTagList extraTagList = optNBT.getTagList("extra", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < extraTagList.tagCount(); ++i) {
            options.extraOptions.add(extraTagList.getCompoundTagAt(i).getString("key"));
        }
        
        this.options = options;
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
		this.tasks.addTask(0, new EntityAISwimming(this));
		//this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
	}
	
	public static class HuskOptions {
		public Set<String> extraOptions = new HashSet<String>();
		
		public String head; // What entity head we should use
		public String body; // What entity body we should use
		
		public float width = 1.0F; // Bounding box width
		public float height = 1.0F; // Bounding box height
		
		public float scale = 1.0F; // scale relative to normal (1x1) creature size. TODO: Should this be attached somewhere else?
	}
}
