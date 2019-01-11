package com.zeropoints.ensoulomancy.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.model.husk.*;
import com.zeropoints.ensoulomancy.model.husk.HuskBase.PartType;
import com.zeropoints.ensoulomancy.model.husk.HuskBodyBase.BodyType;
import com.zeropoints.ensoulomancy.model.husk.body.*;
import com.zeropoints.ensoulomancy.model.husk.head.*;
import com.zeropoints.ensoulomancy.model.husk.legs.*;
import com.zeropoints.ensoulomancy.model.husk.attack.*;
import com.zeropoints.ensoulomancy.model.husk.utility.*;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskPart;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskRegistryHelper;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.tpe.TypeMaps.TypeMap;

public class HuskModelHelper {
	
	public final String name; // Name of the entity
	public final String entityname; // Capitilized entity name (as it comes back originally)
	private final String trueEntityName; // The real name of the entity without variation type
	public final ResourceLocation texture; // Texture location of this part when rendering
	
	public int headIdx = -1;
	public int bodyIdx = -1;
	public int legsIdx = -1;
	public int utilityIdx = -1; // Utility 	e.g. Cow Udder
	public int attackIdx = -1; 	// Attack 	e.g. Guardian Eye
	
	/* list of all attached parts for this particular model as given by the 'HuskModelHelper[] Types' variable list below */
	public List<HuskBase> parts = new ArrayList<>();

	public <T extends HuskBase> HuskModelHelper(String name, String texture, T ...args) {
		this.name = name.toLowerCase();
		this.entityname = name;
		this.trueEntityName = name.split(",")[0];
		
		this.texture = new ResourceLocation(texture + ".png");
		
		// Initialize Models -----------------------------------------------
		// Loop through all classes and determine all the parts within it
		for (int i = 0; i < args.length; i++) {
			T arg = args[i];
			parts.add(arg);
			
			// Assign these for easy use. 
			// Each of these options will only be set once so a pointer will help performance out here.
			if (arg instanceof HuskBodyBase || arg.isAlsoBody) {
				bodyIdx = i;
			}
			if (arg instanceof HuskHeadBase) {
				headIdx = i;
			}
			if (arg instanceof HuskLegsBase) {
				legsIdx = i;
			}
			if (arg instanceof HuskUtilityBase) {
				utilityIdx = i;
			}
			if (arg instanceof HuskAttackBase) {
				attackIdx = i;
			}
		}
	}

	
	
	
	public static class HuskRegistryHelper {
		
		/**
		 * Return the helper from the entity name / variant
		 */
		public static HuskModelHelper getHelper(String type) {
			if (TypeMap.containsKey(type)) {
				return Types[TypeMap.get(type)];
			}
			return null; // Return nothing
		}
		
		/**
		 * a map that matches entityName to resource location and head class for all skull types.
		 */
		public static final Map<String,Integer> TypeMap = new HashMap<String,Integer>();
		
		// Shared models -- HEADS
		private final static CowHead cowHead = new CowHead();
		private final static GuardianHead guardianHead = new GuardianHead();
		private final static HorseHead horseHead = new HorseHead();
		private final static IllagerHead illagerHead = new IllagerHead();
		private final static LlamaHead llamaHead = new LlamaHead();
		private final static CatHead catHead = new CatHead();
		private final static ParrotHead parrotHead = new ParrotHead();
		private final static RabbitHead rabbitHead = new RabbitHead();
		private final static SpiderHead spiderHead = new SpiderHead();
		
		// Shared models -- LEGS
		private final static CowLegs cowLegs = new CowLegs();
		private final static SpiderLegs spiderLegs = new SpiderLegs();
		private final static GuardianTail guardianTail = new GuardianTail();
		
		// Shared models -- ATTACK
		private final static GuardianEye guardianEye = new GuardianEye();
		
		/**
		 * A list <EntityName,resource,headClass> for all types of entities matching up to their relevant head model
		 * Entities are named in format: Entity[.variant_name]
		 * BODY should always go first, then HEAD, LEGS, UTILITY, ATTACK -- ??
		 */
		public static final HuskModelHelper[] Types = {
			new HuskModelHelper("Bat", "textures/entity/bat", new BatHead(), new BatWings()),
			new HuskModelHelper("Blaze", "textures/entity/blaze", new BlazeBody(), new BlazeHead()),
			new HuskModelHelper("Chicken", "textures/entity/chicken", new ChickenBody(), new ChickenHead(), new ChickenLegs(), new ChickenWings()),
			new HuskModelHelper("Cow", "textures/entity/cow/cow", cowHead, cowLegs),
			new HuskModelHelper("MushroomCow", "textures/entity/cow/mooshroom", cowHead, cowLegs),
			new HuskModelHelper("Enderman", "textures/entity/enderman/enderman", new EndermanHead()),
			new HuskModelHelper("Endermite", "textures/entity/endermite", new EndermiteHead()),
			new HuskModelHelper("Ghast", "textures/entity/ghast/ghast", new GhastHead()),
			new HuskModelHelper("Guardian", "textures/entity/guardian", guardianHead, guardianTail, guardianEye),
			new HuskModelHelper("GuardianElder", "textures/entity/guardian_elder", guardianHead, guardianTail, guardianEye),
			new HuskModelHelper("Horse.horse_white", "textures/entity/horse/horse_white", horseHead),
			new HuskModelHelper("Horse.horse_creamy", "textures/entity/horse/horse_creamy", horseHead),
			new HuskModelHelper("Horse.horse_chestnut", "textures/entity/horse/horse_chestnut", horseHead),
			new HuskModelHelper("Horse.horse_black", "textures/entity/horse/horse_black", horseHead),
			new HuskModelHelper("Horse.horse_gray", "textures/entity/horse/horse_gray", horseHead),
			new HuskModelHelper("Horse.horse_darkbrown", "textures/entity/horse/horse_darkbrown", horseHead),
			new HuskModelHelper("Donkey", "textures/entity/horse/donkey", horseHead),
			new HuskModelHelper("Mule", "textures/entity/horse/mule", horseHead),
			new HuskModelHelper("SkeletonHorse", "textures/entity/horse/horse_skeleton", horseHead),
			new HuskModelHelper("ZombieHorse", "textures/entity/horse/horse_zombie", horseHead),
			new HuskModelHelper("Husk", "textures/entity/zombie/husk", new ZombieHead()),
			new HuskModelHelper("EvokerIllager", "textures/entity/illager/evoker", illagerHead),
			new HuskModelHelper("VindicatorIllager", "textures/entity/illager/vindicator", illagerHead),
			new HuskModelHelper("LavaSlime", "textures/entity/slime/magmacube", new MagmacubeHead()),
			new HuskModelHelper("Llama.default", "textures/entity/llama/llama", llamaHead),
			new HuskModelHelper("Llama.llama_brown", "textures/entity/llama/llama_brown", llamaHead),
			new HuskModelHelper("Llama.llama_creamy", "textures/entity/llama/llama_creamy", llamaHead),
			new HuskModelHelper("Llama.llama_gray", "textures/entity/llama/llama_gray", llamaHead),
			new HuskModelHelper("Llama.llama_white", "textures/entity/llama/llama_white", llamaHead),
			new HuskModelHelper("Ozelot.ocelot", "textures/entity/cat/ocelot", catHead),
			new HuskModelHelper("Ozelot.black", "textures/entity/cat/black", catHead),
			new HuskModelHelper("Ozelot.red", "textures/entity/cat/red", catHead),
			new HuskModelHelper("Ozelot.siamese", "textures/entity/cat/siamese", catHead),
			new HuskModelHelper("Parrot.parrot_blue", "textures/entity/parrot/parrot_blue", parrotHead),
			new HuskModelHelper("Parrot.parrot_green", "textures/entity/parrot/parrot_green", parrotHead),
			new HuskModelHelper("Parrot.parrot_grey", "textures/entity/parrot/parrot_grey", parrotHead),
			new HuskModelHelper("Parrot.parrot_red_blue", "textures/entity/parrot/parrot_red_blue", parrotHead),
			new HuskModelHelper("Parrot.parrot_yellow_blue", "textures/entity/parrot/parrot_yellow_blue", parrotHead),
			new HuskModelHelper("Pig", "textures/entity/pig/pig", new PigHead()),
			new HuskModelHelper("PigZombie", "textures/entity/zombie_pigman", new ZombiepigHead()),
			new HuskModelHelper("PolarBear", "textures/entity/bear/polarbear", new PolarbearHead()),
			new HuskModelHelper("Rabbit.black", "textures/entity/rabbit/black", rabbitHead),
			new HuskModelHelper("Rabbit.brown", "textures/entity/rabbit/brown", rabbitHead),
			new HuskModelHelper("Rabbit.caerbannog", "textures/entity/rabbit/caerbannog", rabbitHead),
			new HuskModelHelper("Rabbit.gold", "textures/entity/rabbit/gold", rabbitHead),
			new HuskModelHelper("Rabbit.toast", "textures/entity/rabbit/toast", rabbitHead),
			new HuskModelHelper("Rabbit.white", "textures/entity/rabbit/white", rabbitHead),
			new HuskModelHelper("Rabbit.white_splotched", "textures/entity/rabbit/white_splotched", rabbitHead),
			new HuskModelHelper("Sheep", "textures/entity/sheep/sheep", new SheepHead()),
			new HuskModelHelper("Shulker", "textures/entity/shulker/shulker_black", new ShulkerHead()),
			new HuskModelHelper("Silverfish", "textures/entity/silverfish", new SilverfishHead()),
			new HuskModelHelper("Slime", "textures/entity/slime/slime", new SlimeHead()),
			new HuskModelHelper("SnowMan", "textures/entity/snowman", new SnowmanHead()),
			new HuskModelHelper("Spider", "textures/entity/spider/spider", spiderHead, spiderLegs),
			new HuskModelHelper("CaveSpider", "textures/entity/spider/cave_spider", spiderHead, spiderLegs),
			new HuskModelHelper("Squid", "textures/entity/squid", new SquidHead()),
			new HuskModelHelper("Stray", "textures/entity/skeleton/stray", new SkeletonHead()),
			new HuskModelHelper("Vex", "textures/entity/illager/vex", new VexHead()),
			new HuskModelHelper("VillagerGolem", "textures/entity/iron_golem", new IrongolemHead()),
			new HuskModelHelper("Villager", "textures/entity/villager/villager", new VillagerHead()),
			new HuskModelHelper("Witch", "textures/entity/witch", new WitchHead()),
			new HuskModelHelper("Wolf", "textures/entity/wolf/wolf", new WolfHead()),
		};
		
		static {
			// put each of our models into a hashmap to retrieve easily through a name
			// models with sub-types will be classified under the same type
			for (int i = 0; i < HuskRegistryHelper.Types.length; ++i) {
				// This item
				HuskModelHelper type = HuskRegistryHelper.Types[i];
				
				// Name is the localizedname of the entity. Minecraft entity names are camel-cased
				HuskRegistryHelper.TypeMap.put(type.entityname, i); 
			}
		}	
	}
	
	
	
	
	/**
	 * @author ChickenMobile
	 * A class which incorporates all the shared information for a model.
	 * The renderer will loop through these in order to draw a full husk model
	 */
	public static class HuskPart {
		public final HuskModelHelper helper;
		public HuskBase model; // The model we shall render. Keep for referencing
		public int parentModelIndex = -1; // The index of the parent model in the Part list. -1 means it has no parent
		public HuskBase parentModel; // The parent model of this. Not sure if will use 
		public float scale; // The scale we want to render this item to. Should be based on the body / first element comparitive to this default size
		public boolean applyFacing; // Whether facing is relevant for this body part, otherwise it will pick the default part to attach to 
		public EnumFacing enumFacing; // Face direction of certain models (D-U-N-S-W-E). Models will rotate based on this value
		public float yScaleOffset; // Offset (up / down) this part should be relative to its' parent (e.g. udder, horn)
		public Vec3d pos; // The position offset from the main body this should be drawn at
		public Vec3d rotationPos = null; // By default this would be 0,0,0 but sometimes we want to set the rotation point to somwhere else
		//public float xScaleOffset; // Offset (left / right) this part should be relative to its' parent (e.g. eyes, legs)
		
		public HuskPart(HuskBase model, HuskModelHelper helper) {
			this.model = model;	
			this.helper = helper;
		}
		
		/*
		 * Generate a default husk from the given souls inserted into the husk.
		 * TODO: Create a menu to change the utilities if there are more than one for the combinations given
		 */
		public static List<HuskPart> GenerateHuskParts(String[] parts) {
			boolean hasHead = false, hasBody = false, hasLegs = false, hasUtility = false, hasAttack = false;
			HuskPart head = null, body = null, legs = null, utility = null, attack = null;
			List<HuskPart> partList = new ArrayList<HuskPart>();
			
			// Loop through all body parts available and prioritize based on the main soul / first inserted souls
			// We will have to lock the body and (most of the time) legs depending on the main soul
			for (int i = 0; i < parts.length; i++) {
				HuskModelHelper helper = HuskRegistryHelper.getHelper(parts[i]);
				for (HuskBase huskBase : helper.parts) {
					if (!hasBody && (huskBase.GetPartType() == PartType.BODY || huskBase.isAlsoBody)) {
						body = new HuskPart(huskBase, helper);
						partList.add(body);
						hasBody = true;
						hasHead = huskBase.isAlsoBody;
					}
					else if (!hasHead && huskBase.GetPartType() == PartType.HEAD) {
						head = new HuskPart(huskBase, helper);
						head.pos = body.model instanceof HuskBodyBase ? ((HuskBodyBase)body.model).headPos : new Vec3d(0, 0, 0); // This must always be determined in a body base class
						head.rotationPos = body.model instanceof HuskBodyBase ? ((HuskBodyBase)body.model).headRotationPos : new Vec3d(0, 0, 0); // This must always be determined in a body base class
						head.parentModelIndex = 0;
						head.parentModel = body.model;
						partList.add(head);
						hasHead = true;
					}
					else if (!hasLegs && huskBase.GetPartType() == PartType.LEGS) {
						legs = new HuskPart(huskBase, helper);
						legs.pos = ((HuskLegsBase)huskBase).GetLegPosFromBodyPart(body.model);
						legs.parentModelIndex = 0;
						legs.parentModel = body.model;
						partList.add(legs);
						hasLegs = true;
					}
					else if (!hasUtility && huskBase.GetPartType() == PartType.UTILITY) {
						utility = new HuskPart(huskBase, helper);
						utility.pos = ((HuskUtilityBase)huskBase).GetPosFromBodyPart(body.model);
						utility.parentModelIndex = 0;
						utility.parentModel = body.model;
						partList.add(utility);
						hasUtility = true;
					}
					else if (!hasAttack && huskBase.GetPartType() == PartType.ATTACK) {
						attack = new HuskPart(huskBase, helper);
						attack.parentModelIndex = 0;
						attack.parentModel = body.model;
						partList.add(attack);
						hasAttack = true;
					}
				}
			}
			
			return partList;
		}

		/**
		 * Deserialize part based on saved dataManager value.
		 */
		public static HuskPart DeserializePart(String[] partOpts, HuskModelHelper helper) {
			try {
				String modelType = partOpts[0];
				Class<? extends HuskBase> cls = (Class<? extends HuskBase>) Class.forName(modelType);
				HuskBase huskBaseModel = null;
				
				// Invoke the method through reflection. 
				// TODO: test if there is any performance issues with this. 
				Method serializeMethod = cls.getMethod("DeserializeFromClass");
				huskBaseModel = (HuskBase)serializeMethod.invoke(partOpts[2]);
				
				HuskPart huskPart = new HuskPart(huskBaseModel, helper);
				
				// Serialized method will always have the parent variable index in second brace. e.g. "CowUdder|0|opt1,opt2,opt3;"
				// A value with no number or -1 means it has no parent
				String parentIndex = partOpts[1];
				if (!parentIndex.isEmpty() && parentIndex != "-1") {
					huskPart.parentModelIndex = Integer.parseInt(parentIndex);
				}
				
				return new HuskPart(huskBaseModel, helper);
			} 
			catch (Exception e) {
				// Invalid deserialization
				e.printStackTrace();
				return null;
			} 
		}
		
	}
}
