package com.zeropoints.ensoulomancy.model.husk.head;

import com.zeropoints.ensoulomancy.model.husk.HuskHeadBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SheepHead extends HuskHeadBase<SheepHead> {
	
	public SheepHead() {
		this(new Vec3d(0,0,0));
	}
	
    public SheepHead(Vec3d offset) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.base = new ModelRenderer(this, 2, 2);
        this.addBaseBox(this.base, offset, -3, -6, -3, 6, 6, 6, 0);
    }

}
