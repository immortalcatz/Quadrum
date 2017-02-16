package me.dmillerw.quadrum.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */

public class CommandQuadrumDump implements ICommand {

    private static interface DataDumper {

        public List<String> getData();
    }

    private static final Map<String, DataDumper> DATA_DUMPERS = Maps.newHashMap();

    static {
        DATA_DUMPERS.put("particle", () -> {
            List<String> list = Lists.newArrayList();

            for (EnumParticleTypes p : EnumParticleTypes.values())
                list.add(p.name());

            return list;
        });
    }

    @Override
    public String getCommandName() {
        return "quadrum_dump";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/quadrum-dump [tag]";
    }

    @Override
    public List<String> getCommandAliases() {
        return Lists.newArrayList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 0)
            throw new WrongUsageException(getCommandUsage(sender));

        DataDumper dumper = DATA_DUMPERS.get(args[0]);
        if (dumper == null)
            return;

        StringBuilder builder = new StringBuilder();
        for (String s : dumper.getData())
            builder.append(s).append(", ");

        sender.addChatMessage(new TextComponentString(builder.toString()));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
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
