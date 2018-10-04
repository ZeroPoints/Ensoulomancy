package com.zeropoints.ensoulomancy.items;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModGuiHandler;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.util.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;



public class ItemSpiritGuide extends Item implements IHasModel {

	private static final String name = "spirit_guide";
	
	public ItemSpiritGuide() {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ENSOULOMANCY_TAB);
		
		ModItems.ITEMS.add(this);
	}
	
	
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote) {
        	Main.log("Open Gui");
        	playerIn.openGui(Main.instance, ModGuiHandler.SPIRIT_GUIDE, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
    }



	@Override
	public void registerModels() {
		ModRenderers.registerRenderer(this, 0, name);

	}

	 
	 
}
