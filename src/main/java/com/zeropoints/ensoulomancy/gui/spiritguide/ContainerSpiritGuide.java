package com.zeropoints.ensoulomancy.gui.spiritguide;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.items.ItemSpiritGuide;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerSpiritGuide extends Container {

	
	/*
	 * NO IDEA IF THIS IS NEEDED. do testing.
	 */

	public ContainerSpiritGuide(final ItemSpiritGuide guide) {
		Main.log("New Container");
	
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	
	
	
	
}
