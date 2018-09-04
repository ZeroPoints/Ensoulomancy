package com.zeropoints.ensoulomancy.tileentity;

import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.block.BlockBed;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySoulBed extends TileEntityBed {
	
    public final static String resourceLocation = Reference.MOD_ID + ":soul_bed";

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 11, this.getUpdateTag());
    }

    @SideOnly(Side.CLIENT)
    public boolean isHeadPiece() {
        return BlockBed.isHeadPiece(this.getBlockMetadata());
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemStack(ModItems.SOUL_BED, 1);
    }
}