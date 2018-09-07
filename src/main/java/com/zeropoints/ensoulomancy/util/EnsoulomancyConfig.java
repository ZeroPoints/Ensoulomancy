package com.zeropoints.ensoulomancy.util;

import com.zeropoints.ensoulomancy.Main;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Soulcraft config class
 * 
 * Intance of this class is responsible for storing configuration for Soulcraft mod.
 */
public class EnsoulomancyConfig {
    /* Config options */

    /**
     * Hide username in the survival morphing menu. Added just because, for 
     * no reason, if you're asking 
     */
    public boolean hide_username;

    /**
     * Show demorph as an option in survival morph menu 
     */
    public boolean show_demorph;

    /**
     * Disable modifying Point-of-View. Requested because of MorePlayerModels
     */
    public boolean disable_pov;

    /* End of config options */

    /**
     * Forge-provided configuration object class instance stuff...
     */
    private Configuration config;

    public EnsoulomancyConfig(Configuration config) {
        this.config = config;
        this.reload();
    }

    /**
     * Reload config values
     */
    public void reload() {
        String cat = Configuration.CATEGORY_GENERAL;
        String lang = "metamorph.config.";

        // TODO: Uncomment this once we get configs working, otherwise keep defaults
        /*
        this.prevent_ghosts = this.config.getBoolean("prevent_ghosts", cat, true, "Prevent ghosts from spawning if player has morph of mob already?", lang + "prevent_ghosts");
        this.keep_morphs = this.config.getBoolean("keep_morphs", cat, true, "Retain morphs when player dies?", lang + "keep_morphs");
        this.hide_username = this.config.getBoolean("hide_username", cat, false, "Hide username in survival morphing menu", lang + "hide_username");
        this.prevent_kill_acquire = this.config.getBoolean("prevent_kill_acquire", cat, false, "Prevent morph acquiring by killing a mob (or specifically prevent ghost spawning in any case)?", lang + "prevent_kill_acquire");
        this.show_demorph = this.config.getBoolean("show_demorph", cat, true, "Show demorph as an option in survival morph menu", lang + "show_demorph");
        this.disable_pov = this.config.getBoolean("disable_pov", cat, false, "Disable modifying Point-of-View. Requested to fix MorePlayerModels vertical jittering", lang + "disable_pov");
        this.disable_health = this.config.getBoolean("disable_health", cat, false, "Disable modifying health. Requested to fix dying all the time with Tough as Nails", lang + "disable_health");
        this.disable_morph_animation = this.config.getBoolean("disable_morph_animation", cat, false, "Disables morphing animation", lang + "disable_morph_animation");
        this.disable_morph_disguise = this.config.getBoolean("disable_morph_disguise", cat, false, "Disables the ability of morphs labeled as 'hostile' to avoid being attacked by hostile mobs.", lang + "disable_morph_disguise");
        this.acquire_immediately = this.config.getBoolean("acquire_immediately", cat, false, "Acquires morph immediately after player kills an entity instead of spawning a ghost", lang + "acquire_immediately");

        this.config.getCategory(cat).setComment("General configuration of Metamorph mod");

        if (this.config.hasChanged()) {
            this.config.save();
        }
        */
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID) && this.config.hasChanged()) {
            this.reload();
        }
    }
}