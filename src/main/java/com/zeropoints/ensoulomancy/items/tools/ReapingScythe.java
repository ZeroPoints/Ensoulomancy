package com.zeropoints.ensoulomancy.items.tools;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import mchorse.metamorph.api.EntityUtils;
import mchorse.metamorph.api.MorphAPI;
import mchorse.metamorph.api.MorphManager;
import mchorse.metamorph.api.morphs.AbstractMorph;
import mchorse.metamorph.capabilities.morphing.*;


public class ReapingScythe extends ToolSword {

	private static final String name = "reaping_scythe";
	
	public ReapingScythe() {
		super(name, ModItems.MATERIAL_SOUL_INGOT);
		this.CAN_BEHEAD = true;
		this.HEAD_DROP_CHANCE = 0.5F;
	}
	
	@Override
	protected void registerAbilities() {
		this.Abilities.put("POSSESS", true);
	}
	

	/**
	 * When right-clicking a living entity with this item, possess the creature
	 */
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        if (target.world.isRemote || !target.isNonBoss() || !target.isEntityAlive() ||
        		target instanceof EntityPlayer || target instanceof FakePlayer) {
        	return false;
        }
        
        
        
        IMorphing capability = Morphing.get(player);
        
        // Cannot morph if currently possessing
        if (capability == null || capability.isMorphed()) {
            return false;
        }

        String name = MorphManager.INSTANCE.morphNameFromEntity(target);

        if (!MorphManager.INSTANCE.hasMorph(name)) {
            Main.log(Level.WARN, "Morph by key '" + name + "' doesn't exist!");
            return false;
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("Name", name);
        tag.setTag("EntityData", EntityUtils.stripEntityNBT(target.serializeNBT()));

        AbstractMorph morph = MorphManager.INSTANCE.morphFromNBT(tag);
        
        MorphAPI.morph(player, morph, true);
        
        target.setDead(); // Remove entity from world
        
        return true;
    }
	
	/**
     * Called when a Block is right-clicked with this Item
     * shift-right clicking will demorph the player
     */
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.world.isRemote || !player.isSneaking()) {
			return EnumActionResult.PASS;
		}
		
		IMorphing capability = Morphing.get(player);
		if (capability != null && capability.isMorphed()) {
			MorphAPI.demorph(player);
		}
		
        return EnumActionResult.PASS;
    }
	
	
}
