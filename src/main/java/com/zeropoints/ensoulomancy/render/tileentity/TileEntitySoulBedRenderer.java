package com.zeropoints.ensoulomancy.render.tileentity;

import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulBed;

import net.minecraft.client.model.ModelBed;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntitySoulBedRenderer extends TileEntitySpecialRenderer<TileEntitySoulBed> {
	
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/bed/red.png");
    private static final ModelBed model = new ModelBed();

    @Override
    public void render(TileEntitySoulBed te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        boolean flag = te.getWorld() != null;
        boolean flag1 = flag ? te.isHeadPiece() : true;
        int i = flag ? te.getBlockMetadata() & 3 : 0;

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else {
            this.bindTexture(TEXTURE);
        }

        if (flag) {
            this.renderPiece(flag1, x, y, z, i, alpha);
        }
        else {
            GlStateManager.pushMatrix();
            this.renderPiece(true, x, y, z, i, alpha);
            this.renderPiece(false, x, y, z - 1.0D, i, alpha);
            GlStateManager.popMatrix();
        }

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    private void renderPiece(boolean isHead, double x, double y, double z, int facing, float alpha) {
        this.model.preparePiece(isHead);
        GlStateManager.pushMatrix();
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;

        if (facing == EnumFacing.NORTH.getHorizontalIndex()) {
            f = 0.0F;
        }
        else if (facing == EnumFacing.SOUTH.getHorizontalIndex()) {
            f = 180.0F;
            f1 = 1.0F;
            f2 = 1.0F;
        }
        else if (facing == EnumFacing.WEST.getHorizontalIndex()) {
            f = -90.0F;
            f2 = 1.0F;
        }
        else if (facing == EnumFacing.EAST.getHorizontalIndex()) {
            f = 90.0F;
            f1 = 1.0F;
        }

        GlStateManager.translate((float)x + f1, (float)y + 0.5625F, (float)z + f2);
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        this.model.render();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        GlStateManager.popMatrix();
    }

}