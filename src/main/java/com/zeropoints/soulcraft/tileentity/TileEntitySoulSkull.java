package com.zeropoints.soulcraft.tileentity;

import javax.annotation.Nullable;

import com.zeropoints.soulcraft.blocks.BlockSoulSkull;
import com.zeropoints.soulcraft.util.Reference;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySoulSkull extends TileEntity {
	
    private int skullType;
    private int skullRotation;
    public final static String resourceLocation = Reference.MOD_ID + ":soul_skull";
    
    //TODO: It feels like a lot of this code just does the same thing twice, optimize??
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.writeUpdateTag(compound);
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readUpdateTag(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
    	NBTTagCompound compound = new NBTTagCompound();
    	this.writeUpdateTag(compound);
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), compound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	NBTTagCompound compound = pkt.getNbtCompound();
    	readUpdateTag(compound);
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
    	NBTTagCompound compound = super.getUpdateTag();
    	writeUpdateTag(compound);
    	return compound;
    }
    
    public void writeUpdateTag(NBTTagCompound compound) {
    	compound.setByte("SkullType", (byte)(this.skullType & 255));
    	compound.setByte("Rot", (byte)(this.skullRotation & 255));
    }
    
    public void readUpdateTag(NBTTagCompound compound) {
    	setType(compound.getByte("SkullType"));
        setSkullRotation(compound.getByte("Rot"));
    }

    public void setType(int type) {
        this.skullType = type;
        markDirty();
    }

    public int getSkullType() {
        return this.skullType;
    }

    public void setSkullRotation(int rotation) {
        this.skullRotation = rotation;
        markDirty(); // Do we need this??
    }
    
    @SideOnly(Side.CLIENT)
    public int getSkullRotation() {
        return this.skullRotation;
    }

    @Override
    public void mirror(Mirror mirrorIn) {
        if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockSoulSkull.FACING) == EnumFacing.UP) {
            setSkullRotation(mirrorIn.mirrorRotation(this.skullRotation, 16));
        }
    }

    @Override
    public void rotate(Rotation rotationIn) {
        if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockSoulSkull.FACING) == EnumFacing.UP) {
            setSkullRotation(rotationIn.rotate(this.skullRotation, 16));
        }
    }
}