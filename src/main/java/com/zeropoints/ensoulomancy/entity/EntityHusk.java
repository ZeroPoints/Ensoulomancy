package com.zeropoints.ensoulomancy.entity;

import java.util.HashSet;
import java.util.Set;
import com.zeropoints.ensoulomancy.render.entity.husk.RenderHusk;
import com.zeropoints.ensoulomancy.util.IEntity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

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
