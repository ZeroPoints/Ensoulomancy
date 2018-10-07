package com.zeropoints.ensoulomancy.render.player;

//import com.zeropoints.ensoulomancy.api.morphs.EntityMorph;
import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
import com.zeropoints.ensoulomancy.entity.EntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Rendering handler
 *
 * This handler is rendering handler which is responsible for few things:
 * 
 * - Player model
 */
@SideOnly(Side.CLIENT)
public class RenderingHandler {
	
    private RenderManager manager;
    private static int cameraRenderCounter = 0;
    
    //private static Map<String,RenderPlayerEvent.Pre> renderPlayerQueue = new HashMap<String,RenderPlayerEvent.Pre>();
    
    public RenderingHandler() {
    	this.manager = Minecraft.getMinecraft().getRenderManager();
    }
    
    /**
     * Attaches to the update Camera event
     * If camera is not default player's change it. 
     * Executed actions are likely to be all client-side
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void updateCamera(RenderHandEvent event) {
    	if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityCamera) {
    		EntityCamera cameraEntity = (EntityCamera)Minecraft.getMinecraft().getRenderViewEntity();
    		
    		// Reset viewing entity back to player as past event rendering time
    		if (cameraRenderCounter > cameraEntity.getTicksActive()) {
    			Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
    			cameraEntity.onStopped(); 
    			cameraEntity.setDead(); // Remove this entity
    			cameraRenderCounter = 0;
    			return;
    		}
    		
    		cameraRenderCounter++;
    		event.setCanceled(true); // Don't actually render the hand when camera is active. 
    	}
    }
    
    /**
     * Render player hook
     * 
     * This method is responsible for rendering player in case they're currently morphed or a ghost.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        
        //if (renderPlayerQueue.containsKey(player.getName())) {
        //	return;
        //}
        
        /*
        IMorphing morph = Morphing.getCapability(player);

        // Render morph
        if (morph != null && morph.renderPlayer(player, event.getX(), event.getY(), event.getZ(), player.rotationYaw, event.getPartialRenderTick())) {
        	event.setCanceled(true);
        	return;
        }
        */
        
        IGhost ghost = Ghost.getCapability(player);
        
        // Render ghost
        if (ghost != null && ghost.renderPlayer(player)) {
        	// This adds the player to a rendering queue so then they get rendered after TESRs (Tile Entity Special Renderer)
        	// This causes extreme rendering bugs. Undo for now
        	/*renderPlayerQueue.put(player.getName(), event);
        	event.setCanceled(true);*/
        }
    }
    
    /**
     * This renders after every other entity has rendered. 
     * By doing this we make sure TESRs get blended properly with transparent entities.
     */
    /*@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderWorldLast(RenderWorldLastEvent event) {
    	for (RenderPlayerEvent.Pre e : renderPlayerQueue.values()) {
			EntityPlayer player = e.getEntityPlayer();
            e.getRenderer().doRender((AbstractClientPlayer)player, e.getX(), e.getY(), e.getZ(), player.rotationYaw, e.getPartialRenderTick());
    	}
    	
    	// When all 'ghost' players are drawn, clear the queue
    	renderPlayerQueue.clear();
    }*/

    /**
     * On name render, simply render the name of the user, instead of the name of the entity.
     */
    @SubscribeEvent
    public void onNameRender(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
    	/*
        EntityLivingBase host = EntityMorph.renderEntity;

        if (host == null) {
            return;
        }
        event.setCanceled(true);

        EntityLivingBase target = event.getEntity();

        if (!this.canRenderName(host)) {
            return;
        }

        double dist = target.getDistanceSq(this.manager.renderViewEntity);
        float factor = target.isSneaking() ? 32.0F : 64.0F;

        if (dist < (double) (factor * factor)) {
            GlStateManager.alphaFunc(516, 0.1F);
            this.renderEntityName(target, host.getDisplayName().getFormattedText(), event.getX(), event.getY(), event.getZ());
        }
        
*/
    }

    /**
     * Can render the morph's name 
     */
    protected boolean canRenderName(EntityLivingBase host) {
        
    	
    	EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
        boolean flag = !host.isInvisibleToPlayer(entityplayersp);

        if (host != entityplayersp) {
            Team team = host.getTeam();
            Team team1 = entityplayersp.getTeam();

            if (team != null) {
                Team.EnumVisible team$enumvisible = team.getNameTagVisibility();

                switch (team$enumvisible) {
                    case ALWAYS:
                        return flag;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return team1 == null ? flag : team.isSameTeam(team1) && (team.getSeeFriendlyInvisiblesEnabled() || flag);
                    case HIDE_FOR_OWN_TEAM:
                        return team1 == null ? flag : !team.isSameTeam(team1) && flag;
                    default:
                        return true;
                }
            }
        }

        if (!(host instanceof EntityPlayer)) {
            flag = flag && host.hasCustomName();
        }

        return Minecraft.isGuiEnabled() && host != this.manager.renderViewEntity && flag && !host.isBeingRidden();
    }

    /**
     * Renders an entity's name above its head (copied and modified from 
     * {@link RenderLivingBase})
     */
    protected void renderEntityName(EntityLivingBase entity, String name, double x, double y, double z) {
        if (name.isEmpty()) {
            return;
        }

        boolean sneaking = entity.isSneaking();
        boolean thirdFrontal = this.manager.options.thirdPersonView == 2;

        float px = this.manager.playerViewY;
        float py = this.manager.playerViewX;
        float pz = entity.height + 0.5F - (sneaking ? 0.25F : 0.0F);

        int i = "deadmau5".equals(name) ? -10 : 0;

        EntityRenderer.drawNameplate(this.manager.getFontRenderer(), name, (float) x, (float) y + pz, (float) z, i, px, py, thirdFrontal, sneaking);
    }
}