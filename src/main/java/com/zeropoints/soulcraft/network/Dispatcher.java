package com.zeropoints.soulcraft.network;

import com.zeropoints.soulcraft.network.client.ClientHandlerMorph;
import com.zeropoints.soulcraft.network.client.ClientHandlerMorphPlayer;
import com.zeropoints.soulcraft.network.client.ClientHandlerSettings;
import com.zeropoints.soulcraft.network.common.PacketAcquireMorph;
import com.zeropoints.soulcraft.network.common.PacketMorph;
import com.zeropoints.soulcraft.network.common.PacketMorphPlayer;
import com.zeropoints.soulcraft.network.common.PacketSettings;
import com.zeropoints.soulcraft.network.server.ServerHandlerMorph;
//import com.zeropoints.soulcraft.network.client.ClientHandlerAcquireMorph;
//import com.zeropoints.soulcraft.network.client.ClientHandlerAcquiredMorphs;
//import com.zeropoints.soulcraft.network.client.ClientHandlerFavoriteMorph;
//import com.zeropoints.soulcraft.network.common.PacketRemoveMorph;
//import com.zeropoints.soulcraft.network.common.PacketSelectMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerAcquireMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerAction;
//import com.zeropoints.soulcraft.network.server.ServerHandlerFavoriteMorph;
//import com.zeropoints.soulcraft.network.common.PacketRemoveMorph;
//import com.zeropoints.soulcraft.network.common.PacketSelectMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerAcquireMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerAction;
//import com.zeropoints.soulcraft.network.server.ServerHandlerFavoriteMorph;
//import com.zeropoints.soulcraft.network.common.PacketRemoveMorph;
//import com.zeropoints.soulcraft.network.common.PacketSelectMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerAcquireMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerAction;
//import com.zeropoints.soulcraft.network.server.ServerHandlerFavoriteMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerRemoveMorph;
//import com.zeropoints.soulcraft.network.server.ServerHandlerSelectMorph;
import com.zeropoints.soulcraft.util.Reference;

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
        //register(PacketAction.class, ServerHandlerAction.class, Side.SERVER);

        /* Morphing */
        register(PacketMorph.class, ClientHandlerMorph.class, Side.CLIENT);
        register(PacketMorph.class, ServerHandlerMorph.class, Side.SERVER);
        register(PacketMorphPlayer.class, ClientHandlerMorphPlayer.class, Side.CLIENT);

        //register(PacketAcquireMorph.class, ClientHandlerAcquireMorph.class, Side.CLIENT);
        //register(PacketAcquireMorph.class, ServerHandlerAcquireMorph.class, Side.SERVER);
        //register(PacketAcquiredMorphs.class, ClientHandlerAcquiredMorphs.class, Side.CLIENT);

        /* Managing morphs */
        //register(PacketRemoveMorph.class, ClientHandlerRemoveMorph.class, Side.CLIENT);
        //register(PacketRemoveMorph.class, ServerHandlerRemoveMorph.class, Side.SERVER);
        
        /* Syncing data */
        register(PacketSettings.class, ClientHandlerSettings.class, Side.CLIENT);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void register(Class<REQ> message, Class<? extends IMessageHandler<REQ, REPLY>> handler, Side side) {
        DISPATCHER.registerMessage(handler, message, PACKET_ID++, side);
    }
}