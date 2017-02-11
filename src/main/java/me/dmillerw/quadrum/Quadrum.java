package me.dmillerw.quadrum;

import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.proxy.IProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

/**
 * @author dmillerw
 */
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, acceptedMinecraftVersions = "[1.10,1.10.2]", version = ModInfo.MOD_VERSION)
public class Quadrum {

    @Mod.Instance(ModInfo.MOD_ID)
    public static Quadrum INSTANCE;

    @SidedProxy(serverSide = "me.dmillerw.quadrum.proxy.CommonProxy", clientSide = "me.dmillerw.quadrum.proxy.ClientProxy")
    public static IProxy PROXY;

    public static File configurationDirectory;
    public static File blockDirectory;
    public static File itemDirectory;

    static {
        configurationDirectory = new File(Loader.instance().getConfigDir(), ModInfo.MOD_ID);
        blockDirectory = new File(configurationDirectory, "blocks");
        if (!blockDirectory.exists()) blockDirectory.mkdirs();
        itemDirectory = new File(configurationDirectory, "items");
        if (!itemDirectory.exists()) itemDirectory.mkdirs();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PROXY.preInit(event);
    }
}
