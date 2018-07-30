package com.zeropoints.soulcraft.items.tools;

import java.util.Random;

import com.mojang.authlib.GameProfile;
import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.init.ModItems;
import com.zeropoints.soulcraft.model.heads.ModelCatHead;
import com.zeropoints.soulcraft.model.heads.ModelHorseHead;
import com.zeropoints.soulcraft.model.heads.ModelLlamaHead;
import com.zeropoints.soulcraft.model.heads.ModelParrotHead;
import com.zeropoints.soulcraft.model.heads.ModelRabbitHead;
import com.zeropoints.soulcraft.renderer.tileentity.TileEntitySoulSkullRenderer;
import com.zeropoints.soulcraft.util.SoulSkullType;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;

// TODO: Change this to a enchantment attribute instead of assigning to an event???
public class ToolBeheading {
	
	private static final Random random = new Random();
	
		
	/**
	 * When a beheading weapon kills a mob and they drop items, drop a head item based on the item head drop chance
	 */
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		// Other mobs can behead other than players??
		if(event.getSource().getTrueSource() instanceof EntityPlayer) {			
			Item item = ((EntityLivingBase) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND).getItem();

			// Is capable of beheading
			if(item instanceof ToolSword && ((ToolSword)item).CAN_BEHEAD) {
				ItemStack head = getHeadDrop(event.getEntityLiving());
				
				if(head != null && !head.isEmpty() && !alreadyContainsDrop(event, head) && ((ToolSword)item).HEAD_DROP_CHANCE > random.nextFloat()) {
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
	
	/*
	 * Get the type of item to drop. Works for vanilla and custom skulls listed in the TileEntitySoulSkullRenderer class
	 */
	private ItemStack getHeadDrop(EntityLivingBase entity) {
		String entityName = EntityList.getEntityString(entity);
		System.out.println(entityName + ", " + entity.getName());
		
		// Players are special - get their meta and return the special head 
		if(entity instanceof EntityPlayer) {
	    	ItemStack head = new ItemStack(Items.SKULL, 1, 3);
	    	NBTUtil.writeGameProfile(head.getOrCreateSubCompound("SkullOwner"), ((EntityPlayer)entity).getGameProfile());
	    	return head;
	    }
		
		int variant;
		
		// For all other types of heads, use vanilla skull types for existing heads
		switch(entityName) {
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
				entityName += "." + ModelHorseHead.subTypes[variant];
				break;
			case "Parrot":
				variant = ((EntityParrot)entity).getVariant();
				entityName += "." + ModelParrotHead.subTypes[variant];
				break;
			case "Llama":
				variant = ((EntityLlama)entity).getVariant();
				entityName += "." + ModelLlamaHead.subTypes[variant];
				break;
			case "Ozelot":
				variant = ((EntityOcelot)entity).getTameSkin();
				entityName += "." + ModelCatHead.subTypes[variant];
				break;
			case "Rabbit":
				variant = "Toast".equals(TextFormatting.getTextWithoutFormattingCodes(entity.getName())) ? 6 : ((EntityRabbit)entity).getRabbitType();
				entityName += "." + ModelRabbitHead.subTypes[variant];
				break;
		}
		
		// Custom heads, look up using the meta index using SoulSkullTypeMap
		if(TileEntitySoulSkullRenderer.SoulSkullTypeMap.containsKey(entityName)) {
			return new ItemStack(ModItems.SOUL_SKULL, 1, TileEntitySoulSkullRenderer.SoulSkullTypeMap.get(entityName));
		}
	    
	    // Entity does not have a skull drop
	    return null;
	}

}
