package com.zeropoints.ensoulomancy.blocks;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.blocks.counter.TileEntityCounter;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;
import com.zeropoints.ensoulomancy.world.PurgatoryTeleporter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.w3c.dom.events.Event;


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
				newDimension = ConfigurationHandler.dimensionId;
				
			}
			else {
				newDimension = 0;
			}
			
	        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
	        MinecraftServer server = entity.getEntityWorld().getMinecraftServer();
	        WorldServer overworldServer = server.getWorld(0);
	        WorldServer purgatoryServer = server.getWorld(ConfigurationHandler.dimensionId);
	        //entity.addExperienceLevel(0);
	        
	        int x = entityPlayerMP.getPosition().getX(); 
			int y = entityPlayerMP.getPosition().getY(); 
			int z = entityPlayerMP.getPosition().getZ(); 
			
	
	        if (purgatoryServer == null || purgatoryServer.getMinecraftServer() == null){ //Dimension doesn't exist
	            throw new IllegalArgumentException("Dimension: "+newDimension+" doesn't exist!");
	        }
	
	        boolean foundTeleporter = false;
	        
	        if(ConfigurationHandler.PURGATORY_TELEPORTER == null) {
	        	ConfigurationHandler.PURGATORY_TELEPORTER = new PurgatoryTeleporter(overworldServer, purgatoryServer);
	        	purgatoryServer.customTeleporters.add(ConfigurationHandler.PURGATORY_TELEPORTER);
	        	foundTeleporter = true;
	        }
	        
	        /*
			for (final Teleporter item : purgatoryServer.customTeleporters) {

				if(item instanceof PurgatoryTeleporter) {
					
					pt = (PurgatoryTeleporter) item;
				}
	        }
	        
			
			if(!foundTeleporter) {
				
				pt = 
				
			}*/
	        
			
	        ConfigurationHandler.PURGATORY_TELEPORTER.CalculateReceiverPortal(entityPlayerMP, newDimension, blockPos);
			
			
	        //Test both below for server problems...SP both work
	        entity.changeDimension(newDimension, ConfigurationHandler.PURGATORY_TELEPORTER);
	        

	         //Also is this owserver or pwserver...hmmm
	        /*
	         overworldServer.getMinecraftServer().getPlayerList()
	        		.transferPlayerToDimension(entityPlayerMP, newDimension, ConfigurationHandler.PURGATORY_TELEPORTER);
			*/
	        
	        
	        
	        
		}
    }
	
	
	
	

}
