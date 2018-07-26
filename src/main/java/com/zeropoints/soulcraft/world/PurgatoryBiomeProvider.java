package com.zeropoints.soulcraft.world;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.zeropoints.soulcraft.init.ModDimensions;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;


public class PurgatoryBiomeProvider extends BiomeProvider {


	//private GenLayer genBiomes;

	
	
	
	
	
	public PurgatoryBiomeProvider(World world) {
		super(ModDimensions.purgatoryWorldInfo);
		
		//GenLayer[] agenlayer = initializeAllBiomeGenerators(seed, default1, str, properties);//GenLayer.initializeAllBiomeGenerators(seed, default1); //;
		//agenlayer = getModdedBiomeGenerators(default1, seed, agenlayer);
		//this.genBiomes = agenlayer[0];
		//this.biomeIndexLayer = agenlayer[1];
		
		
		//this.
		
		//allowedBiomes = Lists.newArrayList(HALLOWED_BIOME, PROFANE_BIOME, STYX_BIOME);
		
		//biomeCache = new BiomeCache(this);

		
	}
	
	
	
	/*
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] aint = this.genBiomes.getInts(x, z, width, height);

        try
        {
            for (int i = 0; i < width * height; ++i)
            {
                biomes[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
            }

            return biomes;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
            crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
            crashreportcategory.addCrashSection("x", Integer.valueOf(x));
            crashreportcategory.addCrashSection("z", Integer.valueOf(z));
            crashreportcategory.addCrashSection("w", Integer.valueOf(width));
            crashreportcategory.addCrashSection("h", Integer.valueOf(height));
            throw new ReportedException(crashreport);
        }
    }
    */
}
