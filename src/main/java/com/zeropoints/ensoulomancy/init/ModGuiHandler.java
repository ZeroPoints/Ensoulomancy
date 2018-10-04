package com.zeropoints.ensoulomancy.init;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.gui.spiritguide.ContainerSpiritGuide;
import com.zeropoints.ensoulomancy.gui.spiritguide.GuiSpiritGuide;
import com.zeropoints.ensoulomancy.items.ItemSpiritGuide;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {

	public static final int SPIRIT_GUIDE = 0;
	public static final int SOUL_BED = 1;
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case SPIRIT_GUIDE:
				ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
                if(!held.isEmpty()) {
                	if(held.getItem() instanceof ItemSpiritGuide) {
                		//Dunno if i need both cclient and server
                		Main.log("GET Server Container");
                        return new ContainerSpiritGuide((ItemSpiritGuide)held.getItem());
                    }
                }
                break;
			default:
				break;
		}
		
		return  null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case SPIRIT_GUIDE:
				ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
	            if(!held.isEmpty()) {
	            	if(held.getItem() instanceof ItemSpiritGuide) {
                		//Dunno if i need both cclient and server
	            		Main.log("GET Client Gui");
	                    return new GuiSpiritGuide(getServerGuiElement(ID, player, world, x, y, z));
	                }
	            }
	            break;
			default:
				break;
		}
	
	return  null;
	}
	
}

