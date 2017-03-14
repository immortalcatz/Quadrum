package me.dmillerw.quadrum.network;

import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.network.packet.client.ClientOpenDumpGui;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID);
    static {
        INSTANCE.registerMessage(ClientOpenDumpGui.Handler.class, ClientOpenDumpGui.class, -1, Side.CLIENT);
    }
}
