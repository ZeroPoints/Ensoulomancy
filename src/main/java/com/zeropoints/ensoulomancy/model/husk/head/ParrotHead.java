package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParrotHead extends HuskHeadBase<ParrotHead> {
	
	public static String[] subTypes = new String[] {"parrot_blue", "parrot_green", "parrot_grey", "parrot_red_blue", "parrot_yellow_blue"};
	
    private final ModelRenderer HeadTop;
    private final ModelRenderer LowerBeak;
    private final ModelRenderer Beak;
    private final ModelRenderer Crest;

    public ParrotHead() {
    	this(new Vec3d(0,0,0));
    }
    
    public ParrotHead(Vec3d offset) {
        this.textureWidth = 32;
        this.textureHeight = 32;
        
        this.HeadTop = new ModelRenderer(this, 10, 0);
        this.addBox(this.HeadTop, offset, -1, -4, -3, 2, 1, 4);
        
        this.Beak = new ModelRenderer(this, 16, 7);
        this.addBox(this.Beak, offset, -0.5F, -3.25F, -3, 1, 2, 1);
        
        this.LowerBeak = new ModelRenderer(this, 11, 7);
        this.addBox(this.LowerBeak, offset, -0.5F, -3, -2, 1, 2, 1);
        
        this.Crest = new ModelRenderer(this, 2, 18);
        this.addBox(this.Crest, offset, 0, -8, -2.5F, 0, 5, 4);
        this.setRotateAngle(Crest, -0.2214822820780804F, 0, 0);
        
        this.base = new ModelRenderer(this, 2, 2);
        this.addBaseBox(this.base, offset, -1, -3, -1, 2, 3, 2);
        
        this.base.addChild(this.HeadTop);
        this.base.addChild(this.Beak);
        this.base.addChild(this.LowerBeak);
        this.base.addChild(this.Crest);
    }

}
