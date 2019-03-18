

package com.zeropoints.ensoulomancy.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.blocks.BlockSoulSkull;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.init.ModDimensions;
import com.zeropoints.ensoulomancy.tileentity.TileEntitySoulSkull;
import com.zeropoints.ensoulomancy.util.BlockComparing;
import com.zeropoints.ensoulomancy.util.PortalMapping;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;



public class PurgatoryTeleporter extends Teleporter {

	/**
	 * Teleporter object for controlling how a player is sent to the purgatory dim and how the portal is created in the dim
	 * 
	 * Still extends Teleporter for entity.chanedim function. Any actual purgatoryteleporter functionality is called prior to calling entity.chanedim
	 * 
	 * When i get better i will learn how to write this better...
	 * But for now the reason im not using the base functions is because i wanted the senderblock location for some calculations for building destinationblock portal
	 * 
	 */
	public PurgatoryTeleporter(WorldServer overWorld, WorldServer purgatoryWorld) {
		super(purgatoryWorld);

		PurgatoryWorldServer = purgatoryWorld;
		OverWorldServer = overWorld;
		
	}


	//Keep track of which both servers for entire life of this class...Maybe make them static...
	public WorldServer PurgatoryWorldServer;
	public WorldServer OverWorldServer;
	
	
	//Store the sender X receiver
	public Long2ObjectMap<PortalMapping> blockMappingCache = new Long2ObjectOpenHashMap<PortalMapping>();

	
	
	
	
	/*
	 * Shape of our portal to match
	 */
	public List<BlockComparing> Portal = InitPortalShape();
	
	private static List<BlockComparing> InitPortalShape(){
		HashMap<String, BlockComparing> blocksMap = new HashMap<String, BlockComparing>();

		//White space negative.
		for(int x = -2; x <= 2; x++) {
			for(int y = 0; y <= 4; y++) {
				for(int z = -2; z <= 2; z++) {
					blocksMap.put(x + "," + y + "," + z, new BlockComparing(new BlockPos(x,y,z), Arrays.asList(Blocks.AIR.getDefaultState())));
				}	
			}	
		}
		
		
		//Bottom-Obsidian-Circle+0
		blocksMap.put("1,0,0", new BlockComparing(new BlockPos(1,0,0), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		blocksMap.put("1,0,1", new BlockComparing(new BlockPos(1,0,1), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		blocksMap.put("1,0,-1", new BlockComparing(new BlockPos(1,0,-1), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		blocksMap.put("-1,0,0", new BlockComparing(new BlockPos(-1,0,0), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		blocksMap.put("-1,0,1", new BlockComparing(new BlockPos(-1,0,1), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		blocksMap.put("-1,0,-1", new BlockComparing(new BlockPos(-1,0,-1), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		blocksMap.put("0,0,-1", new BlockComparing(new BlockPos(0,0,-1), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		blocksMap.put("0,0,1", new BlockComparing(new BlockPos(0,0,1), Arrays.asList(Blocks.OBSIDIAN.getDefaultState())));
		

		//Corners+0
		blocksMap.put("2,0,2", new BlockComparing(new BlockPos(2,0,2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("2,0,-2", new BlockComparing(new BlockPos(2,0,-2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-2,0,2", new BlockComparing(new BlockPos(-2,0,2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-2,0,-2", new BlockComparing(new BlockPos(-2,0,-2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));


		//Corners+1
		blocksMap.put("2,1,2", new BlockComparing(new BlockPos(2,1,2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("2,1,-2", new BlockComparing(new BlockPos(2,1,-2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-2,1,2", new BlockComparing(new BlockPos(-2,1,2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-2,1,-2", new BlockComparing(new BlockPos(-2,1,-2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		//Corners+2
		blocksMap.put("2,2,2", new BlockComparing(new BlockPos(2,2,2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("2,2,-2", new BlockComparing(new BlockPos(2,2,-2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-2,2,2", new BlockComparing(new BlockPos(-2,2,2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-2,2,-2", new BlockComparing(new BlockPos(-2,2,-2), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		//Corners-Skull+3
		blocksMap.put("2,3,2", new BlockComparing(new BlockPos(2,3,2), Arrays.asList(Blocks.SKULL.getDefaultState(), ModBlocks.SOUL_SKULL.getDefaultState() )));
		blocksMap.put("2,3,-2", new BlockComparing(new BlockPos(2,3,-2), Arrays.asList(Blocks.SKULL.getDefaultState(), ModBlocks.SOUL_SKULL.getDefaultState() )));
		blocksMap.put("-2,3,2", new BlockComparing(new BlockPos(-2,3,2), Arrays.asList(Blocks.SKULL.getDefaultState(), ModBlocks.SOUL_SKULL.getDefaultState() )));
		blocksMap.put("-2,3,-2", new BlockComparing(new BlockPos(-2,3,-2), Arrays.asList(Blocks.SKULL.getDefaultState(), ModBlocks.SOUL_SKULL.getDefaultState() )));

		//Top-Obsidian-Circle+4
		blocksMap.put("1,4,0", new BlockComparing(new BlockPos(1,4,0), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("1,4,1", new BlockComparing(new BlockPos(1,4,1), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("1,4,-1", new BlockComparing(new BlockPos(1,4,-1), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-1,4,0", new BlockComparing(new BlockPos(-1,4,0), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-1,4,1", new BlockComparing(new BlockPos(-1,4,1), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("-1,4,-1", new BlockComparing(new BlockPos(-1,4,-1), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("0,4,-1", new BlockComparing(new BlockPos(0,4,-1), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		blocksMap.put("0,4,1", new BlockComparing(new BlockPos(0,4,1), Arrays.asList(Blocks.STONEBRICK.getDefaultState())));
		
		
		//Top-Center+4
		blocksMap.put("0,4,0", new BlockComparing(new BlockPos(0,4,0), Arrays.asList(Blocks.GOLD_BLOCK.getDefaultState())));
		
		//Bottom-Center+0
		blocksMap.put("0,0,0", new BlockComparing(new BlockPos(0,0,0), Arrays.asList(ModBlocks.MYSTICAL_BLOCK.getDefaultState())));
				
		return new ArrayList<BlockComparing>(blocksMap.values());
	}
	
	
	
	
	/**
	 * Performs everything for creating a portal
	 */
	public Boolean CalculateReceiverPortal(Entity entity, int targetDim, BlockPos senderBlockPos) {

		//Cant have 2 portals in same chunk
        long chunkPos = ChunkPos.asLong(senderBlockPos.getX()/16, senderBlockPos.getZ()/16);

		
		if(blockMappingCache.containsKey(chunkPos)) {
			PortalMapping pm = blockMappingCache.get(chunkPos);

			Main.log("Portal Exists");
			
			//If this isnt the same block in cache then 
			if(senderBlockPos.getX() != pm.OverWorldBlock.getX() || senderBlockPos.getZ() != pm.OverWorldBlock.getZ()) {
				Main.log("Portal Chunk Collision Positions Mismatch");
				return false;
			}
			//Maybe double check that the blockstate in server still exists???
			ShiftEntityY(entity, targetDim, pm);
			return true;
		}
		else {
			//Check parts of portal.
			Main.log("Checking Structure");
			WorldServer targetServer;
			WorldServer sendingServer;
			if(targetDim == ModDimensions.dimensionId) {
				sendingServer = OverWorldServer;
				targetServer = PurgatoryWorldServer;
	        }
	        else {
	        	sendingServer = PurgatoryWorldServer;
				targetServer = OverWorldServer;
	        }
			
			
			for(int i = 0; i < Portal.size(); i++) {
				String notFoundStr = "Block Match Not Found: ";

				BlockPos tmpy = senderBlockPos.add(Portal.get(i).TargetPos);
				boolean found = false;
				for(int j = 0; j < Portal.get(i).PossibleStates.size(); j++) {
	    			if(sendingServer.getBlockState(tmpy).getBlock().getDefaultState() == Portal.get(i).PossibleStates.get(j)) {
	    				Main.log("Portal Structure Block Match Found: " + sendingServer.getBlockState(tmpy) + " - " + Portal.get(i).PossibleStates.get(j));
	    				found = true;
	    				break;
	    			}
	    			else {
	    				notFoundStr += Portal.get(i).PossibleStates.get(j) + ". ";
	    			}
				}
				
				if(!found) {
					Main.log(notFoundStr);
					return found;
				}
			}
			
			//build corresponding portal
			if(BuildPortal(entity, sendingServer, targetServer, targetDim, senderBlockPos, chunkPos)) {
				//dont do this if buildportal fails.
				PortalMapping pm = blockMappingCache.get(chunkPos);
				ShiftEntityY(entity, targetDim, pm);
				return true;
			}
			else {
				return false;
			}
			
		}
		
	
	}
	
	
	
	/*
	 * Places the entity at Y away from  the control block.
	 * As the y isnt  a complete 1 to 1 mapping
	 */
	private Boolean ShiftEntityY(Entity entity, int targetDim, PortalMapping pm) {
	
		int surfaceY;

		Main.log("OW Block:" + pm.OverWorldBlock.getY());
		Main.log("PG Block:" + pm.PurgatoryBlock.getY());
		if(targetDim == ModDimensions.dimensionId) {
			//get difference
			double offset = entity.posY - pm.OverWorldBlock.getY();

			Main.log("offset Y:" + offset);

			surfaceY = (int) (pm.PurgatoryBlock.getY() + offset);
        }
        else {

			double offset = entity.posY - pm.PurgatoryBlock.getY();
			Main.log("offset Y:" + offset);
			surfaceY = (int) (pm.OverWorldBlock.getY() + offset);
        }
		
		entity.posY = surfaceY;
		Main.log("Player new Y:" + entity.posY);
		
		return true;
	}
	
    
	
	
	/**
	 * Builds the portal using blocks from the sending dimension.
	 * Only rebuilds if it can't find one that already exists.
	 * 
	 * TODO: Look into caching into a file the mappings
	 * 
	 */
	private Boolean BuildPortal(Entity entity, WorldServer sendingServer, WorldServer targetServer, int targetDim, BlockPos senderBlockPos, long chunkPos) {
		//Make Portal blockpos identical to receiver
		BlockPos receiverControllerBlockPos = new BlockPos(senderBlockPos);
		IBlockState bs;
        boolean portalFound = false;
        Biome bi = targetServer.getBiome(receiverControllerBlockPos);
        
		
        //When the biome maps directly to void biome just build at that location
    	//Due to restart the portal may already exist before. Check that before actually re-building portal
        if(bi.getBiomeName() == "VoidBiome") {
        	Main.log("VOID Selected");
        	bs = targetServer.getBlockState(receiverControllerBlockPos);
        	if(bs.getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
        		portalFound = true;
        	}
        }
        else {
    		Boolean newSurfaceYSet = false;
    		
    		//Any other biome other then void will store the players portal at the surfaceY level for that biome. But also checks if the portal already exists in the x,z first.
	        for(int i = 256; i > 0; i--) {		        	
	        	BlockPos tmpBlockPos = new BlockPos(senderBlockPos.getX(), i, senderBlockPos.getZ());
	        	bs = targetServer.getBlockState(tmpBlockPos);
	        	//Checks that we already havent got a mystical block in this chunkpos that was removed from restart.
	        	if(bs.getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
	        		receiverControllerBlockPos = tmpBlockPos;
	        		portalFound = true;
	        		//Just incase but im pretty sure its not needed.
	        		Main.log("Existing Portal Found");
	        		break;
	        	}
	        	
	        	if(bs.getMaterial() != Material.AIR && !newSurfaceYSet ) {
	        		newSurfaceYSet = true;
	        		//Go one block backwards to above the surface.
	        		receiverControllerBlockPos = tmpBlockPos.add(0, 1, 0);
	        	}
	        }
	        
	        //We found a surface. But check around for a clear space as to not delete blocks
	        if(newSurfaceYSet) {
	        	//TODO: Possible future code to check for blocks that we are allowed to delete. eg air/sand/dirt/stone/cobble......OR our structures...
	        	
	        }
        }
        

		//Portal not found so we will build a corresponding portal
		if(!portalFound) {
			for(int i = 0; i < Portal.size(); i++) {
				BlockPos receiverBlockPosTarget = receiverControllerBlockPos.add(Portal.get(i).TargetPos);
				IBlockState targetIBlockState = targetServer.getBlockState(receiverBlockPosTarget);
				if(targetIBlockState.getMaterial() != Material.AIR) {
					Main.log("Portal Erasing Block: " + targetIBlockState);
				}
    			
	        	BlockPos senderBlockPosTarget = senderBlockPos.add(Portal.get(i).TargetPos);
	        	IBlockState senderIBlockState = sendingServer.getBlockState(senderBlockPosTarget);
	        	TileEntity senderTileEntity = null;
	        	if(senderIBlockState.getBlock() instanceof BlockSkull || senderIBlockState.getBlock() instanceof BlockSoulSkull) {
	        		//Means we have a TileEntity
	        		senderTileEntity = sendingServer.getTileEntity(senderBlockPosTarget);
	        	}
				targetServer.setBlockState(new BlockPos(receiverBlockPosTarget), sendingServer.getBlockState(senderBlockPosTarget));				
				if(senderTileEntity != null) {
					TileEntity tileEntityTarget = targetServer.getTileEntity(receiverBlockPosTarget);					
					if(senderTileEntity instanceof TileEntitySkull) {
		        		((TileEntitySkull)tileEntityTarget).setSkullRotation( ((TileEntitySkull)senderTileEntity).getSkullRotation());
		        		((TileEntitySkull)tileEntityTarget).setType( ((TileEntitySkull)senderTileEntity).getSkullType());
					}
					else if(senderTileEntity instanceof TileEntitySoulSkull) {
		        		((TileEntitySoulSkull)tileEntityTarget).setSkullRotation( ((TileEntitySoulSkull)senderTileEntity).getSkullRotation());
		        		((TileEntitySoulSkull)tileEntityTarget).setType( ((TileEntitySoulSkull)senderTileEntity).getSkullType());
					}
				}
			}
			
			targetServer.setBlockState(receiverControllerBlockPos,ModBlocks.MYSTICAL_BLOCK.getDefaultState());
			Main.log("Portal Built");
		}

		
		//Did we build successfully...Or map successfully.
    	if(targetServer.getBlockState(receiverControllerBlockPos).getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
    		PortalMapping pm ;
        	if(targetDim == ModDimensions.dimensionId) {
        		pm = new PortalMapping(receiverControllerBlockPos, senderBlockPos);
            }
            else {
        		pm = new PortalMapping(senderBlockPos, receiverControllerBlockPos);
            }
        	//Validate that the dim of sender/receiver are different before adding to cache.
        	blockMappingCache.put(chunkPos, pm);
    		Main.log("Portal Success");
    		return true;
        	
    	}
    	else {
    		Main.log("Portal Fail");
    		return false;
    	}
    	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * OVERRIDES FOR BASE MODEL 
	 */
	
	
    
    /*
     * This is called by playerlist.teleport
     * (non-Javadoc)
     * @see net.minecraft.world.Teleporter#placeEntity(net.minecraft.world.World, net.minecraft.entity.Entity, float)
     */
    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
    	
    }
    
    
    /*
     * Places entity at an existing portal location if it exists.
     * True is success
     * (non-Javadoc)
     * @see net.minecraft.world.Teleporter#placeInExistingPortal(net.minecraft.entity.Entity, float)
     */
    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
    	
        return true;
    }
    

    /*
     * 
     * (non-Javadoc)
     * @see net.minecraft.world.Teleporter#placeInPortal(net.minecraft.entity.Entity, float)
     */
	@Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
		
    }


	@Override
	public boolean makePortal(Entity entityIn) {
		
		return true;
	}
	
	
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.world.Teleporter#removeStalePortalLocations(long)
	 */
	@Override
	public void removeStalePortalLocations(long worldTime) {
    	
	}
	
	
	
	
	
	



}
