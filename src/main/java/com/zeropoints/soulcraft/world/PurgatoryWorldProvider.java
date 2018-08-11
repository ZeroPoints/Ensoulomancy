package com.zeropoints.soulcraft.world;

import com.zeropoints.soulcraft.util.ConfigurationHandler;
import com.zeropoints.soulcraft.world.biome.PurgatoryBiomeProvider;
import com.zeropoints.soulcraft.init.ModBiomes;
import com.zeropoints.soulcraft.init.ModDimensions;
import com.zeropoints.soulcraft.Main;

import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
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
        
        
        
        hasSkyLight = true;
    	
        setDimension(ConfigurationHandler.dimensionId);
        
        Main.LogMesssage("PurgatoryWorldProvider", "init");
        
    }
    
	
	
	
	@Override
	public DimensionType getDimensionType() {
		
        return ModDimensions.purgatoryDimensionType;
	}

	
	
	

    @Override
    public int getAverageGroundLevel () {

        return 70;
    }


    @Override
    public boolean isSurfaceWorld () {

        return false;
    }
    
    
    
    @Override
    public IChunkGenerator createChunkGenerator() {
        Main.LogMesssage("PurgatoryWorldProvider", "createChunkGenerator");
        return new PurgatoryChunkGenerator(this.world, this.world.getSeed(), false, world.getWorldInfo().getGeneratorOptions());
    }
    

    
}
