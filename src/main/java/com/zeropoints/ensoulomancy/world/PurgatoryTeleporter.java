

package com.zeropoints.ensoulomancy.world;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModBlocks;
import com.zeropoints.ensoulomancy.util.ConfigurationHandler;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;


/*
 * Teleporter object for controlling how a player is sent to the purgatory dim and how the portal is created in the dim
 * 
 * Still extends Teleporter for entity.chanedim function. Any actual purgatoryteleporter functionality is called prior to calling entity.chanedim
 * 
 * When i get better i will learn how to write this better...
 * But for now the reason im not using the base functions is because i wanted the senderblock location for some calculations for building destinationblock portal
 * 
 */
public class PurgatoryTeleporter extends Teleporter {

	
	public PurgatoryTeleporter(WorldServer overWorld, WorldServer purgatoryWorld) {
		super(purgatoryWorld);

		PurgatoryWorldServer = purgatoryWorld;
		OverWorldServer = overWorld;
		
	}


	//Server objs
	public WorldServer PurgatoryWorldServer;
	public WorldServer OverWorldServer;
	
	
	//Store the sender X receiver
	public Long2ObjectMap<PortalMapping> blockMappingCache = new Long2ObjectOpenHashMap<PortalMapping>();

	
	
	
	
	/*
	 * Performs everythingn for creating a portal
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
			if(BuildPortal(entity, targetDim, senderBlockPos, chunkPos)) {
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
		if(targetDim == ConfigurationHandler.dimensionId) {
			//get difference
			double offset = pm.OverWorldBlock.getY() - entity.posY;
			surfaceY = (int) (pm.PurgatoryBlock.getY() + offset);
        }
        else {

			double offset = pm.PurgatoryBlock.getY() - entity.posY;
			surfaceY = (int) (pm.OverWorldBlock.getY() + offset);
        }
		
		entity.posY = surfaceY + 3;
		
		return true;
	}
	
    
	
	
	/*
	 * Builds the portal using blocks from the sending dimension.
	 * Only rebuilds if it can't find one that already exists.
	 * 
	 * Slight annoying condition can be hit but i can't be bothered fixing:
	 * 		If you already had a portal built in chunk and server restarts and you put a new sender block in same chunk
	 * 		it will rebuild a portal for the new sender block unless you first click the original block. This is kind of an exception case though
	 * 
	 * Other problems:
	 * 		Since i scan blocks around if a player builds 2 portal blocks and places them next to each other at chunk boundary 
	 * 		and they keep going back and forth and restarting game it will keep rebuilding portal. Its a slow way to dupe these blocks.
	 * 		I dont think its a game breaking problem as it takes too much effort for little gain when you can easily farm the blocks..
	 * 
	 * TODO: Will need to alter this later so it will not replace Biome structure blocks. 
	 * TODO: Get bounds of portal and maybe dont build in targetdim until it finds a box of empty area to build portal.
	 */
	private Boolean BuildPortal(Entity entity, int targetDim, BlockPos senderBlockPos, long chunkPos) {
        int surfaceY = senderBlockPos.getY();
		WorldServer targetServer;
		WorldServer oldServer;
		if(targetDim == ConfigurationHandler.dimensionId) {
			oldServer = OverWorldServer;
			targetServer = PurgatoryWorldServer;
        }
        else {
        	oldServer = PurgatoryWorldServer;
			targetServer = OverWorldServer;
        }
		//Make Portal blockpos identical to receiver
		BlockPos receiverControllerBlockPos = new BlockPos(senderBlockPos);
		IBlockState bs;
        boolean portalFound = false;
        Biome bi = targetServer.getBiome(receiverControllerBlockPos);
		
        //When the biome maps directly to void biome just build at that location
        if(bi.getBiomeName() == "VoidBiome") {
        	Main.LogMesssage("VOID Selected");
        	//Due to restart the portal may already exist before. Check that before actually re-building portal
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
	        		surfaceY = i;
	        	}
	        }
        }
        
		if(!portalFound) {
			//Portal not found so we will get the blocks from the sender dim that are relevant to new portal and add to cache for building.
			//will only takke control wool.
			Map<BlockPos, IBlockState> blockFrameCache = new HashMap<BlockPos, IBlockState>();
	        for(int x = -6; x <= 6; x++) {
        		for(int z = -6; z <= 6; z++) {
        			//Go from top down. No  reason anymore but before i was attempting to do magic 
        			//where i would detect blocks underneath and offset portal base using this to make better structures
        			for(int y = 6; y >= 0; y--) {	   
	                	BlockPos oldbp = new BlockPos(senderBlockPos.getX()+x, senderBlockPos.getY()+y, senderBlockPos.getZ()+z);
	                	BlockPos newbp = new BlockPos(senderBlockPos.getX()+x, surfaceY + y, senderBlockPos.getZ()+z);
	                	bs = oldServer.getBlockState(oldbp);
	                	//Add the control block to cache, which is always at center
	                	
	                	if(y == 0 && x == 0 && z == 0) {
	                		receiverControllerBlockPos = newbp;
	                		blockFrameCache.put(newbp, bs);
	                	}
	                	if(bs.getBlock().getRegistryName() == Blocks.WOOL.getRegistryName() ) {
	                		
	                		blockFrameCache.put(newbp, bs);
	                	}
	                }
	            }
	        } 

			Main.LogMesssage("Found Portal Blocks: " + blockFrameCache.size());
			//Build new portal in otherworld
			for(Map.Entry<BlockPos, IBlockState> entry: blockFrameCache.entrySet()) {
				targetServer.setBlockState(new BlockPos(entry.getKey().getX(), entry.getKey().getY(), entry.getKey().getZ()), entry.getValue());
	        }

			Main.log("Portal Built");
		}

		
		
		//Did we build successfully...Or map successfully.
    	if(targetServer.getBlockState(receiverControllerBlockPos).getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
    		PortalMapping pm ;
        	if(targetDim == ConfigurationHandler.dimensionId) {
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

	

}
