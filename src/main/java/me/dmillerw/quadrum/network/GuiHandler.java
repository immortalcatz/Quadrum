package me.dmillerw.quadrum.network;

import me.dmillerw.quadrum.client.gui.GuiViewData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * @author dmillerw
 */
public class GuiHandler implements IGuiHandler {

    public static String dataKey;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GuiViewData(dataKey);
    }
}
