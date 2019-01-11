package com.zeropoints.ensoulomancy.render.entity.husk;

import java.util.ArrayList;
import java.util.List;

import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.entity.profane.EntityImp;
import com.zeropoints.ensoulomancy.model.husk.ModelHusk;
import com.zeropoints.ensoulomancy.render.layer.husk.*;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHusk<T extends EntityHusk> extends RenderLiving<T> {
	
	// By default, it doesn't render anything unless a texture can be binded. 
	// TODO: Change this so we can skip unecessary processing deep down
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/profane/imp.png");
	
	public RenderHusk(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelHusk(), 0.5F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TEXTURES;
	}
	
	public static class RenderFactory implements IRenderFactory<EntityHusk> {
        @Override
        public Render<? super EntityHusk> createRenderFor(RenderManager manager) {
            return new RenderHusk(manager);
        }
    }
	
}
