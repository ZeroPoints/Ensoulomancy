package com.zeropoints.ensoulomancy.blocks;

import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.world.PurgatoryTeleporter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;


//public class MysticalBlock extends BlockTileEntity<PortalTileEntity>  {


public class MysticalBlock extends BlockBase  {

	public MysticalBlock(Material materialIn) {
		super("mystical_block", materialIn);

	}

	
	
	
	@Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

		teleportToDimension(playerIn, pos);
		
        return true;
	}

	

	
	public void teleportToDimension(Entity entity, BlockPos blockPos) {
		if (entity instanceof EntityPlayerMP) {
			
			int oldDimension = entity.dimension;
			int newDimension = entity.dimension;
			
			if(oldDimension == 0) {
				newDimension = ModDimensions.dimensionId;
				
			}
			else {
				newDimension = 0;
			}
			
	        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
	        MinecraftServer server = entity.getEntityWorld().getMinecraftServer();
	        WorldServer overworldServer = server.getWorld(0);
	        WorldServer purgatoryServer = server.getWorld(ModDimensions.dimensionId);
	        //entity.addExperienceLevel(0);
	        
	
	        if (purgatoryServer == null || purgatoryServer.getMinecraftServer() == null){ //Dimension doesn't exist
	            throw new IllegalArgumentException("Dimension: "+newDimension+" doesn't exist!");
	        }
	
	        
	        if(ModDimensions.PURGATORY_TELEPORTER == null) {
	        	ModDimensions.PURGATORY_TELEPORTER = new PurgatoryTeleporter(overworldServer, purgatoryServer);
	        	purgatoryServer.customTeleporters.add(ModDimensions.PURGATORY_TELEPORTER);
	        }
	       
			
	        if (ModDimensions.PURGATORY_TELEPORTER.CalculateReceiverPortal(entityPlayerMP, newDimension, blockPos)) {
		        //Test both below for server problems...SP both work
	        	entity.changeDimension(newDimension, ModDimensions.PURGATORY_TELEPORTER);
	        }
			

			
	         //Also is this owserver or pwserver...hmmm
	        /*
	         overworldServer.getMinecraftServer().getPlayerList()
	        		.transferPlayerToDimension(entityPlayerMP, newDimension, ConfigurationHandler.PURGATORY_TELEPORTER);
			*/
	        
	        
	        
	        
		}
    }
	
	
	
	

}
