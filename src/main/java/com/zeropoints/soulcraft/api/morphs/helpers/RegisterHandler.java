package com.zeropoints.soulcraft.api.morphs.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.zeropoints.soulcraft.Main;
import com.zeropoints.soulcraft.api.morphs.MorphManager;
import com.zeropoints.soulcraft.api.morphs.MorphSettings;
import com.zeropoints.soulcraft.api.morphs.MorphSettingsAdapter;
import com.zeropoints.soulcraft.api.morphs.abilities.IAbility;
import com.zeropoints.soulcraft.api.morphs.abilities.IAction;
import com.zeropoints.soulcraft.api.morphs.abilities.IAttackAbility;
import com.zeropoints.soulcraft.api.morphs.events.RegisterSettingsEvent;
//import com.zeropoints.soulcraft.api.morphs.events.RegisterBlacklistEvent;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.Climb;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.FireProof;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.Fly;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.Glide;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.Hungerless;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.Jumping;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.NightVision;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.PreventFall;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.SnowWalk;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.SunAllergy;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.Swim;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.WaterAllergy;
import com.zeropoints.soulcraft.api.morphs.helpers.abilities.WaterBreath;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Endermite;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Explode;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.FireBreath;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Fireball;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Jump;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Potions;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.ShulkerBullet;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Sliverfish;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.SmallFireball;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Snowball;
import com.zeropoints.soulcraft.api.morphs.helpers.actions.Teleport;
import com.zeropoints.soulcraft.api.morphs.helpers.attacks.KnockbackAttack;
import com.zeropoints.soulcraft.api.morphs.helpers.attacks.PoisonAttack;
import com.zeropoints.soulcraft.api.morphs.helpers.attacks.WitherAttack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Register handler 
 * 
 * This event handler is responsible for 
 */
public class RegisterHandler {
    /**
     * GSON instance that is responsible for deserializing morph settings
     */
    private final static Gson GSON = new GsonBuilder().registerTypeAdapter(MorphSettings.class, new MorphSettingsAdapter()).create();

    /**
     * Register Metamorph's supplied abilities, actions and attacks. 
     */
    public static void registerAbilities(MorphManager manager) {
        /* Define shortcuts */
        Map<String, IAbility> abilities = manager.abilities;
        Map<String, IAttackAbility> attacks = manager.attacks;
        Map<String, IAction> actions = manager.actions;

        /* Register default abilities */
        abilities.put("climb", new Climb());
        abilities.put("fire_proof", new FireProof());
        abilities.put("fly", new Fly());
        abilities.put("glide", new Glide());
        abilities.put("hungerless", new Hungerless());
        abilities.put("jumping", new Jumping());
        abilities.put("night_vision", new NightVision());
        abilities.put("prevent_fall", new PreventFall());
        abilities.put("snow_walk", new SnowWalk());
        abilities.put("sun_allergy", new SunAllergy());
        abilities.put("swim", new Swim());
        abilities.put("water_allergy", new WaterAllergy());
        abilities.put("water_breath", new WaterBreath());

        /* Register default actions */
        actions.put("endermite", new Endermite());
        actions.put("explode", new Explode());
        actions.put("fireball", new Fireball());
        actions.put("fire_breath", new FireBreath());
        actions.put("jump", new Jump());
        actions.put("potions", new Potions());
        actions.put("shulker_bullet", new ShulkerBullet());
        actions.put("silverfish", new Sliverfish());
        actions.put("small_fireball", new SmallFireball());
        actions.put("snowball", new Snowball());
        actions.put("teleport", new Teleport());

        /* Register default attacks */
        attacks.put("poison", new PoisonAttack());
        attacks.put("wither", new WitherAttack());
        attacks.put("knockback", new KnockbackAttack());
    }

    /**
     * Register morph settings from default morphs configuration that 
     * comes with Metamorph and user configuration file 
     */
    
    @SubscribeEvent
    public void onSettingsReload(RegisterSettingsEvent event) {
        this.loadMorphSettings(event.settings, this.getClass().getClassLoader().getResourceAsStream("assets/sc/morphs.json"));
        this.loadMorphSettings(event.settings, Main.proxy.morphs);
    }

    /**
     * Load morph settings into {@link MorphManager} with given {@link File} and 
     * with a try-catch which logs out an error in case of failure.
     */
    private void loadMorphSettings(Map<String, MorphSettings> settings, File config) {
        try {
            this.loadMorphSettings(settings, new FileInputStream(config));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load morph settings from {@link InputStream}
     */
    private void loadMorphSettings(Map<String, MorphSettings> settings, InputStream input) {
        Scanner scanner = new Scanner(input, "UTF-8");

        Type type = new TypeToken<Map<String, MorphSettings>>() {}.getType();

        Map<String, MorphSettings> data = GSON.fromJson(scanner.useDelimiter("\\A").next(), type);

        scanner.close();

        for (Map.Entry<String, MorphSettings> entry : data.entrySet()) {
            String key = entry.getKey();
            MorphSettings morphSettings = entry.getValue();

            if (settings.containsKey(key))            {
                settings.get(key).merge(morphSettings);
            }
            else {
                settings.put(key, morphSettings);
            }
        }
    }
}