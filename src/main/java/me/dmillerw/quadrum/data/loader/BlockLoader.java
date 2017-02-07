package me.dmillerw.quadrum.data.loader;

import com.google.common.collect.Maps;
import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.data.BlockData;
import me.dmillerw.quadrum.lib.ExtensionFilter;
import me.dmillerw.quadrum.lib.GsonLib;
import me.dmillerw.quadrum.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author dmillerw
 */
@Mod.EventBusSubscriber
public class BlockLoader {

    private static Map<String, BlockData> dataMap = Maps.newHashMap();
    private static Map<String, Block> blockMap = Maps.newHashMap();

    private static boolean initialized = false;

    public static void intialize(File dir) {
        if (initialized) return;

        for (File file : dir.listFiles(ExtensionFilter.JSON)) {
            BlockData data;
            try {
                data = GsonLib.gson().fromJson(new FileReader(file), BlockData.class);
            } catch (IOException ex) {
                ex.printStackTrace();
                data = null;
            }

            if (data == null) continue;

            dataMap.put(data.name, data);
        }

        initialized = true;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BlockLoader.intialize(new File(Quadrum.configurationDirectory, "blocks"));

        for (BlockData data : dataMap.values()) {
            // Dammit Java:
            // Here to allow for Blocks to still call upon their block data even if they don't know their block data yet
            // Like during super constructor calls (block state initialization, etc)
            BlockQuadrum.HACK = data;
            BlockQuadrum block = new BlockQuadrum(data);
            block.setUnlocalizedName(ModInfo.MOD_ID + ":" + data.name);
            block.setRegistryName(ModInfo.MOD_ID, data.name);

            blockMap.put(data.name, block);

            event.getRegistry().register(block);
        }

        BlockQuadrum.HACK = null;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Block block : blockMap.values()) {
            ItemBlock item = new ItemBlock(block);
            item.setRegistryName(block.getRegistryName());

            event.getRegistry().register(item);
        }
    }
}
