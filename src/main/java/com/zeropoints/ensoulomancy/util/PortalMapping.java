package com.zeropoints.ensoulomancy.util;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;



/*
 * Obj to store a 1 to 1 mapping of sender rececivers for easy lookup
 */
public class PortalMapping 
{
	public BlockPos PurgatoryBlock;
	public BlockPos OverWorldBlock;
	
	
    public PortalMapping(BlockPos pb, BlockPos ob)
    {
    	
    	PurgatoryBlock = pb;
    	OverWorldBlock = ob;
    	
    }
    
    
    
}