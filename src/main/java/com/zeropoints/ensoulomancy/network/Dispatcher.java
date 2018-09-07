package com.zeropoints.ensoulomancy.network;

import com.zeropoints.ensoulomancy.network.client.ClientHandlerGhost;
import com.zeropoints.ensoulomancy.network.client.ClientHandlerMorph;
import com.zeropoints.ensoulomancy.network.client.ClientHandlerMorphPlayer;
import com.zeropoints.ensoulomancy.network.client.ClientHandlerSettings;
import com.zeropoints.ensoulomancy.network.common.PacketAcquireMorph;
import com.zeropoints.ensoulomancy.network.common.PacketAction;
import com.zeropoints.ensoulomancy.network.common.PacketGhost;
import com.zeropoints.ensoulomancy.network.common.PacketMorph;
import com.zeropoints.ensoulomancy.network.common.PacketMorphPlayer;
import com.zeropoints.ensoulomancy.network.common.PacketSettings;
import com.zeropoints.ensoulomancy.network.server.ServerHandlerAction;
import com.zeropoints.ensoulomancy.network.server.ServerHandlerGhost;
import com.zeropoints.ensoulomancy.network.server.ServerHandlerMorph;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Network dispatcher
 *
 * @author Ernio (Ernest Sadowski)
 */
public class Dispatcher {
    private static final SimpleNetworkWrapper DISPATCHER = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
    private static byte PACKET_ID;

    public static SimpleNetworkWrapper get() {
        return DISPATCHER;
    }

    public static void updateTrackers(Entity entity, IMessage message) {
        EntityTracker et = ((WorldServer) entity.world).getEntityTracker();

        for (EntityPlayer player : et.getTrackingPlayers(entity)) {
            DISPATCHER.sendTo(message, (EntityPlayerMP) player);
        }
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        DISPATCHER.sendTo(message, player);
    }

    public static void sendToServer(IMessage message) {
        DISPATCHER.sendToServer(message);
    }

    /**
     * Register all the networking messages and message handlers
     */
    public static void register() {
        /* Action */
        register(PacketAction.class, ServerHandlerAction.class, Side.SERVER);

        /* Morphing */
        register(PacketMorph.class, ClientHandlerMorph.class, Side.CLIENT);
        register(PacketMorph.class, ServerHandlerMorph.class, Side.SERVER);
        register(PacketMorphPlayer.class, ClientHandlerMorphPlayer.class, Side.CLIENT);

        /* Syncing data */
        register(PacketSettings.class, ClientHandlerSettings.class, Side.CLIENT);
        
        /* Ghost */
        register(PacketGhost.class, ClientHandlerGhost.class, Side.CLIENT);
        register(PacketGhost.class, ServerHandlerGhost.class, Side.SERVER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void register(Class<REQ> message, Class<? extends IMessageHandler<REQ, REPLY>> handler, Side side) {
        DISPATCHER.registerMessage(handler, message, PACKET_ID++, side);
    }
}