package com.zeropoints.ensoulomancy.api.morphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zeropoints.ensoulomancy.api.DefaultSettings;
import com.zeropoints.ensoulomancy.api.morphs.abilities.IAbility;
import com.zeropoints.ensoulomancy.api.morphs.abilities.IAction;
import com.zeropoints.ensoulomancy.api.morphs.abilities.IAttackAbility;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Morph settings
 * 
 * An instance of this class is responsible for storing information about 
 * morph's configurable settings.
 */
public class MorphSettings extends DefaultSettings {
	
    public static final MorphSettings DEFAULT = new MorphSettings();

    /**
     * Abilities that are going to be applied on a morph 
     */
    public IAbility[] abilities = new IAbility[] {};

    /**
     * Attack that is going to be used on a morph
     */
    public IAttackAbility attack;

    /**
     * Action that is going to be used on a morph
     */
    public IAction action;

    /**
     * Health units which are going to be applied
     */
    public int health = 20;

    /**
     * Speed which is going to be applied 
     */
    public float speed = 0.1F;

    /**
     * Hostile flag which is going to be applied
     */
    public boolean hostile;

    /**
     * Does client tries render hands for this morph 
     */
    public boolean hands;

    /**
     * Merge given morph settings with this settings 
     */
    public void merge(MorphSettings setting) {
        if (setting.abilities.length != 0) {
            List<IAbility> abilities = new ArrayList<IAbility>();

            for (IAbility ability : setting.abilities) {
                abilities.add(ability);
            }

            this.abilities = abilities.toArray(new IAbility[abilities.size()]);
        }

        this.action = setting.action;
        this.attack = setting.attack;

        this.health = setting.health;
        this.speed = setting.speed;
        this.hostile = setting.hostile;
        this.hands = setting.hands;
    }

    @Override
    public MorphSettings clone() {
        MorphSettings settings = new MorphSettings();

        settings.merge(this);

        return settings;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.abilities.length);

        for (IAbility ability : this.abilities) {
            String string = getKey(MorphManager.INSTANCE.abilities, ability);

            ByteBufUtils.writeUTF8String(buf, string == null ? "" : string);
        }

        String action = getKey(MorphManager.INSTANCE.actions, this.action);
        String attack = getKey(MorphManager.INSTANCE.attacks, this.attack);

        buf.writeBoolean(action != null);

        if (action != null) {
            ByteBufUtils.writeUTF8String(buf, action);
        }

        buf.writeBoolean(attack != null);

        if (attack != null)        {
            ByteBufUtils.writeUTF8String(buf, attack);
        }

        buf.writeInt(this.health);
        buf.writeFloat(this.speed);
        buf.writeBoolean(this.hostile);
        buf.writeBoolean(this.hands);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        List<IAbility> abilities = new ArrayList<IAbility>();

        for (int i = 0, c = buf.readInt(); i < c; i++) {
            IAbility ability = MorphManager.INSTANCE.abilities.get(ByteBufUtils.readUTF8String(buf));

            if (ability != null) {
                abilities.add(ability);
            }
        }

        this.abilities = abilities.toArray(new IAbility[abilities.size()]);

        if (buf.readBoolean()) {
            String action = ByteBufUtils.readUTF8String(buf);

            this.action = MorphManager.INSTANCE.actions.get(action);
        }

        if (buf.readBoolean()) {
            String attack = ByteBufUtils.readUTF8String(buf);

            this.attack = MorphManager.INSTANCE.attacks.get(attack);
        }

        this.health = buf.readInt();
        this.speed = buf.readFloat();
        this.hostile = buf.readBoolean();
        this.hands = buf.readBoolean();
    }
    
}