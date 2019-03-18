package com.zeropoints.ensoulomancy.util;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class BlockComparing {

	
	public BlockPos TargetPos;
	public List<IBlockState> PossibleStates;
	
	
	public BlockComparing(BlockPos bp, List<IBlockState> ps)
	{
		TargetPos = bp;
		PossibleStates = ps;
	}
	

}
