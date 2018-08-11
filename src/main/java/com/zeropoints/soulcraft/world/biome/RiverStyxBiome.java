package com.zeropoints.soulcraft.world.biome;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.zeropoints.soulcraft.init.ModBiomes;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.ChunkPrimer;


public class RiverStyxBiome extends Biome implements ICustomBiome {

	
	protected List<Biome.SpawnListEntry> highMonsterList = Lists.<Biome.SpawnListEntry>newArrayList();
	protected List<Biome.SpawnListEntry> midMonsterList = Lists.<Biome.SpawnListEntry>newArrayList();
	protected List<Biome.SpawnListEntry> lowMonsterList = Lists.<Biome.SpawnListEntry>newArrayList();
	
	
	public RiverStyxBiome(BiomeProperties properties) {
		super(properties);

		

		this.setRegistryName("sc", "riverstyx");

		this.topBlock = Blocks.WATER.getDefaultState(); 
		this.fillerBlock = Blocks.SOUL_SAND.getDefaultState(); 

		highMonsterList = Lists.newArrayList(
        	new SpawnListEntry(net.minecraft.entity.monster.EntitySilverfish.class, 5, 1, 4)
	    ) ;
		midMonsterList = Lists.newArrayList(
			new SpawnListEntry(net.minecraft.entity.monster.EntityShulker.class, 5, 1, 4)
	    ) ;
		lowMonsterList = Lists.newArrayList(
			new SpawnListEntry(net.minecraft.entity.monster.EntityPolarBear.class, 5, 1, 4)
	    ) ;
	    
        
	}
	
	
	

	@Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
		int i = worldIn.getSeaLevel();
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int j = -1;
        //int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        //K is noise for how many blocks it will FILL up. Fill goes reverse from air to fill depth
        int k = 10;
        int l = x & 15;
        int i1 = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j1 = 255; j1 >= 0; --j1)
        {
            if (j1 <= rand.nextInt(5))
            {
                chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
            }
            else
            {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

                if (iblockstate2.getMaterial() == Material.AIR)
                {
                    j = -1;
                }
                else if (iblockstate2.getBlock() == Blocks.EMERALD_BLOCK)
                {
                    if (j == -1)
                    {
                        if (k <= 0)
                        {
                            iblockstate = AIR;
                            iblockstate1 = STONE;
                        }
                        else if (j1 >= i - 4 && j1 <= i + 1)
                        {
                            iblockstate = this.topBlock;
                            iblockstate1 = this.fillerBlock;
                        }

                        if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
                        {
                            if (this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F)
                            {
                                iblockstate = ICE;
                            }
                            else
                            {
                                iblockstate = WATER;
                            }
                        }

                        j = k;

                        if (j1 >= i - 1)
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                        }
                        else if (j1 < i - 7 - k)
                        {
                            iblockstate = AIR;
                            iblockstate1 = STONE;
                            chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
                        }
                        else
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                        }
                    }
                    else if (j > 0)
                    {
                        --j;
                        chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);

                        if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1)
                        {
                            j = rand.nextInt(4) + Math.max(0, j1 - 63);
                            iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
                        }
                    }
                }
            }
        }
        
		
    }
	
	
	
	public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType, BlockPos pos)
    {

    	if(pos.getY() <= 50) {
    		return lowMonsterList;
    	}
    	if(pos.getY() > 50 && pos.getY() <= 90) {
    		return midMonsterList;
    	}
    	if(pos.getY() > 90) {
    		return highMonsterList;
    	}
		return midMonsterList;

    }
	
	
	
}
