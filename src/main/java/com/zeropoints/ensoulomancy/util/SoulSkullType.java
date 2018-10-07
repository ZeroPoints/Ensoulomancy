package com.zeropoints.ensoulomancy.util;

import java.util.HashMap;
import java.util.Map;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;
import com.zeropoints.ensoulomancy.model.heads.*;
import net.minecraft.util.ResourceLocation;

public class SoulSkullType {
	public final String name;
	public final String entityname;
	public final ResourceLocation texture;
	private final String _texture;
	public ModelHeadBase model;
	public final Class<? extends ModelHeadBase> headClass;

	public SoulSkullType(String name, String texture, Class<? extends ModelHeadBase> headClass) {
		this.name = name.toLowerCase();
		this.entityname = name;
		this._texture = texture;
		this.texture = new ResourceLocation(texture + ".png");
		this.headClass = headClass;
	}

	
	public static class SkullRegistryHelper {
		
		/**
		 * a map that matches entityName to resource location and head class for all skull types.
		 */
		public static final Map<String,Integer> SoulSkullTypeMap = new HashMap<String,Integer>();
		
		/**
		 * A list <EntityName,resource,headClass> for all types of entities matching up to their relevant head model
		 * Entities are named in format: Entity[.variant_name]
		 */
		public static final SoulSkullType[] SoulSkullTypes = {
			new SoulSkullType("Bat", "textures/entity/bat", ModelBatHead.class),
			new SoulSkullType("Blaze", "textures/entity/blaze", ModelBlazeHead.class),
			new SoulSkullType("Chicken", "textures/entity/chicken", ModelChickenHead.class),
			new SoulSkullType("Cow", "textures/entity/cow/cow", ModelCowHead.class),
			new SoulSkullType("MushroomCow", "textures/entity/cow/mooshroom", ModelCowHead.class),
			new SoulSkullType("Enderman", "textures/entity/enderman/enderman", ModelEndermanHead.class),
			new SoulSkullType("Endermite", "textures/entity/endermite", ModelEnderMiteHead.class),
			new SoulSkullType("Ghast", "textures/entity/ghast/ghast", ModelGhastHead.class),
			new SoulSkullType("Guardian", "textures/entity/guardian", ModelGuardianHead.class),
			new SoulSkullType("GuardianElder", "textures/entity/guardian_elder", ModelGuardianHead.class),
			new SoulSkullType("Horse.horse_white", "textures/entity/horse/horse_white", ModelHorseHead.class),
			new SoulSkullType("Horse.horse_creamy", "textures/entity/horse/horse_creamy", ModelHorseHead.class),
			new SoulSkullType("Horse.horse_chestnut", "textures/entity/horse/horse_chestnut", ModelHorseHead.class),
			new SoulSkullType("Horse.horse_black", "textures/entity/horse/horse_black", ModelHorseHead.class),
			new SoulSkullType("Horse.horse_gray", "textures/entity/horse/horse_gray", ModelHorseHead.class),
			new SoulSkullType("Horse.horse_darkbrown", "textures/entity/horse/horse_darkbrown", ModelHorseHead.class),
			new SoulSkullType("Donkey", "textures/entity/horse/donkey", ModelHorseHead.class),
			new SoulSkullType("Mule", "textures/entity/horse/mule", ModelHorseHead.class),
			new SoulSkullType("SkeletonHorse", "textures/entity/horse/horse_skeleton", ModelHorseHead.class),
			new SoulSkullType("ZombieHorse", "textures/entity/horse/horse_zombie", ModelHorseHead.class),
			new SoulSkullType("Husk", "textures/entity/zombie/husk", ModelZombieHead.class),
			new SoulSkullType("EvokerIllager", "textures/entity/illager/evoker", ModelIllagerHead.class),
			new SoulSkullType("VindicatorIllager", "textures/entity/illager/vindicator", ModelIllagerHead.class),
			new SoulSkullType("LavaSlime", "textures/entity/slime/magmacube", ModelMagmaCubeHead.class),
			new SoulSkullType("Llama.default", "textures/entity/llama/llama", ModelLlamaHead.class),
			new SoulSkullType("Llama.llama_brown", "textures/entity/llama/llama_brown", ModelLlamaHead.class),
			new SoulSkullType("Llama.llama_creamy", "textures/entity/llama/llama_creamy", ModelLlamaHead.class),
			new SoulSkullType("Llama.llama_gray", "textures/entity/llama/llama_gray", ModelLlamaHead.class),
			new SoulSkullType("Llama.llama_white", "textures/entity/llama/llama_white", ModelLlamaHead.class),
			new SoulSkullType("Ozelot.ocelot", "textures/entity/cat/ocelot", ModelCatHead.class),
			new SoulSkullType("Ozelot.black", "textures/entity/cat/black", ModelCatHead.class),
			new SoulSkullType("Ozelot.red", "textures/entity/cat/red", ModelCatHead.class),
			new SoulSkullType("Ozelot.siamese", "textures/entity/cat/siamese", ModelCatHead.class),
			new SoulSkullType("Parrot.parrot_blue", "textures/entity/parrot/parrot_blue", ModelParrotHead.class),
			new SoulSkullType("Parrot.parrot_green", "textures/entity/parrot/parrot_green", ModelParrotHead.class),
			new SoulSkullType("Parrot.parrot_grey", "textures/entity/parrot/parrot_grey", ModelParrotHead.class),
			new SoulSkullType("Parrot.parrot_red_blue", "textures/entity/parrot/parrot_red_blue", ModelParrotHead.class),
			new SoulSkullType("Parrot.parrot_yellow_blue", "textures/entity/parrot/parrot_yellow_blue", ModelParrotHead.class),
			new SoulSkullType("Pig", "textures/entity/pig/pig", ModelPigHead.class),
			new SoulSkullType("PigZombie", "textures/entity/zombie_pigman", ModelZombiePigHead.class),
			new SoulSkullType("PolarBear", "textures/entity/bear/polarbear", ModelPolarBearHead.class),
			new SoulSkullType("Rabbit.black", "textures/entity/rabbit/black", ModelRabbitHead.class),
			new SoulSkullType("Rabbit.brown", "textures/entity/rabbit/brown", ModelRabbitHead.class),
			new SoulSkullType("Rabbit.caerbannog", "textures/entity/rabbit/caerbannog", ModelRabbitHead.class),
			new SoulSkullType("Rabbit.gold", "textures/entity/rabbit/gold", ModelRabbitHead.class),
			new SoulSkullType("Rabbit.toast", "textures/entity/rabbit/toast", ModelRabbitHead.class),
			new SoulSkullType("Rabbit.white", "textures/entity/rabbit/white", ModelRabbitHead.class),
			new SoulSkullType("Rabbit.white_splotched", "textures/entity/rabbit/white_splotched", ModelRabbitHead.class),
			new SoulSkullType("Sheep", "textures/entity/sheep/sheep", ModelSheepHead.class),
			new SoulSkullType("Shulker", "textures/entity/shulker/shulker_black", ModelShulkerHead.class),
			new SoulSkullType("Silverfish", "textures/entity/silverfish", ModelSilverfishHead.class),
			new SoulSkullType("Slime", "textures/entity/slime/slime", ModelSlimeHead.class),
			new SoulSkullType("SnowMan", "textures/entity/snowman", ModelSnowManHead.class),
			new SoulSkullType("Spider", "textures/entity/spider/spider", ModelSpiderHead.class),
			new SoulSkullType("CaveSpider", "textures/entity/spider/cave_spider", ModelSpiderHead.class),
			new SoulSkullType("Squid", "textures/entity/squid", ModelSquidHead.class),
			new SoulSkullType("Stray", "textures/entity/skeleton/stray", ModelSkeletonHead.class),
			new SoulSkullType("Vex", "textures/entity/illager/vex", ModelVexHead.class),
			new SoulSkullType("VillagerGolem", "textures/entity/iron_golem", ModelIronGolemHead.class),
			new SoulSkullType("Villager", "textures/entity/villager/villager", ModelVillagerHead.class),
			new SoulSkullType("Witch", "textures/entity/witch", ModelWitchHead.class),
			new SoulSkullType("Wolf", "textures/entity/wolf/wolf", ModelWolfHead.class),
		};
		
		static {
			for (int i = 0; i < SkullRegistryHelper.SoulSkullTypes.length; ++i) {
				// Name is the localizedname of the entity. Minecraft entity names are camel-cased
				SkullRegistryHelper.SoulSkullTypeMap.put(SkullRegistryHelper.SoulSkullTypes[i].entityname, i); 
			}
		}
	}
}
