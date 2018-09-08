package com.zeropoints.ensoulomancy.world;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.Main;

import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.world.biome.Biome;

public class PurgatoryTeleporter extends Teleporter {

	public PurgatoryTeleporter(WorldServer worldIn, double x, double y, double z) {
		super(worldIn);

		this.worldServer = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	

    public final WorldServer worldServer;
    
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
	
	        //Get ground of dim
	        BlockPos pos = new BlockPos(x, y, z);
	        IBlockState bs = worldServer.getBlockState(pos);

	        Biome bi = worldServer.getBiome(pos);
	        if(bi.getBiomeName() == "VoidBiome") {
	        	Main.LogMesssage("VOID TELEPORTED");
	        	
	        	worldServer.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
	        	y++;
	        }
	        else {
		        	
		        for(int i = 256; i > 0; i--) {
		        	
			        pos = new BlockPos(x, i, z);
		        	bs = worldServer.getBlockState(pos);
		        	
		        	
		        	
		        	if(bs.getMaterial() != Material.AIR) {
		        		y = i+1;
		        		break;
		        	}
		        }
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

	
	
	
	
	
	
	
	/*
	
	@Override

    public boolean makePortal(Entity entityIn)
    {
        int i = 16;
        double d0 = -1.0D;
        int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posY);
        int l = MathHelper.floor(entityIn.posZ);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = this.random.nextInt(4);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        
        
        
        
        for (int j2 = j - 16; j2 <= j + 16; ++j2)
        {
            double d1 = (double)j2 + 0.5D - entityIn.posX;

            for (int l2 = l - 16; l2 <= l + 16; ++l2)
            {
                double d2 = (double)l2 + 0.5D - entityIn.posZ;
                label293:

                for (int j3 = this.world.getActualHeight() - 1; j3 >= 0; --j3)
                {
                    if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2)))
                    {
                        while (j3 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2)))
                        {
                            --j3;
                        }

                        for (int k3 = i2; k3 < i2 + 4; ++k3)
                        {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if (k3 % 4 >= 2)
                            {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for (int j4 = 0; j4 < 3; ++j4)
                            {
                                for (int k4 = 0; k4 < 4; ++k4)
                                {
                                    for (int l4 = -1; l4 < 4; ++l4)
                                    {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        blockpos$mutableblockpos.setPos(i5, j5, k5);

                                        if (l4 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos))
                                        {
                                            continue label293;
                                        }
                                    }
                                }
                            }

                            double d5 = (double)j3 + 0.5D - entityIn.posY;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;

                            if (d0 < 0.0D || d7 < d0)
                            {
                                d0 = d7;
                                i1 = j2;
                                j1 = j3;
                                k1 = l2;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }
        
        
        
        
        

        if (d0 < 0.0D)
        {
            for (int l5 = j - 16; l5 <= j + 16; ++l5)
            {
                double d3 = (double)l5 + 0.5D - entityIn.posX;

                for (int j6 = l - 16; j6 <= l + 16; ++j6)
                {
                    double d4 = (double)j6 + 0.5D - entityIn.posZ;
                    label231:

                    for (int i7 = this.world.getActualHeight() - 1; i7 >= 0; --i7)
                    {
                        if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6)))
                        {
                            while (i7 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6)))
                            {
                                --i7;
                            }

                            for (int k7 = i2; k7 < i2 + 2; ++k7)
                            {
                                int j8 = k7 % 2;
                                int j9 = 1 - j8;

                                for (int j10 = 0; j10 < 4; ++j10)
                                {
                                    for (int j11 = -1; j11 < 4; ++j11)
                                    {
                                        int j12 = l5 + (j10 - 1) * j8;
                                        int i13 = i7 + j11;
                                        int j13 = j6 + (j10 - 1) * j9;
                                        blockpos$mutableblockpos.setPos(j12, i13, j13);

                                        if (j11 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos))
                                        {
                                            continue label231;
                                        }
                                    }
                                }

                                double d6 = (double)i7 + 0.5D - entityIn.posY;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;

                                if (d0 < 0.0D || d8 < d0)
                                {
                                    d0 = d8;
                                    i1 = l5;
                                    j1 = i7;
                                    k1 = j6;
                                    l1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        
        
        
        
        
        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;

        if (l1 % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if (d0 < 0.0D)
        {
            j1 = MathHelper.clamp(j1, 70, this.world.getActualHeight() - 10);
            k2 = j1;

            for (int j7 = -1; j7 <= 1; ++j7)
            {
                for (int l7 = 1; l7 < 3; ++l7)
                {
                    for (int k8 = -1; k8 < 3; ++k8)
                    {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8;
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.world.setBlockState(new BlockPos(k9, k10, k11), flag ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        
        
        
        IBlockState iblockstate = Blocks.PORTAL.getDefaultState().withProperty(BlockPortal.AXIS, l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

        for (int i8 = 0; i8 < 4; ++i8)
        {
            for (int l8 = 0; l8 < 4; ++l8)
            {
                for (int l9 = -1; l9 < 4; ++l9)
                {
                    int l10 = i6 + (l8 - 1) * l6;
                    int l11 = k2 + l9;
                    int k12 = k6 + (l8 - 1) * i3;
                    boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                    this.world.setBlockState(new BlockPos(l10, l11, k12), flag1 ? Blocks.OBSIDIAN.getDefaultState() : iblockstate, 2);
                }
            }

            for (int i9 = 0; i9 < 4; ++i9)
            {
                for (int i10 = -1; i10 < 4; ++i10)
                {
                    int i11 = i6 + (i9 - 1) * l6;
                    int i12 = k2 + i10;
                    int l12 = k6 + (i9 - 1) * i3;
                    BlockPos blockpos = new BlockPos(i11, i12, l12);
                    this.world.notifyNeighborsOfStateChange(blockpos, this.world.getBlockState(blockpos).getBlock(), false);
                }
            }
        }

        return true;
    }
	
	*/
	
	

}
