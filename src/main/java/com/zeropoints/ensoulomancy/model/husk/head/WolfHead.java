package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WolfHead extends HuskHeadBase<WolfHead> {
    
    private final ModelRenderer RightEar;
    private final ModelRenderer LeftEar;
    private final ModelRenderer Nose;
	
	public WolfHead() {
		this(new Vec3d(0,0,0));
	}
	
    public WolfHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.RightEar = new ModelRenderer(this, 16, 14);
        this.addBox(this.RightEar, offset, -3, -8, 0, 2, 2, 1);
        
        this.LeftEar = new ModelRenderer(this, 16, 14);
        this.addBox(this.LeftEar, offset, 1, -8, 0, 2, 2, 1);
        
        this.Nose = new ModelRenderer(this, 0, 10);
        this.addBox(this.Nose, offset, -1.5F, -3, -5, 3, 3, 4);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -3, -6, -2, 6, 6, 4);
        
        this.base.addChild(this.RightEar);
        this.base.addChild(this.LeftEar);
        this.base.addChild(this.Nose);
    }

}
