

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


//Could probably get rid of all this and write my own later. 
//Cant be bothered using the override for teleporter.placeEntity that is called in entity.changedim
//Want access to more vars. Just call change dim with a empty teleporter that does nothing 
//then do my own code once its returned...hackyyyy
public class PurgatoryTeleporter extends Teleporter {

	
	public PurgatoryTeleporter(WorldServer overWorld, WorldServer purgatoryWorld) {
		super(purgatoryWorld);

		PurgatoryWorldServer = purgatoryWorld;
		OverWorldServer = overWorld;
		
	}


	public WorldServer PurgatoryWorldServer;
	public WorldServer OverWorldServer;
	
	//Store the sender X receiver
	public Long2ObjectMap<PortalMapping> blockMappingCache = new Long2ObjectOpenHashMap<PortalMapping>();

	
	
	//public BlockPos senderControllerBlockPos;
    //public BlockPos receiverControllerBlockPos;

    //public final WorldServer newWorldServer;
    //public final WorldServer oldWorldServer;
    
    //Safe coords of land x,y,z
    //private double playerX;
    //private double playerY;
    //private double playerZ;
    
    
	
	
	
	
	
	/*
	 * 
	 * TODO: Offset control block from surface based on position of player.
	 * eg: Player on ground but block is 2 above the ground infront. It should place same in otherworld not on surface.
	 */
	public Boolean CalculateReceiverPortal(Entity entity, int targetDim, BlockPos senderBlockPos) {
		
        long l = ChunkPos.asLong(senderBlockPos.getX(), senderBlockPos.getY());
		double surfaceY = senderBlockPos.getY();

		PortalMapping pm ;
		if(blockMappingCache.containsKey(l)) {
			pm = blockMappingCache.get(l);
			//Maybe double check that the blockstate in server still exists???
		}
		else {
			
			//Make Portal blockpos identical to receiver
			BlockPos receiverControllerBlockPos = new BlockPos(senderBlockPos);

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
			
			//placeholder var dunno if java get any optimisation for reusing vars
			IBlockState bs;
			
	        boolean portalFound = false;
	        Biome bi = targetServer.getBiome(receiverControllerBlockPos);
	        
	        if(bi.getBiomeName() == "VoidBiome") {
	        	Main.LogMesssage("VOID Selected");
	        	//Should be 1 to 1
	        	//If portal already exists but not in cache from restart then dont build a new one
	        	bs = targetServer.getBlockState(receiverControllerBlockPos);
	        	if(bs.getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
	        		portalFound = true;
	        	}
	        }
	        else {
	        	//Find safe block on land if biome is available
	        	//From skycap to bedrock...
		        for(int i = 256; i > 0; i--) {		        	
		        	receiverControllerBlockPos = new BlockPos(receiverControllerBlockPos.getX(), i, receiverControllerBlockPos.getZ());
		        	bs = targetServer.getBlockState(receiverControllerBlockPos);
		        	
		        	if(bs.getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
		        		//We have already defined controller block in this realm.
		        		//So we know we dont need to recreate portal.
		        		//but we do need to add the block to cache
		        		portalFound = true;
		        		Main.log("Existing Portal Found");
		        		break;
		        	}
		        	//Change this...MMM can land on structures or on other stuff its not meant to?
		        	if(bs.getMaterial() != Material.AIR ) {
		        		surfaceY = i;
		        		break;
		        	}
		        }
	        }
	        
			if(!portalFound) {
				//Block Formation from previous world to build in new world
				boolean flag = false;
				Map<BlockPos, IBlockState> blockFrameCache = new HashMap<BlockPos, IBlockState>();
		        for(int x = -6; x <= 6; x++) {
	        		for(int z = -6; z <= 6; z++) {
	        			flag = false;
	        			//Go from top down. If x wool found between portal  block and non floor then offset surface value
	        			for(int y = 6; y >= -6; y--) {
		                	
		                	BlockPos bp = new BlockPos(senderBlockPos.getX()+x, senderBlockPos.getY()+y, senderBlockPos.getZ()+z);
		                	bs = oldServer.getBlockState(bp);
		                	
		                	if(bs.getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
		                		blockFrameCache.put(bp, bs);
		                		flag = true;
		                	}
		                	else if(bs.getBlock().getRegistryName() == Blocks.WOOL.getRegistryName() ) {
		                		if(flag) {
		                			//surfaceY++;
		                		}
		                		blockFrameCache.put(bp, bs);
		                	}
		                }
		            }
		        } 
	
				Main.LogMesssage("Found Portal Blocks: " + blockFrameCache.size());
				//Build new portal in otherworld
				for(Map.Entry<BlockPos, IBlockState> entry: blockFrameCache.entrySet()) {
					targetServer.setBlockState(new BlockPos(entry.getKey().getX(), surfaceY + entry.getKey().getY() - senderBlockPos.getY(), entry.getKey().getZ()), entry.getValue());
		        }
			}

			//Did we build successfully..
        	if(targetServer.getBlockState(receiverControllerBlockPos).getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName()) {
        		Main.log("Portal Success");
        	}
        	else {
        		Main.log("Portal Fail");
        	}
        	
        	
        	if(targetDim == ConfigurationHandler.dimensionId) {
        		pm = new PortalMapping(receiverControllerBlockPos, senderBlockPos);
	        }
	        else {
        		pm = new PortalMapping(senderBlockPos, receiverControllerBlockPos);
	        }
        	
        	blockMappingCache.put(l, pm);
		}
		
		
		
		if(targetDim == ConfigurationHandler.dimensionId) {
			//get difference
			double offset = pm.OverWorldBlock.getY() - entity.posY;
			surfaceY = pm.PurgatoryBlock.getY() + offset;
        }
        else {

			double offset = pm.PurgatoryBlock.getY() - entity.posY;
			surfaceY = pm.OverWorldBlock.getY() + offset;
        }
		
		
		//Maybe
		entity.posY = surfaceY + 2;
		
		
		return true;
	}
	
	
	
	
    
    
    /*
     * This is called by playerlist.teleport
     * (non-Javadoc)
     * @see net.minecraft.world.Teleporter#placeEntity(net.minecraft.world.World, net.minecraft.entity.Entity, float)
     */
    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
    	
    	/*
    	if (entity instanceof EntityPlayerMP) {
        	Main.LogMesssage("PE->MP");
            placeInPortal(entity, yaw);
        }
        else {
        	Main.LogMesssage("placeEntity->SP->Is this ever hit...");
            placeInExistingPortal(entity, yaw);
        }
    	*/
    	
    }
    
    
    /*
     * Places entity at an existing portal location if it exists.
     * True is success
     * (non-Javadoc)
     * @see net.minecraft.world.Teleporter#placeInExistingPortal(net.minecraft.entity.Entity, float)
     */
    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
    	
    	//Helpful code
    	/*
    	
    	int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posZ);
        long l = ChunkPos.asLong(j, k);
        
        
        
        //grab from cache quick lookup...
        //i wonder if i can remove this just use direct location of other block and check if it still exists.
        if (this.destinationCoordinateCache.containsKey(l))
        {
            Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)this.destinationCoordinateCache.get(l);

            teleporter$portalposition.lastUpdateTime = this.world.getTotalWorldTime();
            
            if (entityIn instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP)entityIn).connection.setPlayerLocation(
                		teleporter$portalposition.getX(), 
                		teleporter$portalposition.getY()+1, 
                		teleporter$portalposition.getZ(), 
                		entityIn.rotationYaw, entityIn.rotationPitch);
            }
            else
            {
                entityIn.setLocationAndAngles(
                		teleporter$portalposition.getX(), 
                		teleporter$portalposition.getY()+1, 
                		teleporter$portalposition.getZ(), 
                		entityIn.rotationYaw, entityIn.rotationPitch);
            }
            
            Main.log("Portal Exist");
            
            return true;
        }
        else
        {
        	//means it doesnt exist
        	//
        	Main.log("Portal Doesnt Exist");

        	//Make one...
        	return false;
        }
    	*/
        
        return false;
    }
    

    /*
     * 
     * (non-Javadoc)
     * @see net.minecraft.world.Teleporter#placeInPortal(net.minecraft.entity.Entity, float)
     */
	@Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
		/*
		if (!this.placeInExistingPortal(entityIn, rotationYaw))
        {
			
            this.makePortal(entityIn);
            this.placeInExistingPortal(entityIn, rotationYaw);
        }
		else {
			Main.log("portal already exists ????");
		}
		*/
    }


	@Override
	public boolean makePortal(Entity entityIn) {
		
		//Main.log("Making Portal");

        //long l = ChunkPos.asLong(senderBlockPos.getX(), senderBlockPos.getZ());

		/*
        //Get ground of dim
        BlockPos receiverControllerBlockPos = new BlockPos(senderBlockPos);
        IBlockState bs = newWorldServer.getBlockState(receiverControllerBlockPos);

        int surfaceY = senderControllerBlockPos.getY();
        
        Biome bi = newWorldServer.getBiome(receiverControllerBlockPos);
        if(bi.getBiomeName() == "VoidBiome") {
        	Main.LogMesssage("VOID Selected");
        	
        	newWorldServer.setBlockState(receiverControllerBlockPos, ModBlocks.MYSTICAL_BLOCK.getDefaultState());
        	
        }
        else {
	        	
        	//Find safe block on land if biome is available
        	//From skycap to bedrock...
	        for(int i = 256; i > 0; i--) {		        	
	        	receiverControllerBlockPos = new BlockPos(receiverControllerBlockPos.getX(), i, receiverControllerBlockPos.getZ());
	        	bs = newWorldServer.getBlockState(receiverControllerBlockPos);
	        	//Change this...MMM can land on structures or on other stuff its not meant to?
	        	if(bs.getMaterial() != Material.AIR) {
	        		surfaceY = i;
	        		break;
	        	}
	        }
        }
        
		
		
		//Block Formation from previous world to build in new world
		Map<BlockPos, IBlockState> blockFrameCache = new HashMap<BlockPos, IBlockState>();
        for(int x = -6; x <= 6; x++) {
        	for(int y = -6; y <= 6; y++) {
        		for(int z = -6; z <= 6; z++) {
                	
                	BlockPos bp = new BlockPos(senderControllerBlockPos.getX()+x, senderControllerBlockPos.getY()+y, senderControllerBlockPos.getZ()+z);
                	
                	bs = oldWorldServer.getBlockState(bp);
                	
                	if(bs.getBlock().getRegistryName() == Blocks.WOOL.getRegistryName() || 
                			bs.getBlock().getRegistryName() == ModBlocks.MYSTICAL_BLOCK.getRegistryName() ) {
                		blockFrameCache.put(bp, bs);
                	}
                }
            }
        }
	        
        entityIn.posY = surfaceY + 1;
        
		Main.LogMesssage("Found Portal Blocks: " + blockFrameCache.size());
		for(Map.Entry<BlockPos, IBlockState> entry: blockFrameCache.entrySet()) {
			newWorldServer.setBlockState(new BlockPos(entry.getKey().getX(), surfaceY + entry.getKey().getY() - senderControllerBlockPos.getY(), entry.getKey().getZ()), entry.getValue());
        }
		*/
		
		return true;
	}
	
	
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.world.Teleporter#removeStalePortalLocations(long)
	 */
	@Override
	public void removeStalePortalLocations(long worldTime) {
    	//Main.LogMesssage("StaleRemove");
	}
	
	
	
	
	
	
	


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
