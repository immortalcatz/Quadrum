package me.dmillerw.quadrum.feature.loader;

import com.google.common.collect.Maps;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.property.handler.block.BlockPropertyHandler;
import me.dmillerw.quadrum.helper.LogHelper;
import me.dmillerw.quadrum.lib.gson.GsonLib;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

/**
 * @author dmillerw
 */
@Mod.EventBusSubscriber
public class BlockLoader {

    private static Map<String, BlockData> dataMap = Maps.newHashMap();

    private static boolean initialized = false;

    public static Collection<BlockData> getBlockData() {
        return dataMap.values();
    }

    public static void initialize(File dir) {
        if (initialized) return;

        LogHelper.info("Initializing BlockLoader...");

        Path base = Paths.get(dir.toURI());
        Collection<File> files = FileUtils.listFiles(dir, new String[] { "json" }, true);
        for (File file : files) {
            String relative = "/blocks/" + base.relativize(Paths.get(file.toURI())).toString();

            BlockData data;

            LoaderState.setCurrentlyLoading(new LoaderState.State(relative, LoaderState.Type.BLOCK));

            LogHelper.info("Starting to load a Block from " + relative);

            try {
                data = GsonLib.gson().fromJson(new FileReader(file), BlockData.class);
            } catch (IOException | JsonIOException ex) {
                data = null;
                LogHelper.warn("Ran into an issue reading data from the file. It will be ignored: [" + ex.getMessage() + "]");
            } catch (JsonSyntaxException ex) {
                data = null;
                LogHelper.warn("Failed to load Block due to an issue with the JSON syntax");
                LogHelper.warn(" - " + ex.getMessage());
                LogHelper.warn(" - Loading Trait: " + LoaderState.getCurrentlyLoading().loadingTrait);
            } catch (JsonParseException ex) {
                data = null;
                LogHelper.warn("Failed to load Block due to an issue parsing the file");
                LogHelper.warn(" - " + ex.getMessage());
                LogHelper.warn(" - Loading Trait: " + LoaderState.getCurrentlyLoading().loadingTrait);
            }

            LoaderState.setCurrentlyLoading(null);

            if (data == null) continue;

            data.properties.propertyHandler.parent = data;

            dataMap.put(data.name, data);

            // Block/Item initialization

            // Dammit Java:
            // Here to allow for Blocks to still call upon their block impl even if they don't know their block impl yet
            // Like during super constructor calls (block state initialization, etc)
            BlockQuadrum.HACK = data;
            for (Block block : ((BlockPropertyHandler)data.properties.propertyHandler).loadBlocks(data)) data.blocks.add(block);
            for (ItemBlock itemBlock : ((BlockPropertyHandler)data.properties.propertyHandler).loadItemBlocks(data)) data.itemBlocks.add(itemBlock);

            BlockQuadrum.HACK = null;
        }

        LogHelper.info("Loaded " + dataMap.size() + " blocks into the game");

        initialized = true;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BlockLoader.initialize(Quadrum.blockDirectory);

        for (BlockData data : dataMap.values()) {
            data.blocks.forEach((b) -> event.getRegistry().register(b));
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (BlockData data : dataMap.values()) {
            data.itemBlocks.forEach((i) -> event.getRegistry().register(i));
        }
    }
}
