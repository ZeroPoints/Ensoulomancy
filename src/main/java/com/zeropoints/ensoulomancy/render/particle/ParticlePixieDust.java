package com.zeropoints.ensoulomancy.render.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticlePixieDust extends ParticleSimpleAnimated {
	
    public ParticlePixieDust(World world, double x, double y, double z, double vx, double vy, double vz, int rgb, float alpha) {
        super(world, x, y, z, 176, 8, -5.0E-4F);
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        this.particleScale *= 0.75F;
        this.particleMaxAge = 50 + this.rand.nextInt(12);
        this.setColor(rgb); // each pixie has different coloured sparkles
        this.setAlphaF(alpha);
        this.setColorFade(0x00AAAAFF);
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... parms) {
            return new ParticlePixieDust(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, parms[0], parms[1]);
        }
    }
    
}