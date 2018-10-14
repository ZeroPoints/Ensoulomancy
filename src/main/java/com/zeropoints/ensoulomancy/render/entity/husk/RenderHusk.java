package com.zeropoints.ensoulomancy.render.entity.husk;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.entity.EntityHusk;
import com.zeropoints.ensoulomancy.model.husk.ModelHusk;
import com.zeropoints.ensoulomancy.render.layer.husk.LayerHuskHead;
import com.zeropoints.ensoulomancy.render.layer.husk.LayerHuskSpiderLegs;


import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHusk<T extends EntityHusk> extends RenderLiving<T> {
	
	public RenderHusk(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelHusk(), 0.5F);
		
		// Extra layers to render. Layers always exist but are not always drawn.
		// Not sure what sort of cost this has on performance
		this.addLayer(new LayerHuskHead(this));
		this.addLayer(new LayerHuskSpiderLegs(this));
	}
	
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		// TODO: dynamic texture creation? Will need to return some sort of default 
		return new ResourceLocation(Main.MOD_ID + ":textures/entity/profane/imp.png");
	}
	
	public void postRender(float scale) {
		
	}

	public static class RenderFactory implements IRenderFactory<EntityHusk> {
        @Override
        public Render<? super EntityHusk> createRenderFor(RenderManager manager) {
            return new RenderHusk(manager);
        }
    }
	
}
