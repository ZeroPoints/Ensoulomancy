package com.zeropoints.ensoulomancy.tileentity;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.blocks.BlockSoulBed;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySoulBed extends TileEntity {
	
    public final static String resourceLocation = Reference.MOD_ID + ":soul_bed";

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
    }
    
    public void readUpdateTag(NBTTagCompound compound) {
    }

    @SideOnly(Side.CLIENT)
    public boolean isHeadPiece() {
        return BlockSoulBed.isHeadPiece(this.getBlockMetadata());
    }
}