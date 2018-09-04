package com.zeropoints.ensoulomancy.render.tileentity;

import java.util.HashMap;
import java.util.Map;

import com.zeropoints.ensoulomancy.model.ModelHeadBase;
import com.zeropoints.ensoulomancy.model.heads.*;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;
import com.zeropoints.ensoulomancy.util.SoulSkullType;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntitySoulSkullRenderer extends TileEntitySpecialRenderer<TileEntitySoulSkull> {
	
	public static TileEntitySoulSkullRenderer instance;
	
	//TODO: Merge simple models into one, e.g. blaze, spider
	
	// Shared models
	private static final ModelHeadBase modelCowHead = new ModelCowHead();
	private static final ModelHeadBase modelGuardianHead = new ModelGuardianHead();
	private static final ModelHeadBase modelHorseHead = new ModelHorseHead();
	private static final ModelHeadBase modelIllagerHead = new ModelIllagerHead();
	private static final ModelHeadBase modelParrotHead = new ModelParrotHead();
	private static final ModelHeadBase modelCatHead = new ModelCatHead();
	private static final ModelHeadBase modelLlamaHead = new ModelLlamaHead();
	private static final ModelHeadBase modelRabbitHead = new ModelRabbitHead();
	private static final ModelHeadBase modelSpiderHead = new ModelSpiderHead();
	
	public static final SoulSkullType[] SoulSkullTypes = {
			new SoulSkullType("Bat", "textures/entity/bat", new ModelBatHead()),
			new SoulSkullType("Blaze", "textures/entity/blaze", new ModelBlazeHead()),
			new SoulSkullType("Chicken", "textures/entity/chicken", new ModelChickenHead()),
			new SoulSkullType("Cow", "textures/entity/cow/cow", modelCowHead),
			new SoulSkullType("MushroomCow", "textures/entity/cow/mooshroom", modelCowHead),
			new SoulSkullType("Enderman", "textures/entity/enderman/enderman", new ModelEndermanHead()),
			new SoulSkullType("Endermite", "textures/entity/endermite", new ModelEnderMiteHead()),
			new SoulSkullType("Ghast", "textures/entity/ghast/ghast", new ModelGhastHead()),
			new SoulSkullType("Guardian", "textures/entity/guardian", modelGuardianHead),
			new SoulSkullType("GuardianElder", "textures/entity/guardian_elder", modelGuardianHead),
			new SoulSkullType("Horse.horse_white", "textures/entity/horse/horse_white", modelHorseHead),
			new SoulSkullType("Horse.horse_creamy", "textures/entity/horse/horse_creamy", modelHorseHead),
			new SoulSkullType("Horse.horse_chestnut", "textures/entity/horse/horse_chestnut", modelHorseHead),
			new SoulSkullType("Horse.horse_black", "textures/entity/horse/horse_black", modelHorseHead),
			new SoulSkullType("Horse.horse_gray", "textures/entity/horse/horse_gray", modelHorseHead),
			new SoulSkullType("Horse.horse_darkbrown", "textures/entity/horse/horse_darkbrown", modelHorseHead),
			new SoulSkullType("Donkey", "textures/entity/horse/donkey", modelHorseHead),
			new SoulSkullType("Mule", "textures/entity/horse/mule", modelHorseHead),
			new SoulSkullType("SkeletonHorse", "textures/entity/horse/horse_skeleton", modelHorseHead),
			new SoulSkullType("ZombieHorse", "textures/entity/horse/horse_zombie", modelHorseHead),
			new SoulSkullType("Husk", "textures/entity/zombie/husk", new ModelZombieHead()),
			new SoulSkullType("EvokerIllager", "textures/entity/illager/evoker", modelIllagerHead),
			new SoulSkullType("VindicatorIllager", "textures/entity/illager/vindicator", modelIllagerHead),
			new SoulSkullType("LavaSlime", "textures/entity/slime/magmacube", new ModelMagmaCubeHead()),
			new SoulSkullType("Llama.default", "textures/entity/llama/llama", modelLlamaHead),
			new SoulSkullType("Llama.llama_brown", "textures/entity/llama/llama_brown", modelLlamaHead),
			new SoulSkullType("Llama.llama_creamy", "textures/entity/llama/llama_creamy", modelLlamaHead),
			new SoulSkullType("Llama.llama_gray", "textures/entity/llama/llama_gray", modelLlamaHead),
			new SoulSkullType("Llama.llama_white", "textures/entity/llama/llama_white", modelLlamaHead),
			new SoulSkullType("Ozelot.ocelot", "textures/entity/cat/ocelot", modelCatHead),
			new SoulSkullType("Ozelot.black", "textures/entity/cat/black", modelCatHead),
			new SoulSkullType("Ozelot.red", "textures/entity/cat/red", modelCatHead),
			new SoulSkullType("Ozelot.siamese", "textures/entity/cat/siamese", modelCatHead),
			new SoulSkullType("Parrot.parrot_blue", "textures/entity/parrot/parrot_blue", modelParrotHead),
			new SoulSkullType("Parrot.parrot_green", "textures/entity/parrot/parrot_green", modelParrotHead),
			new SoulSkullType("Parrot.parrot_grey", "textures/entity/parrot/parrot_grey", modelParrotHead),
			new SoulSkullType("Parrot.parrot_red_blue", "textures/entity/parrot/parrot_red_blue", modelParrotHead),
			new SoulSkullType("Parrot.parrot_yellow_blue", "textures/entity/parrot/parrot_yellow_blue", modelParrotHead),
			new SoulSkullType("Pig", "textures/entity/pig/pig", new ModelPigHead()),
			new SoulSkullType("PigZombie", "textures/entity/zombie_pigman", new ModelZombiePigHead()),
			new SoulSkullType("PolarBear", "textures/entity/bear/polarbear", new ModelPolarBearHead()),
			new SoulSkullType("Rabbit.black", "textures/entity/rabbit/black", modelRabbitHead),
			new SoulSkullType("Rabbit.brown", "textures/entity/rabbit/brown", modelRabbitHead),
			new SoulSkullType("Rabbit.caerbannog", "textures/entity/rabbit/caerbannog", modelRabbitHead),
			new SoulSkullType("Rabbit.gold", "textures/entity/rabbit/gold", modelRabbitHead),
			new SoulSkullType("Rabbit.toast", "textures/entity/rabbit/toast", modelRabbitHead),
			new SoulSkullType("Rabbit.white", "textures/entity/rabbit/white", modelRabbitHead),
			new SoulSkullType("Rabbit.white_splotched", "textures/entity/rabbit/white_splotched", modelRabbitHead),
			new SoulSkullType("Sheep", "textures/entity/sheep/sheep", new ModelSheepHead()),
			new SoulSkullType("Shulker", "textures/entity/shulker/shulker_black", new ModelShulkerHead()),
			new SoulSkullType("Silverfish", "textures/entity/silverfish", new ModelSilverfishHead()),
			new SoulSkullType("Slime", "textures/entity/slime/slime", new ModelSlimeHead()),
			new SoulSkullType("SnowMan", "textures/entity/snowman", new ModelSnowManHead()),
			new SoulSkullType("Spider", "textures/entity/spider/spider", modelSpiderHead),
			new SoulSkullType("CaveSpider", "textures/entity/spider/cave_spider", modelSpiderHead),
			new SoulSkullType("Squid", "textures/entity/squid", new ModelSquidHead()),
			new SoulSkullType("Stray", "textures/entity/skeleton/stray", new ModelSkeletonHead()),
			new SoulSkullType("Vex", "textures/entity/illager/vex", new ModelVexHead()),
			new SoulSkullType("VillagerGolem", "textures/entity/iron_golem", new ModelIronGolemHead()),
			new SoulSkullType("Villager", "textures/entity/iron_golem", new ModelVillagerHead()),
			new SoulSkullType("Witch", "textures/entity/witch", new ModelWitchHead()),
			new SoulSkullType("Wolf", "textures/entity/wolf/wolf", new ModelWolfHead()),
	};
	public static final Map<String,Integer> SoulSkullTypeMap = new HashMap<String,Integer>();
	
	// put each of our models into a hashmap to retrieve easily through a name
	// models with sub-types will be classified under the same SoulSkullType
	static {
		for(int i = 0; i < SoulSkullTypes.length; ++i) {
			SoulSkullTypeMap.put(SoulSkullTypes[i].entityname, i); // Name is the localizedname of the entity. Minecraft entity names are camel-case
		}
	}

    @Override
    public void render(TileEntitySoulSkull te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing enumfacing = EnumFacing.getFront(te.getBlockMetadata() & 7);
        this.renderSkull((float)x, (float)y, (float)z, enumfacing, (float)(te.getSkullRotation() * 360) / 16.0F, te.getSkullType(), destroyStage);
    }

    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
        super.setRendererDispatcher(rendererDispatcherIn);
        instance = this;
    }

    public void renderSkull(float x, float y, float z, EnumFacing facing, float rotationIn, int skullType, int destroyStage) {
    	if(skullType >= SoulSkullTypes.length) {
    		skullType = 0;
    	}
    	SoulSkullType soulSkull = SoulSkullTypes[skullType];
        ModelHeadBase modelbase = soulSkull.model;

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else {
        	this.bindTexture(soulSkull.texture);
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        if (facing == EnumFacing.UP) {
            GlStateManager.translate(x + 0.5F, y, z + 0.5F);
        }
        else {
            switch (facing) {
                case NORTH: GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.74F); 					   break;
                case SOUTH: GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.26F); rotationIn = 180.0F; break;
                case WEST: 	GlStateManager.translate(x + 0.74F, y + 0.25F, z + 0.5F); rotationIn = 270.0F; break;
                case EAST: 
            	default: 	GlStateManager.translate(x + 0.26F, y + 0.25F, z + 0.5F); rotationIn = 90.0F;  break;
            }
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1, -1, 1);
        GlStateManager.enableAlpha();

        modelbase.render((Entity)null, 0, 0, 0, rotationIn, 0, 0.0625F);
        GlStateManager.popMatrix();

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}