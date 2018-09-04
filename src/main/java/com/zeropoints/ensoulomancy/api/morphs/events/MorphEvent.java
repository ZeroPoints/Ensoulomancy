package com.zeropoints.ensoulomancy.api.morphs.events;

import com.zeropoints.ensoulomancy.api.morphs.AbstractMorph;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Morph event
 * 
 * This event occurs when player gets morphed or demorphed. If player gets 
 * demorphed then {@link #morph} is null. Check for player's worldObj property 
 * to get on which side this event is triggered.
 * 
 * This event is cancelable. If it's get canceled, then player won't demorph 
 * or morph. If you'll reassign {@link #morph}, then player will apply morph 
 * which you assigned (or demorph if you assign {@link #morph} to null).
 * 
 * You can also modify {@link #force} parameter which is responsible for 
 * forcing morphing (if not forced, player will morph only in case if he has 
 * acquired morph like given).
 */
@Cancelable
public class MorphEvent extends Event {
	
	public MorphEvent Pre;
	public MorphEvent Post;
	
    public static class Pre extends MorphEvent {
		public Pre(EntityPlayer player, AbstractMorph morph) {
			super(player, morph);
		}
	}
    
    public static class Post extends MorphEvent {
		public Post(EntityPlayer player, AbstractMorph morph) {
			super(player, morph);
		}
	}

	public EntityPlayer player;
    public AbstractMorph morph;

    public MorphEvent(EntityPlayer player, AbstractMorph morph) {
        this.player = player;
        this.morph = morph;
    }

}