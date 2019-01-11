package com.zeropoints.ensoulomancy.model.husk.head;

import org.lwjgl.opengl.GL11;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SlimeHead extends HuskHeadBase<SlimeHead> {
	
    private final ModelRenderer Mouth;
    private final ModelRenderer LeftEye;
    private final ModelRenderer RightEye;
    private final ModelRenderer Outer;
    
	public SlimeHead() {
		this(new Vec3d(0,0,0));
	}
	
    public SlimeHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.LeftEye = new ModelRenderer(this, 32, 4);
        this.addBox(this.LeftEye, offset, 1.25F, -6, -3.5F, 2, 2, 2);
        
        this.RightEye = new ModelRenderer(this, 32, 0);
        this.addBox(this.RightEye, offset, -3.25F, -6, -3.5F, 2, 2, 2);
        
        this.Mouth = new ModelRenderer(this, 32, 8);
        this.addBox(this.Mouth, offset, 0, -3, -3.5F, 1, 1, 1);
        
        // Outside cube - transparency
        this.Outer = new ModelRenderer(this, 0, 0);
        this.addBox(this.Outer, offset, -4, -8, -4, 8, 8, 8);
        
        this.base = new ModelRenderer(this, 0, 16);
        this.addBaseBox(this.base, offset, -3, -7, -3, 6, 6, 6);
        
        this.base.addChild(this.LeftEye);
        this.base.addChild(this.RightEye);
        this.base.addChild(this.Mouth);
        this.base.addChild(this.Outer);
    }
    
    @Override
    public void preRender() {
    	GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1, 1, 1, 1);
    }
    
    @Override
    public void postRender() {
    	GlStateManager.disableBlend();
    }

}
