package com.zeropoints.ensoulomancy.render.tileentity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.model.husk.HuskBase;
import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;
import com.zeropoints.ensoulomancy.model.husk.head.*;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;
import com.zeropoints.ensoulomancy.util.HuskModelHelper;
import com.zeropoints.ensoulomancy.util.HuskModelHelper.HuskRegistryHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntitySoulSkullRenderer extends TileEntitySpecialRenderer<TileEntitySoulSkull> {
	
	public static TileEntitySoulSkullRenderer instance;

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

    /**
     * 
     */
    public void renderSkull(float x, float y, float z, EnumFacing facing, float rotationIn, int skullType, int destroyStage) {
    	if (skullType >= HuskRegistryHelper.Types.length) {
    		skullType = 0;
    	}
    	HuskModelHelper helper = HuskRegistryHelper.Types[skullType];
        HuskHeadBase modelbase = (HuskHeadBase)helper.parts.get(helper.headIdx);

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else {
        	this.bindTexture(helper.texture);
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

        // Since the appearance of husk offsets, have to physically add this in here otherwise it offsets the skulls too
        modelbase.base.offsetX = 0;
        modelbase.base.offsetY = 0;
        modelbase.base.offsetZ = 0;
        modelbase.base.rotationPointX = 0;
        modelbase.base.rotationPointY = 0;
        modelbase.base.rotationPointZ = 0;
        
        modelbase.render((Entity)null, 0, 0, 0, rotationIn, 0, modelbase.scale); // Use headbase render func
        GlStateManager.popMatrix();

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}