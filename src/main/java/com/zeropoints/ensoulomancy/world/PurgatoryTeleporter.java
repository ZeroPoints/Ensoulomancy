package com.zeropoints.ensoulomancy.world;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class PurgatoryTeleporter extends Teleporter {

	public PurgatoryTeleporter(WorldServer worldIn, double x, double y, double z) {
		super(worldIn);

		this.worldServer = world;
		
		this.x = x;
		this.y = y;
		this.z = z;
	}

	

    private final WorldServer worldServer;
    
    private double x;
    private double y;
    private double z;
    

	
	public static void teleportToDimension(Entity entity, int dimensionId, double x, double y, double z) {
		if (entity instanceof EntityPlayerMP) {
	        int oldDimension = entity.getEntityWorld().provider.getDimension();
	        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
	        MinecraftServer server = entity.getEntityWorld().getMinecraftServer();
	        WorldServer worldServer = server.getWorld(dimensionId);
	        //entity.addExperienceLevel(0);
	
	        if (worldServer == null || worldServer.getMinecraftServer() == null){ //Dimension doesn't exist
	            throw new IllegalArgumentException("Dimension: "+dimensionId+" doesn't exist!");
	        }
	
	        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimensionId, new PurgatoryTeleporter(worldServer, x, y, z));
	        entity.setPositionAndUpdate(x, y, z);
	        if (oldDimension == 1) {
	            // For some reason teleporting out of the end does weird things. Compensate for that
	            entity.setPositionAndUpdate(x, y, z);
	            worldServer.spawnEntity(entity);
	            worldServer.updateEntityWithOptionalForce(entity, false);
	        }
		}
    }
	
	
	
	@Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        // The main purpose of this function is to *not* create a nether portal
        this.world.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));

        entityIn.setPosition(this.x, this.y, this.z);
        entityIn.motionX = 0.0f;
        entityIn.motionY = 0.0f;
        entityIn.motionZ = 0.0f;
    }

	

}
