package com.zeropoints.soulcraft.items.tools;

import org.apache.logging.log4j.Level;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.api.morphs.AbstractMorph;
import com.zeropoints.soulcraft.api.morphs.EntityUtils;
import com.zeropoints.soulcraft.api.morphs.MorphAPI;
import com.zeropoints.soulcraft.api.morphs.MorphManager;
import com.zeropoints.soulcraft.capabilities.morphing.IMorphing;
import com.zeropoints.soulcraft.capabilities.morphing.Morphing;
import com.zeropoints.soulcraft.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
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

public class ReapingScythe extends ToolSword {

	public static final String NAME = "reaping_scythe";
	
	protected Boolean CAN_BEHEAD = true;
	protected float HEAD_DROP_CHANCE = 0.5F;
	
	public ReapingScythe() {
		super(NAME, ModItems.MATERIAL_SOUL_INGOT);
	}
	
	@Override
	protected void registerAbilities(){
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
        
        IMorphing capability = Morphing.getCapability(player);
        
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
        
        MorphAPI.morph(player, morph);
        
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
		
		IMorphing capability = Morphing.getCapability(player);
		if (capability != null && capability.isMorphed()) {
			MorphAPI.demorph(player);
		}
		
        return EnumActionResult.PASS;
    }
	
	
}
