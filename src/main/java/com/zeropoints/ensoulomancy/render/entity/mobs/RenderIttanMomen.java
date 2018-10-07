package com.zeropoints.ensoulomancy.render.entity.mobs;

import com.zeropoints.ensoulomancy.entity.ghost.EntityIttanMomen;
import com.zeropoints.ensoulomancy.model.ghost.ModelIttanMomen;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class RenderIttanMomen extends RenderLiving<EntityIttanMomen> {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/ghost/ittan_momen.png");
	
	public RenderIttanMomen(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelIttanMomen(), 0.2F);
	}
	
	protected ResourceLocation getEntityTexture(EntityIttanMomen entity) {
		return TEXTURES;
	}
	
	/**
     * Rendering factory
     * 
     * Returns new instance of the morph renderer
     */
    public static class RenderFactory implements IRenderFactory<EntityIttanMomen> {
        @Override
        public Render<EntityIttanMomen> createRenderFor(RenderManager manager) {
            return new RenderIttanMomen(manager);
        }
    }
	
}
