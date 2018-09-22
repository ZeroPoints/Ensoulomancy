package com.zeropoints.ensoulomancy.render.tileentity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.model.ModelHeadBase;
import com.zeropoints.ensoulomancy.model.heads.*;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;
import com.zeropoints.ensoulomancy.util.SoulSkullType;
import com.zeropoints.ensoulomancy.util.SoulSkullType.SkullRegistryHelper;

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
	private static final ModelHeadBase modelCatHead = new ModelCatHead();
	private static final ModelHeadBase modelCowHead = new ModelCowHead();
	private static final ModelHeadBase modelGuardianHead = new ModelGuardianHead();
	private static final ModelHeadBase modelHorseHead = new ModelHorseHead();
	private static final ModelHeadBase modelIllagerHead = new ModelIllagerHead();
	private static final ModelHeadBase modelLlamaHead = new ModelLlamaHead();
	private static final ModelHeadBase modelParrotHead = new ModelParrotHead();
	private static final ModelHeadBase modelRabbitHead = new ModelRabbitHead();
	private static final ModelHeadBase modelSpiderHead = new ModelSpiderHead();
	
	// put each of our models into a hashmap to retrieve easily through a name
	// models with sub-types will be classified under the same SoulSkullType
	static {
		for (int i = 0; i < SkullRegistryHelper.SoulSkullTypes.length; ++i) {
			SoulSkullType skullType = SkullRegistryHelper.SoulSkullTypes[i];
			switch (skullType.headClass.getName()) {
				case "ModelCatHead":      skullType.model = modelCatHead;		break;
				case "ModelCowHead":      skullType.model = modelCowHead;		break;
				case "ModelGuardianHead": skullType.model = modelGuardianHead;	break;
				case "ModelHorseHead":    skullType.model = modelHorseHead;		break;
				case "ModelIllagerHead":  skullType.model = modelIllagerHead;	break;
				case "ModelLlamaHead":    skullType.model = modelLlamaHead;		break;
				case "ModelParrotHead":   skullType.model = modelParrotHead;	break;
				case "ModelRabbitHead":   skullType.model = modelRabbitHead;	break;
				case "ModelSpiderHead":   skullType.model = modelSpiderHead;	break;
				default: 
					Constructor<?> cons = skullType.headClass.getConstructors()[0];
					ModelHeadBase headModel = null;
					try {
						headModel = (ModelHeadBase)cons.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
						Main.log(Level.ERROR, "Model for Soul Skull cannot be null!");
					}
					skullType.model = headModel;
					break;
			}
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
    	if (skullType >= SkullRegistryHelper.SoulSkullTypes.length) {
    		skullType = 0;
    	}
    	SoulSkullType soulSkull = SkullRegistryHelper.SoulSkullTypes[skullType];
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