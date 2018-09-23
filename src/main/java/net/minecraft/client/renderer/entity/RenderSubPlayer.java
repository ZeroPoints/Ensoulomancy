package net.minecraft.client.renderer.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.zeropoints.ensoulomancy.capabilities.ghost.Ghost;
import com.zeropoints.ensoulomancy.capabilities.ghost.IGhost;
//import com.zeropoints.ensoulomancy.capabilities.morphing.IMorphing;
//import com.zeropoints.ensoulomancy.capabilities.morphing.Morphing;
import com.zeropoints.ensoulomancy.render.layer.LayerGhostBipedArmor;
import com.zeropoints.ensoulomancy.render.layer.LayerGhostHeldItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * Render sub player renderer
 *
 * This class is responsible for substituting native player renderer classes
 * in the skinMap to achieve the rendering of the custom morphed hands.
 *
 * I hope nobody will want to substitute the same map as I did :D
 */
public class RenderSubPlayer extends RenderPlayer {
	
    public RenderPlayer original;
    
    
    /**
     * Initiate with render manager, player renderer and smallArms flag.
     */
    public RenderSubPlayer(RenderManager renderManager, RenderPlayer original, boolean useSmallArms) {
        super(renderManager, useSmallArms);
        
        original.layerRenderers.remove(0); // Remove Armor Layer Renderer
        original.addLayer(new LayerGhostBipedArmor(this)); // Add Armor Layer Renderer
        
        this.original = original;
    }

    /**
     * Render default or morphed left hand.
     */
    @Override
    public void renderLeftArm(AbstractClientPlayer clientPlayer) {
        /*
    	IMorphing morph = Morphing.getCapability(clientPlayer);

        if (morph != null && morph.isMorphed()) {
            if (morph.getCurrentMorph().renderHand(clientPlayer, EnumHand.OFF_HAND)) {
                return;
            }
        }
        */
        this.original.renderLeftArm(clientPlayer);
    }

    /**
     * Render default or morphed right hand.
     */
    @Override
    public void renderRightArm(AbstractClientPlayer clientPlayer) {
        /*
    	IMorphing morph = Morphing.getCapability(clientPlayer);

        if (morph != null && morph.isMorphed()) {
            if (morph.getCurrentMorph().renderHand(clientPlayer, EnumHand.MAIN_HAND)) {
                return;
            }
        }
        */
    	
    	IGhost ghost = Ghost.getCapability(clientPlayer);
        
        if (ghost != null && ghost.isGhost()) {
        	if (renderGhostRightArm(clientPlayer)) {
        		return;
        	}
        }

        this.original.renderRightArm(clientPlayer);
    }
    
    /**
     * This method renders the hand 'ghostly' for the player. 
     * We can skip unnecessary checks minecraft does normally and just render when the capability is active
     */
    private boolean renderGhostRightArm(AbstractClientPlayer clientPlayer) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.35F);
        ModelPlayer modelplayer = this.getMainModel(); // TODO: Should override with own ghost model?
        GlStateManager.enableBlend();
        modelplayer.swingProgress = 0.0F;
        modelplayer.isSneak = false;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
        modelplayer.bipedRightArm.render(0.0625F);
        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedRightArmwear.render(0.0625F);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    /* Overriding RenderPlayer methods */

    @Override
    public ModelPlayer getMainModel() {
        return this.original == null ? super.getMainModel() : this.original.getMainModel();
    }

    @Override
    public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.original.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
	public ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
        return this.original.getEntityTexture(entity);
    }

    @Override
    public void transformHeldFull3DItemLayer() {
        this.original.transformHeldFull3DItemLayer();
    }

    @Override
    protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
        this.original.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    @Override
    protected void renderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
        this.original.renderEntityName(entityIn, x, y, z, name, distanceSq);
    }

    @Override
    protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
        this.original.renderLivingAt(entityLivingBaseIn, x, y, z);
    }

    /*
    @Override
    protected void rotateCorpse(AbstractClientPlayer entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
        this.original.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
    }
    */
}