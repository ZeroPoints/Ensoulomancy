package com.zeropoints.ensoulomancy.items.tools;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;



public class Bla extends ToolSword {

	public Bla() {
		super("bla", ModItems.MATERIAL_SOUL_INGOT);
	}
	
	@Override
	protected void registerAbilities() {
	}
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		EntityHusk husk = new EntityHusk(world);
		husk.setPosition(player.posX, player.posY, player.posZ);
		
		if (!player.world.isRemote) {
			world.spawnEntity(husk);
			husk.getDataManager().set(husk.souls, "Guardian,Bat"); // Hopefully this causes a change? 
			husk.getDataManager().setDirty(husk.souls);
		}
		
		return EnumActionResult.PASS;
	}
	
}
