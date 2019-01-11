package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IllagerHead extends HuskHeadBase<IllagerHead> {
	
	public static String[] subTypes = {""};
	
    private final ModelRenderer Hair;
    private final ModelRenderer Nose;

    public IllagerHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public IllagerHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.Hair = new ModelRenderer(this, 32, 0);
        this.addBox(this.Hair, offset, -4, -10, -4, 8, 12, 8, 0.5F);
        
        this.Nose = new ModelRenderer(this, 24, 0);
        this.addBox(this.Nose, offset, -1, -3, -6, 2, 4, 2);
        
        this.base = new ModelRenderer(this, 0, 0);
        this.addBaseBox(this.base, offset, -4, -10, -4, 8, 10, 8);
        
        this.base.addChild(this.Hair);
        this.base.addChild(this.Nose);
    }
    
}
