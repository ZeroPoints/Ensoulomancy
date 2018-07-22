package com.zeropoints.soulcraft.init;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;



public class ModGuiHandler implements IGuiHandler {

	
	public static final int PEDESTAL = 0;

	
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			default:
				return null;
		}
	}
	
	
	

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			default:
				return null;
		}
	}
	
	
	
	
}

