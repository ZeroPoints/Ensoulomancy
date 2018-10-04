package com.zeropoints.ensoulomancy.world;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBiomes;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;
import com.zeropoints.ensoulomancy.world.biome.PurgatoryBiomeProvider;

import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class PurgatoryWorldProvider extends WorldProvider {

	

    @Override
    protected void init () {

        biomeProvider = new PurgatoryBiomeProvider(world);
        
        //me trying things dunno...
        //this.setSkyRenderer(skyRenderer);
        
        //This needs to be set.!!!
        //REASON: enabled causes light calcs on block updates for all blocks below blocks. Since we have large empty spaces all the way to the void it is LAGGY.
        hasSkyLight = false;
    	
        setDimension(ConfigurationHandler.dimensionId);
        
        Main.LogMesssage("PurgatoryWorldProvider", "init");
        
    }
    
    
    /*
     * NO matter the DIM id the dims folder is named this. Easier to find
     * 
     * (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#getSaveFolder()
     */
    @Override
    public String getSaveFolder() {
        return "DIM-PURGATORY";
    }
	
    
    /**
	 * Dunno..didnt check refs...
	 */
	@Override
	public DimensionType getDimensionType() {
		
        return ModDimensions.purgatoryDimensionType;
	}

	
	/*
	 * Leave this at this value.
	 * All it does is when you get below that horizon point it will start moving a black texture over the screen.
	 * 
	 * Does different things if value is >=0 based on code in renderglobal.rendersky
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.world.WorldProvider#getHorizon()
	 */
	@Override
    public double getHorizon()
    {
        return -500;
    }
    
    
    
	
	/**
	 * Dunno...didnt bother checking refs
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.world.WorldProvider#getAverageGroundLevel()
	 */
    @Override
    public int getAverageGroundLevel () {

        return 0;
    }

    /*
	 * Seem to need this for starry night and sun and moon render
	 * 
     * (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#isSurfaceWorld()
     */
    @Override
    public boolean isSurfaceWorld () {

        return true;
    }
    
    
    /*
	 * gotta gen my chunks mate 	 
     * 
     * (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#createChunkGenerator()
     */
    @Override
    public IChunkGenerator createChunkGenerator() {
        return new PurgatoryChunkGenerator(this.world, this.world.getSeed(), false, world.getWorldInfo().getGeneratorOptions());
    }
    
    
	/*
	 * found online  hope its ok to use it didnt check for licensing.
	 * Generic enough tho as it takes vanilla light code and  adjusts based on a factor
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.world.WorldProvider#generateLightBrightnessTable()
	 */
	@Override
	protected void generateLightBrightnessTable()
   {
       float f = 0.11F;

       for (int i = 0; i <= 15; ++i)
       {
           float f1 = 1.0F - (float)i / 15.0F;
           this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + f;
       }
   }
    

    
    /*
     * Something something sky and colors.
     * If this is off it uses default coloring behaviour :(
     * 
     * (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#isSkyColored()
     */
    @Override
    public boolean isSkyColored()
    {
    	return true;
    }
    
    

    /*
     * 0 means that at player below y = 0 it will draw a black fog layer :(
     * Cant seem to get around this unless i change entity renderer.updateFogColor but i dont feel like that.
     * 
     * (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#getVoidFogYFactor()
     */
    @SideOnly(Side.CLIENT)
	@Override
    public double getVoidFogYFactor()
    {
        return 1;
    }
    

  

	
    
    
	    
	/*
	 * FOREVER twilight/dusk/dawn...
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.world.WorldProvider#getWorldTime()
	 */
    @Override
    public long getWorldTime()
    {
        return 23000;
    }
    
    
    /*
     * Sets the bottom axis of horizon color
     * Celestial Angle, Partial Ticks,
     * (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#getFogColor(float, float)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {

        //Params
        //MidDay    Fog = angle:0.97297704, tick:0.4399985
        //Morn    Fog = angle:0.8281958, tick:0.19999886
        //Night   Fog = angle:0.46559247, tick:0.7999992
    	

        //Vec3d v = super.getFogColor(p_76562_1_, p_76562_2_);
    	//Default returns
        //Day    Fog = x:0.7529411911964417,      y:0.8470588326454163,    z:1.0
        //Night  Fog = x:0.045176468789577484,    y:0.050823528319597244,  z:0.09000000357627869
        
        //TEALISH
        return new Vec3d(0.3333, 0.6666, 0.5555);
    }
    
    
    
    /*
     * Sets top axis of horizon color
     * (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#getSkyColor(net.minecraft.entity.Entity, float)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(net.minecraft.entity.Entity cameraEntity, float partialTicks) {
    	//Vec3d v = world.getSkyColorBody(cameraEntity, partialTicks);
    	
    	//Default returns
    	//Day      Sky = x:0.48235294222831726,		y:0.6431372761726379,		z:1.0
    	//Night    Sky = x:0.0,						y:0.0,						z:0.0   	
    	
    	//TEALISH
        return new Vec3d(0.3333, 0.6666, 0.5555);
    }
    
    
    
    
    

    
    /*
    //Cant seem to change fog distance in here have to do something with the entityrenderer . lets just leave fog out for now
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
    }
    */
    
    
    
}
