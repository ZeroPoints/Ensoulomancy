package com.zeropoints.soulcraft.world;

import com.zeropoints.soulcraft.init.ModBiomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerPurgatory extends GenLayer {
    private Biome[] biomes = new Biome[]{
            ModBiomes.HALLOWED_BIOME,
            ModBiomes.PROFANE_BIOME,
            ModBiomes.SPIRIT_BIOME
    };

    public GenLayerPurgatory(long seed, GenLayer parentIn) {
        super(seed);
        parent = parentIn;
    }

    public GenLayerPurgatory(long seed) {
        super(seed);
    }

    @Override
    public int[] getInts(int x, int y, int width, int depth) {
        int dest[] = IntCache.getIntCache(width * depth);
        for (int dz = 0; dz < depth; dz++) {
            for (int dx = 0; dx < width; dx++) {
                initChunkSeed(dx + x, dz + y);
                dest[dx + dz * depth] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);

            }

        }
        return dest;
    }
}