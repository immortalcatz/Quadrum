package me.dmillerw.quadrum.feature.loader;

import com.google.common.collect.Maps;
import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.block.item.ItemBlockQuadrum;
import me.dmillerw.quadrum.lib.ExtensionFilter;
import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.lib.gson.GsonLib;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author dmillerw
 */
@Mod.EventBusSubscriber
public class BlockLoader {

    private static Map<String, BlockData> dataMap = Maps.newHashMap();
    private static Map<String, Block> blockMap = Maps.newHashMap();
    private static Map<String, ItemBlock> itemBlockMap = Maps.newHashMap();

    private static boolean initialized = false;

    public static Collection<Block> getBlocks() {
        return blockMap.values();
    }

    public static Collection<ItemBlock> getItemBlocks() {
        return itemBlockMap.values();
    }

    public static void initialize(File dir) {
        if (initialized) return;

        for (File file : dir.listFiles(ExtensionFilter.JSON)) {
            BlockData data;

            TraitState.setCurrentlyLoading(new TraitState.State(file.getName(), TraitState.Type.BLOCK));

            try {
                data = GsonLib.gson().fromJson(new FileReader(file), BlockData.class);
            } catch (IOException ex) {
                ex.printStackTrace();
                data = null;
            }

            TraitState.setCurrentlyLoading(null);

            if (data == null) continue;

            dataMap.put(data.name, data);
        }

        initialized = true;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BlockLoader.initialize(Quadrum.blockDirectory);

        for (BlockData data : dataMap.values()) {
            // Dammit Java:
            // Here to allow for Blocks to still call upon their block impl even if they don't know their block impl yet
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
            BlockData data = ((BlockQuadrum) block).getObject();

            ItemBlock item;
            if (data.variants.length > 0) {
                item = new ItemBlockQuadrum(block, true).setSubtypeNames(data.variants);
            } else {
                item = new ItemBlockQuadrum(block, false);
            }

            itemBlockMap.put(data.name, item);

            item.setRegistryName(ModInfo.MOD_ID, data.name);

            event.getRegistry().register(item);
        }
    }
}
