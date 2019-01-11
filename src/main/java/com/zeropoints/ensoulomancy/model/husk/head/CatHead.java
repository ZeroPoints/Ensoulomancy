package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CatHead extends HuskHeadBase<CatHead> {
	
	public static String[] subTypes = new String[] {"ocelot", "black", "red", "siamese"};
	
    private final ModelRenderer Nose;
    private final ModelRenderer RightEar;
    private final ModelRenderer LeftEar;

    public CatHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public CatHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.RightEar = new ModelRenderer(this, 0, 10);
        this.addBox(this.RightEar, offset, -2, -5, 0, 1, 1, 2);
        
        this.LeftEar = new ModelRenderer(this, 6, 10);
        this.addBox(this.LeftEar, offset, 1, -5, 0, 1, 1, 2);
        
        this.Nose = new ModelRenderer(this, 0, 24);
        this.addBox(this.Nose, offset, -1.5F, -2, -4, 3, 2, 2);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -2.5F, -4, -3, 5, 4, 5);
        
        this.base.addChild(this.RightEar);
        this.base.addChild(this.LeftEar);
        this.base.addChild(this.Nose);
    }

}
