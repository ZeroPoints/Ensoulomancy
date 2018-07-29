package com.zeropoints.soulcraft.renderer.tileentity;

import java.util.HashMap;
import java.util.Map;

import com.zeropoints.soulcraft.model.*;
import com.zeropoints.soulcraft.model.heads.ModelBlazeHead;
import com.zeropoints.soulcraft.model.heads.ModelChickenHead;
import com.zeropoints.soulcraft.model.heads.ModelCowHead;
import com.zeropoints.soulcraft.tileentity.TileEntitySoulSkull;
import com.zeropoints.soulcraft.util.SoulSkullType;

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
	
	public static final SoulSkullType[] SoulSkullTypes = {
			new SoulSkullType("Blaze", "textures/entity/blaze", new ModelBlazeHead()),
			new SoulSkullType("Chicken", "textures/entity/chicken", new ModelChickenHead()),
			new SoulSkullType("Cow", "textures/entity/cow/cow", new ModelCowHead())
	};
	public static final Map<String,Integer> SoulSkullTypeMap = new HashMap<String,Integer>();
	
	static {
		for(int i = 0; i < SoulSkullTypes.length; ++i) {
			SoulSkullTypeMap.put(SoulSkullTypes[i].entityname, i); // Name is the localizedname of the entity. For minecraft they are capitilized
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

        float f = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
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