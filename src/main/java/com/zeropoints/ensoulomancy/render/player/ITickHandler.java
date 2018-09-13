
package com.zeropoints.ensoulomancy.render.player;

import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.EnumSet;

public interface ITickHandler {

    public void tick(TickEvent.Type type, Object... context);

    /**
     * WORLD, context: world
     * SERVER, context:
     * CLIENT, context:
     * RENDER, context: pTicks
     * PLAYER, context: player, side
     */
    public EnumSet<TickEvent.Type> getHandledTypes();

    public boolean canFire(TickEvent.Phase phase);

    public String getName();

}
