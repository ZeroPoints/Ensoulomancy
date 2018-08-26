package com.zeropoints.soulcraft.items.tools;

import java.util.HashMap;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ToolSword extends ItemSword implements IHasModel {
	
	/* For general abilities */ 
	public HashMap<String,Object> Abilities = new HashMap<String,Object>();
	
	protected Boolean CAN_BEHEAD = false;
	protected float HEAD_DROP_CHANCE = 0.0F;
	
	/*
	 * 
	 */
	public ToolSword(String name, ToolMaterial material) {
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.SOULCRAFT_TAB);
		
		this.registerAbilities();
		
		ModItems.ITEMS.add(this);
	}
	
	/*
	 * Override this method to register abilities in the map
	 */
	protected void registerAbilities() {
		
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "invenory");
	}
	
}
