package com.zeropoints.ensoulomancy.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Entity used as viewpoint for custom client-side camera viewpoint renderer. 
 * Created by ChickenMobile 2018-09-15
 */
public class EntityCamera extends Entity {

	private int ticks;
	private Runnable onStoppedFunc;
	
	public EntityCamera(World world, int ticksActive) {
		super(world);
		
		ticks = ticksActive;
		
		this.onStoppedFunc = new Runnable() {
			@Override
			public void run() {} // Empty runnable
		};
	}
	
	public EntityCamera(World world, int ticksActive, Runnable onStopped) {
		super(world);
		ticks = ticksActive;
		this.onStoppedFunc = onStopped;
	}
	
	public int getTicksActive() {
		return ticks;
	}
	
	public void onStopped() {
		onStoppedFunc.run(); 
	}
	
	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
		return false; // Never render this entity
	}
	
}