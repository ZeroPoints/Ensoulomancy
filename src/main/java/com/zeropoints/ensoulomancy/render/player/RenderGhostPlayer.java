package com.zeropoints.ensoulomancy.render.player;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhostPlayer extends RenderPlayer {
	
	public RenderGhostPlayer(RenderManager renderManager) {
		super(renderManager, false);
	}

	@Override
	public void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
		GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.35F);
        GlStateManager.disableBlend();
	}
	
}
