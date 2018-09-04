package com.zeropoints.ensoulomancy.world;

import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.world.biome.ICustomBiome;

import net.minecraft.util.math.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;


/**
 * This code was from MCJTY tutorial on terrain for custom dimensions and then altered
 */
public class NormalTerrainGenerator {

	
	public class BiomeHeights {
		
		public double height;
		
		public Biome biome; 
		
		
	}
	
	
	
	private World world;
    private Random random;

    

    private final BiomeHeights[] heightMaps;

    private double[] mainNoiseRegion;
    private double[] minLimitRegion;
    private double[] maxLimitRegion;
    private double[] depthRegion;

    private NoiseGeneratorOctaves minLimitPerlinNoise;
    private NoiseGeneratorOctaves maxLimitPerlinNoise;
    private NoiseGeneratorOctaves mainPerlinNoise;
    private NoiseGeneratorPerlin surfaceNoise;

    // A NoiseGeneratorOctaves used in generating terrain
    private NoiseGeneratorOctaves depthNoise;

    private final float[] biomeWeights;
    private double[] depthBuffer = new double[256];

    private Biome[] biomesForGeneration;

    /**
     * Dunno...
     */
    public NormalTerrainGenerator() {

        this.heightMaps = new BiomeHeights[825];

        
        this.biomeWeights = new float[25];
        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt((j * j + k * k) + 0.2F);
                this.biomeWeights[j + 2 + (k + 2) * 5] = f;
            }
        }
    }

    
    /**
     * Tell this object what biomes this chunk will be using
     */
    public void setBiomesForGeneration(Biome[] biomesForGeneration) {
        this.biomesForGeneration = biomesForGeneration;
    }

    /**
     * Setup all noise generators...
     * This is for giving bumpy terrain
     */
    public void setup(World world, Random rand) {
        this.world = world;
        this.random = rand;

        this.minLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctaves(rand, 8);
        this.surfaceNoise = new NoiseGeneratorPerlin(rand, 4);
        NoiseGeneratorOctaves noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(rand, 16);
        NoiseGeneratorOctaves mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);

        net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld ctx =
                new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld(minLimitPerlinNoise,
                    maxLimitPerlinNoise, mainPerlinNoise, surfaceNoise, noiseGen5, depthNoise, mobSpawnerNoise);
        ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(world, rand, ctx);
        this.minLimitPerlinNoise = ctx.getLPerlin1();
        this.maxLimitPerlinNoise = ctx.getLPerlin2();
        this.mainPerlinNoise = ctx.getPerlin();
        this.surfaceNoise = ctx.getHeight();
        this.depthNoise = ctx.getDepth();
    }




    /**
     * Builds a heightmap of locations the biomes will be spawning in this chunk
     */
    private void generateHeightmap(int chunkX4, int chunkY4, int chunkZ4) {
        
        int l = 0;
        int i1 = 0;

        double[] heightMap = new double[825];
        
        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;

                //-2 to 2  twice ?
                Biome biome = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];
                
                float baseHeight = biome.getBaseHeight();
                float variation = biome.getHeightVariation();

                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {

                        float f5 = biomeWeights[l1 + 2 + (i2 + 2) * 5] / (baseHeight + 2.0F);
                        f += variation * f5;
                        f1 += baseHeight * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d12 = this.depthRegion[i1] / 8000.0D;

                if (d12 < 0.0D) {
                    d12 = -d12 * 0.3D;
                }

                d12 = d12 * 3.0D - 2.0D;

                if (d12 < 0.0D) {
                    d12 /= 2.0D;

                    if (d12 < -1.0D) {
                        d12 = -1.0D;
                    }

                    d12 /= 1.4D;
                    d12 /= 2.0D;
                } else {
                    if (d12 > 1.0D) {
                        d12 = 1.0D;
                    }

                    d12 /= 8.0D;
                }

                ++i1;
                double d13 = f1;
                double d14 = f;
                d13 += d12 * 0.2D;
                d13 = d13 * 8.5D / 8.0D;
                double d5 = 8.5D + d13 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = (j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D) {
                        d6 *= 4.0D;
                    }

                    double d7 = this.minLimitRegion[l] / 512.0D;
                    double d8 = this.maxLimitRegion[l] / 512.0D;
                    double d9 = (this.mainNoiseRegion[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.clamp(d7, d8, d9) - d6;

                    if (j2 > 29) {
                        double d11 = ((j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    
                    this.heightMaps[l] = new BiomeHeights();
                    this.heightMaps[l].height = d10;
                    this.heightMaps[l].biome = biome;
                    
                    ++l;
                    //Main.LogMesssage("Height: " + l + " - " + d10);
                }
            }
        }
        
    }


    /**
     * Generates the height map and places blocks at locations based on height map
     */
    public void generate(int chunkX, int chunkZ, ChunkPrimer primer) {
    	this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, chunkX * 4, chunkZ * 4, 5, 5, 200.0D, 200.0D, 0.5D);
        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, chunkX * 4, 0, chunkZ * 4, 5, 33, 5, 8.55515D, 4.277575D, 8.55515D);
        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, chunkX * 4, 0, chunkZ * 4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, chunkX * 4, 0, chunkZ * 4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        
        generateHeightmap(chunkX * 4, 0, chunkZ * 4);


        double oneEighth = 0.125D;
        double oneQuarter = 0.25D;
        // entire chunk is 16x256x16
        // process chunk in subchunks, each one 4x8x4 blocks in size
        // 4 subchunks in x direction, each 4 blocks long
        // 32 subchunks in y direction, each 8 blocks long
        // 4 subchunks in z direction, each 4 blocks long
        // for a total of 512 subchunks
        
        // divide chunk into 4 subchunks in x direction, index as ix
        for (int ix = 0; ix < 4; ++ix)
        {
            int k_x0 = ix * 5;
            int k_x1 = (ix + 1) * 5;

            // divide chunk into 4 subchunks in z direction, index as iz
            for (int iz = 0; iz < 4; ++iz)
            {
            	int k_x0z0 = (k_x0 + iz) * 33;
                int k_x0z1 = (k_x0 + iz + 1) * 33;
                int k_x1z0 = (k_x1 + iz) * 33;
                int k_x1z1 = (k_x1 + iz + 1) * 33;
                
                
                // divide chunk into 32 subchunks in y direction, index as iy
                for (int iy = 0; iy < 32; ++iy)
                {
                	// get the noise values from the noise array
                    // these are the values at the corners of the subchunk
                	
                	///Not totally sure if this is getting this block locations actual biome...dont ask me why..
                	BiomeHeights n_x0y0z0 = heightMaps[k_x0z0 + iy];
                	BiomeHeights n_x0y0z1 = heightMaps[k_x0z1 + iy];
                	BiomeHeights n_x1y0z0 = heightMaps[k_x1z0 + iy];
                	BiomeHeights n_x1y0z1 = heightMaps[k_x1z1 + iy];
                	BiomeHeights n_x0y1z0 = heightMaps[k_x0z0 + iy + 1];
                	BiomeHeights n_x0y1z1 = heightMaps[k_x0z1 + iy + 1];
                	BiomeHeights n_x1y1z0 = heightMaps[k_x1z0 + iy + 1];
                	BiomeHeights n_x1y1z1 = heightMaps[k_x1z1 + iy + 1];
                    
                    

                    // linearly interpolate between the noise points to get a noise value for each block in the subchunk

                    double noiseStepY00 = (n_x0y1z0.height - n_x0y0z0.height) * oneEighth;
                    double noiseStepY01 = (n_x0y1z1.height - n_x0y0z1.height) * oneEighth;
                    double noiseStepY10 = (n_x1y1z0.height - n_x1y0z0.height) * oneEighth;
                    double noiseStepY11 = (n_x1y1z1.height - n_x1y0z1.height) * oneEighth;
                    
                    double noiseStartX0 = n_x0y0z0.height;
                    double noiseStartX1 = n_x0y0z1.height;
                    double noiseEndX0 = n_x1y0z0.height;
                    double noiseEndX1 = n_x1y0z1.height;
                    
                 // subchunk is 8 blocks high in y direction, index as jy
                    for (int jy = 0; jy < 8; ++jy)
                    {
                        
                        double noiseStartZ = noiseStartX0;
                        double noiseEndZ = noiseStartX1;
                        
                        double noiseStepX0 = (noiseEndX0 - noiseStartX0) * oneQuarter;
                        double noiseStepX1 = (noiseEndX1 - noiseStartX1) * oneQuarter;

                        // subchunk is 4 blocks long in x direction, index as jx
                        for (int jx = 0; jx < 4; ++jx)
                        {
                            double noiseStepZ = (noiseEndZ - noiseStartZ) * oneQuarter;
                            double noiseVal = noiseStartZ;

                            // subchunk is 4 blocks long in x direction, index as jz
                            for (int jz = 0; jz < 4; ++jz)
                            {
                            	//Removes chance of spawning block at height level if its not in this biomes range...theoretically
                            	if(iy * 8 + jy > ((ICustomBiome)n_x0y0z0.biome).GetMaxHeight()) {
                            		continue;
                            	}
                            	if(iy * 8 + jy < ((ICustomBiome)n_x0y0z0.biome).GetMinHeight()) {
                            		continue;
                            	}
                            	
                            	//Emerald is our replacement block for calculations in our custom biomes genTerrainBlocks
                            	if ((noiseVal) > 0.0D) {
                                    primer.setBlockState(ix * 4 + jx, iy * 8 + jy, iz * 4 + jz, Blocks.EMERALD_BLOCK.getDefaultState());
                                }
                            	
                            	
                                noiseVal += noiseStepZ;
                            }

                            noiseStartZ += noiseStepX0;
                            noiseEndZ += noiseStepX1;
                        }

                        noiseStartX0 += noiseStepY00;
                        noiseStartX1 += noiseStepY01;
                        noiseEndX0 += noiseStepY10;
                        noiseEndX1 += noiseStepY11;
                    }
                }
            }
        }

    }

    
    
    
    
    
    /**
     * Havent actually identified the exact functionality of this bubt its name is probably what it does. (replaceBiomeBlocks) && (genTerrainBlocks)
     */
    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, IChunkGenerator generator, Biome[] biomes) {
        if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(generator, x, z, primer, this.world)) return;
        this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (x * 16), (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                Biome biome = biomes[j + i * 16];
                biome.genTerrainBlocks(this.world, this.random, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
            }
        }
    }
    
    
}
