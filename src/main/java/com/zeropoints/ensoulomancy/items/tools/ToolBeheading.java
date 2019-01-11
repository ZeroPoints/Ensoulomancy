package com.zeropoints.ensoulomancy.items.tools;

import java.util.Random;

import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.model.husk.head.CatHead;
import com.zeropoints.ensoulomancy.model.husk.head.HorseHead;
import com.zeropoints.ensoulomancy.model.husk.head.LlamaHead;
import com.zeropoints.ensoulomancy.model.husk.head.ParrotHead;
import com.zeropoints.ensoulomancy.model.husk.head.RabbitHead;
import com.zeropoints.ensoulomancy.render.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// TODO: Change this to a enchantment attribute instead of assigning to an event???
public class ToolBeheading {
	
	private static final Random random = new Random();
	
		
	/**
	 * When a beheading weapon kills a mob and they drop items, drop a head item based on the item head drop chance
	 */
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		// Other mobs can behead other than players??
		Entity source = event.getSource().getTrueSource();
		if (source instanceof EntityLivingBase) {			
			Item item = ((EntityLivingBase)source).getHeldItem(EnumHand.MAIN_HAND).getItem();

			// Is capable of beheading
			if (item instanceof ToolSword && ((ToolSword)item).CAN_BEHEAD && ((ToolSword)item).HEAD_DROP_CHANCE > random.nextFloat()) {
				ItemStack head = getHeadDrop(event.getEntityLiving());
				
				if (head != null && !head.isEmpty() && !alreadyContainsDrop(event, head)) {
					EntityLivingBase entityLiving = event.getEntityLiving();
					EntityItem entityitem = new EntityItem(entityLiving.getEntityWorld(), entityLiving.posX, entityLiving.posY, entityLiving.posZ, head);
					entityitem.setDefaultPickupDelay();
					event.getDrops().add(entityitem);
				}
			}
		}
	}
	
	
	/**
	 * Check if a head has already dropped (would be weird if 2 heads dropped)
	 */
	private boolean alreadyContainsDrop(LivingDropsEvent event, ItemStack head) {
	    return event.getDrops().stream().map(EntityItem::getItem).anyMatch(drop -> ItemStack.areItemStacksEqual(drop, head));
	}
	
	/**
	 * Get the type of item to drop. Works for vanilla and custom skulls listed in the TileEntitySoulSkullRenderer class
	 */
	private ItemStack getHeadDrop(EntityLivingBase entity) {
		String entityName = EntityList.getEntityString(entity);
		//System.out.println(entityName + ", " + entity.getName());
		
		// Players are special - get their meta and return the head 
		if (entity instanceof EntityPlayer) {
	    	ItemStack head = new ItemStack(Items.SKULL, 1, 3);
	    	NBTUtil.writeGameProfile(head.getOrCreateSubCompound("SkullOwner"), ((EntityPlayer)entity).getGameProfile());
	    	return head;
	    }
		
		int variant;
		
		// For all other types of heads, use vanilla skull types for existing heads
		switch (entityName) {
			case "Skeleton": // meta 0: skeleton
				return new ItemStack(Items.SKULL, 1, 0);
			case "WitherSkeleton": // meta 1: wither skeleton
				return new ItemStack(Items.SKULL, 1, 1);
			case "Zombie": // meta 2: zombie
				return new ItemStack(Items.SKULL, 1, 2);
			case "Creeper": // meta 4: creeper
				return new ItemStack(Items.SKULL, 1, 4);
			case "EnderDragon": // meta 5: dragon
				return new ItemStack(Items.SKULL, 1, 5);
			case "Horse": // Horses have two textures layers. Variants are split by 256 + type
				variant = ((EntityHorse)entity).getHorseVariant(); 
				while(variant > 6) variant-=256;
				entityName += "." + HorseHead.subTypes[variant];
				break;
			case "Parrot":
				variant = ((EntityParrot)entity).getVariant();
				entityName += "." + ParrotHead.subTypes[variant];
				break;
			case "Llama":
				variant = ((EntityLlama)entity).getVariant();
				entityName += "." + LlamaHead.subTypes[variant];
				break;
			case "Ozelot":
				variant = ((EntityOcelot)entity).getTameSkin();
				entityName += "." + CatHead.subTypes[variant];
				break;
			case "Rabbit":
				variant = "Toast".equals(TextFormatting.getTextWithoutFormattingCodes(entity.getName())) ? 6 : ((EntityRabbit)entity).getRabbitType();
				entityName += "." + RabbitHead.subTypes[variant];
				break;
		}
		
		// Custom heads, look up using the meta index using SoulSkullTypeMap
		if (HuskModelHelper.HuskRegistryHelper.TypeMap.containsKey(entityName)) {
			return new ItemStack(ModItems.SOUL_SKULL, 1, HuskModelHelper.HuskRegistryHelper.TypeMap.get(entityName));
		}
	    
	    // Entity does not have a skull drop
	    return null;
	}

}
