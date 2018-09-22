package com.zeropoints.ensoulomancy.items;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.blocks.BlockSoulSkull;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntityItemSoulSkullStackRenderer;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;
import com.zeropoints.ensoulomancy.util.IHasModel;
import com.zeropoints.ensoulomancy.util.SoulSkullType.SkullRegistryHelper;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulSkull extends Item implements IHasModel {
	
    private final String name = "soul_skull"; 

    public ItemSoulSkull() {
    	super();
    	this.setRegistryName("item_" + name);
		this.setUnlocalizedName(name);
    	
        this.setCreativeTab(Main.ENSOULOMANCY_TAB); // CHANGE THIS
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        
		ModItems.ITEMS.add(this);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
	public void registerModels() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoulSkull.class, new TileEntitySoulSkullRenderer());
    	this.setTileEntityItemStackRenderer(new TileEntityItemSoulSkullStackRenderer());
    	
    	// This makes sure we use the right custom item renderer for the skull type
		for (int i = 0; i < SkullRegistryHelper.SoulSkullTypes.length; ++i) {
			ModRenderers.registerRenderer(this, i, "inventory");
		}
	}

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing == EnumFacing.DOWN) {
            return EnumActionResult.FAIL;
        }
        else {
            if (worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos)) {
                facing = EnumFacing.UP;
                pos = pos.down();
            }
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(worldIn, pos);

            if (!flag) {
                if (!worldIn.getBlockState(pos).getMaterial().isSolid() && !worldIn.isSideSolid(pos, facing, true)) {
                    return EnumActionResult.FAIL;
                }

                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && ModBlocks.SOUL_SKULL.canPlaceBlockAt(worldIn, pos)) {
                if (worldIn.isRemote) {
                    return EnumActionResult.SUCCESS;
                }
                else {
                    worldIn.setBlockState(pos, ModBlocks.SOUL_SKULL.getDefaultState().withProperty(BlockSoulSkull.FACING, facing), 11);
                    int i = 0;

                    if (facing == EnumFacing.UP) {
                        i = MathHelper.floor((double)(player.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
                    }

                    TileEntity tileentity = worldIn.getTileEntity(pos);

                    if (tileentity instanceof TileEntitySoulSkull) {
                        TileEntitySoulSkull tileentityskull = (TileEntitySoulSkull)tileentity;
                        tileentityskull.setType(itemstack.getMetadata());
                        tileentityskull.setSkullRotation(i);
                    }

                    if (player instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                    }

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
            else {
                return EnumActionResult.FAIL;
            }
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
        	for (int i = 0; i < SkullRegistryHelper.SoulSkullTypes.length; ++i) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getMetadata();
        return this.getUnlocalizedName() + "." + SkullRegistryHelper.SoulSkullTypes[i].name;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack);
    }

    /**
     * Called when an ItemStack with NBT data is read to potentially that ItemStack's NBT data
     */
    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt) {
        return false;
    }
    
    /**
     * Can wear this?
     */
    @Override
  	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
  		return armorType == EntityEquipmentSlot.HEAD;
  	}

}