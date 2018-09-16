package com.zeropoints.ensoulomancy.util;

import com.zeropoints.ensoulomancy.render.player.EntityCamera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CameraRenderHelper {
	
	private static EntityCamera camera;	
	
	public static void createCamera(BlockPos pos, EnumFacing facing, Runnable onStopped) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		camera = new EntityCamera(Minecraft.getMinecraft().world, 300, onStopped);
		pos = pos.offset(facing.getOpposite(), 5);
		
		camera.setPositionAndRotation(pos.getX() + 0.5F, pos.getY() + 2, pos.getZ() + 0.5F, getYaw(facing), 30);
		Minecraft.getMinecraft().world.spawnEntity(camera);
		
		// Make sure the player doesn't move around here. Does it work?
		if (player.world.isRemote) {
			KeyBinding.unPressAllKeys();
			Minecraft.getMinecraft().setRenderViewEntity(camera);
		}
	}
	
	public static float getYaw(EnumFacing facing) {
		float yaw;
		if (facing.getFrontOffsetX() != 0) {
			yaw = facing.getFrontOffsetX() == 1 ? 270 : 90;
		}
		else {
			yaw = facing.getFrontOffsetZ() == 1 ? 0 : 180;
		}
		
		return yaw;
	}

}