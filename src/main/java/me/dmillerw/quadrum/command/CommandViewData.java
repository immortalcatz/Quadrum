package me.dmillerw.quadrum.command;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.network.PacketHandler;
import me.dmillerw.quadrum.network.packet.client.ClientOpenDumpGui;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author dmillerw
 */
public class CommandViewData implements ICommand {

    @Override
    public String getName() {
        return "quadrum-dump";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/quadrum-dump [tag]";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String tag = args.length >= 1 ? args[0] : "";
        if (sender instanceof EntityPlayer)
            PacketHandler.INSTANCE.sendTo(new ClientOpenDumpGui(tag), (EntityPlayerMP) sender);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
