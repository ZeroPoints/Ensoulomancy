/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2018
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package com.zeropoints.ensoulomancy.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RenderingUtils
 * Created by HellFirePvP
 * Date: 29.08.2016 / 16:51
 */
public class RenderingUtils {

    private static final Random rand = new Random();

    private static Field itemPhysics_fieldSkipRenderHook = null;

    //Use with caution. Big block of rendering hack.
    @Deprecated
    public static void unsafe_preRenderHackCamera(EntityPlayer renderView, double x, double y, double z, double prevX, double prevY, double prevZ, double yaw, double yawPrev, double pitch, double pitchPrev) {
        TileEntityRendererDispatcher.staticPlayerX = x;
        TileEntityRendererDispatcher.staticPlayerY = y;
        TileEntityRendererDispatcher.staticPlayerZ = z;

        Entity rv = Minecraft.getMinecraft().getRenderViewEntity();
        if (rv == null || !rv.equals(renderView)) {
            Minecraft.getMinecraft().setRenderViewEntity(renderView);
            rv = renderView;
        }
        EntityPlayer render = (EntityPlayer) rv;

        render.posX = x;
        render.posY = y;
        render.posZ = z;
        render.prevPosX = prevX;
        render.prevPosY = prevY;
        render.prevPosZ = prevZ;
        render.lastTickPosX = prevX;
        render.lastTickPosY = prevY;
        render.lastTickPosZ = prevZ;

        render.rotationYawHead =     (float)yaw;
        render.rotationYaw =         (float)yaw;
        render.prevRotationYaw =     (float)yawPrev;
        render.prevRotationYawHead = (float)yawPrev;
        render.cameraYaw =           (float)yaw;
        render.prevCameraYaw =       (float)yawPrev;
        render.rotationPitch =       (float)pitch;
        render.prevRotationPitch =   (float)pitchPrev;

        render = Minecraft.getMinecraft().player;

        render.posX = x;
        render.posY = y;
        render.posZ = z;
        render.prevPosX = prevX;
        render.prevPosY = prevY;
        render.prevPosZ = prevZ;
        render.lastTickPosX = prevX;
        render.lastTickPosY = prevY;
        render.lastTickPosZ = prevZ;

        render.rotationYawHead =     (float)yaw;
        render.rotationYaw =         (float)yaw;
        render.prevRotationYaw =     (float)yawPrev;
        render.prevRotationYawHead = (float)yawPrev;
        render.cameraYaw =           (float)yaw;
        render.prevCameraYaw =       (float)yawPrev;
        render.rotationPitch =       (float)pitch;
        render.prevRotationPitch =   (float)pitchPrev;

        Minecraft.getMinecraft().setIngameNotInFocus();
        ActiveRenderInfo.updateRenderInfo(render, false);
        Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
    }

    @Deprecated
    public static void unsafe_resetCamera() {
        if(Minecraft.getMinecraft().player != null) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            Minecraft.getMinecraft().setRenderViewEntity(player);
            double x = player.posX;
            double y = player.posY;
            double z = player.posZ;
            RenderManager rm = Minecraft.getMinecraft().getRenderManager();
            rm.setRenderPosition(x, y, z);
            rm.viewerPosX = x;
            rm.viewerPosY = y;
            rm.viewerPosZ = z;

            TileEntityRendererDispatcher.staticPlayerX = x;
            TileEntityRendererDispatcher.staticPlayerY = y;
            TileEntityRendererDispatcher.staticPlayerZ = z;

            if(Minecraft.getMinecraft().currentScreen != null) {
                Minecraft.getMinecraft().displayGuiScreen(null);
            }
            Minecraft.getMinecraft().inGameHasFocus = false;
            Minecraft.getMinecraft().setIngameFocus();

            if (Minecraft.IS_RUNNING_ON_MAC) {
                Mouse.setGrabbed(false);
                Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
                Mouse.setGrabbed(true);
            }
        }
    }

    /*public static void renderLightRayEffects(double x, double y, double z, Color effectColor, long seed, long continuousTick, int dstJump, float scale, int countFancy, int countNormal) {
        rand.setSeed(seed);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        int fancy_count = !FMLClientHandler.instance().getClient().gameSettings.fancyGraphics ? countNormal : countFancy;

        Tessellator tes = Tessellator.getInstance();
        BufferBuilder vb = tes.getBuffer();

        RenderHelper.disableStandardItemLighting();
        float f1 = continuousTick / 400.0F;
        float f2 = 0.4F;

        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        //GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        for (int i = 0; i < fancy_count; i++) {
            GlStateManager.rotate(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rand.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(rand.nextFloat() * 360.0F + f1 * 360.0F, 0.0F, 0.0F, 1.0F);
            vb.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            float fa = rand.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
            float f4 = rand.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
            fa /= 30.0F / (Math.min(dstJump, 10 * scale) / 10.0F);
            f4 /= 30.0F / (Math.min(dstJump, 10 * scale) / 10.0F);
            vb.pos(0, 0, 0).color(effectColor.getRed(), effectColor.getGreen(), effectColor.getBlue(), (int) (255.0F * (1.0F - f2))).endVertex();
            vb.pos(-0.7D * f4, fa,   -0.5F * f4).color(effectColor.getRed(), effectColor.getGreen(), effectColor.getBlue(), 0).endVertex();
            vb.pos( 0.7D * f4, fa,   -0.5F * f4).color(effectColor.getRed(), effectColor.getGreen(), effectColor.getBlue(), 0).endVertex();
            vb.pos( 0.0D,      fa,    1.0F * f4).color(effectColor.getRed(), effectColor.getGreen(), effectColor.getBlue(), 0).endVertex();
            vb.pos(-0.7D * f4, fa,   -0.5F * f4).color(effectColor.getRed(), effectColor.getGreen(), effectColor.getBlue(), 0).endVertex();
            tes.draw();
        }
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        Blending.DEFAULT.applyStateManager();
        Blending.DEFAULT.apply();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();

        GlStateManager.popMatrix();
    }*/

    static {
        Field attempt;
        try {
            attempt = ReflectionHelper.findField(EntityItem.class, "skipPhysicRenderer");
            attempt.setAccessible(true);
        } catch(Exception e) {
            attempt = null;
        }
        itemPhysics_fieldSkipRenderHook = attempt;
    }

}