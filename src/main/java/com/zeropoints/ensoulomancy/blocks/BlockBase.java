package com.zeropoints.ensoulomancy.blocks;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.init.ModRenderers;
import com.zeropoints.ensoulomancy.util.IHasModel;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {

	public BlockBase(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ENSOULOMANCY_TAB);

		ModBlocks.BLOCKS.add(this);
		thisItem = new ItemBlock(this).setRegistryName(getRegistryName());
		ModItems.ITEMS.add(thisItem);
	}
	
	private Item thisItem = null;
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(thisItem, 0, "inventory");
	}
	
}
