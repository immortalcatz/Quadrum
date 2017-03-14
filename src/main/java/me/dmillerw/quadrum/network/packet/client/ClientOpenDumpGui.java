package me.dmillerw.quadrum.network.packet.client;

import io.netty.buffer.ByteBuf;
import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.network.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.Callable;

/**
 * @author dmillerw
 */
public class ClientOpenDumpGui implements IMessage {

    private String key;

    public ClientOpenDumpGui() {}
    public ClientOpenDumpGui(String key) { this.key = key; }

    @Override
    public void toBytes(ByteBuf buf) {
        boolean mainMenu = (key == null || key.isEmpty());
        buf.writeBoolean(!mainMenu);
        if (!mainMenu) ByteBufUtils.writeUTF8String(buf, key);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (buf.readBoolean()) key = ByteBufUtils.readUTF8String(buf);
    }

    public static class Handler implements IMessageHandler<ClientOpenDumpGui, IMessage> {

        @Override
        public IMessage onMessage(ClientOpenDumpGui message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    GuiHandler.dataKey = message.key;
                    Minecraft.getMinecraft().player.openGui(Quadrum.INSTANCE, 0, null, 0, 0, 0);
                    return null;
                }
            });
            return null;
        }
    }
}
