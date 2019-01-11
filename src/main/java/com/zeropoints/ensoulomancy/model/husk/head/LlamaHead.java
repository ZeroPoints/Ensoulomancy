package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LlamaHead extends HuskHeadBase<LlamaHead> {
	
	public static String[] subTypes = new String[] { "default", "llama_brown", "llama_creamy", "llama_gray", "llama_white" };
	
	private final ModelRenderer Nose;
    private final ModelRenderer RightEar;
    private final ModelRenderer LeftEar;
    
    public LlamaHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public LlamaHead(Vec3d offset) {    	
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.Nose = new ModelRenderer(this, 0, 0);
        this.addBox(this.Nose, offset, -2, -6, -5, 4, 4, 9);
        
        this.RightEar = new ModelRenderer(this, 17, 0);
        this.addBox(this.RightEar, offset, -4, -11, 1, 3, 3, 2);
        
        this.LeftEar = new ModelRenderer(this, 17, 0);
        this.addBox(this.LeftEar, offset, 1, -11, 1, 3, 3, 2);
        
        this.base = new ModelRenderer(this, 0, 14);
        this.addBaseBox(this.base, offset, -4, -8, -1, 8, 8, 6);
        
        this.base.addChild(this.Nose);
        this.base.addChild(this.RightEar);
        this.base.addChild(this.LeftEar);
    }

}
