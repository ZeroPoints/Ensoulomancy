package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RabbitHead extends HuskHeadBase<RabbitHead> {
	
	public static String[] subTypes = new String[] { "brown", "white", "black", "white_splotched", "gold", "caerbannog", "toast" };
	
    private final ModelRenderer Nose;
    private final ModelRenderer LeftEar;
    private final ModelRenderer RightEar;
    
    public RabbitHead() {
    	this(new Vec3d(0,0,0));
    }

    public RabbitHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.RightEar = new ModelRenderer(this, 52, 0);
        this.addBox(this.RightEar, offset, -2, -9, 1.3F, 2, 5, 1, 0);
        this.setRotateAngle(RightEar, 0, -0.2617993877991494F, 0);
        
        this.LeftEar = new ModelRenderer(this, 58, 0);
        this.addBox(this.LeftEar, offset, 0, -9, 1.3F, 2, 5, 1, 0);
        this.setRotateAngle(LeftEar, 0, 0.2617993877991494F, 0);
        
        this.Nose = new ModelRenderer(this, 32, 9);
        this.addBox(this.Nose, offset, -0.5F, -2.5F, -3, 1, 1, 1, 0);
        
        this.base = new ModelRenderer(this, 32, 0);
        this.addBaseBox(this.base, offset, -2.5F, -4, -2.5F, 5, 4, 5, 0);
        
        this.base.addChild(this.RightEar);
        this.base.addChild(this.LeftEar);
        this.base.addChild(this.Nose);
    }
    
}
