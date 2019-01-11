package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MagmacubeHead extends HuskHeadBase<MagmacubeHead> {
	
	private final ModelRenderer Slice_1;
	private final ModelRenderer Slice_2;
	private final ModelRenderer Slice_3;

	public MagmacubeHead() {
		this(new Vec3d(0,0,0));
	}
	
    public MagmacubeHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Slice_1 = new ModelRenderer(this, 0, 0);
        this.addBox(this.Slice_1, offset, -4, -8, -4, 8, 2, 8);
        
        this.Slice_2 = new ModelRenderer(this, 24, 10);
        this.addBox(this.Slice_2, offset, -4, -6, -4, 8, 1, 8);
        
        this.Slice_3 = new ModelRenderer(this, 24, 19);
        this.addBox(this.Slice_3, offset, -4, -5, -4, 8, 1, 8);
        
        this.base = new ModelRenderer(this, 0, 4);
        this.addBaseBox(this.base, offset, -4, -4, -4, 8, 4, 8);
        
        this.base.addChild(this.Slice_1);
        this.base.addChild(this.Slice_2);
        this.base.addChild(this.Slice_3);
    }

}
