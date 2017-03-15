package me.dmillerw.quadrum.feature.loader;

import com.google.common.collect.Maps;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.trait.Traits;
import me.dmillerw.quadrum.helper.LogHelper;
import me.dmillerw.quadrum.item.IQuadrumItem;
import me.dmillerw.quadrum.item.ItemQuadrum;
import me.dmillerw.quadrum.item.sub.ItemQuadrumConsumable;
import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.lib.gson.GsonLib;
import net.minecraft.item.Item;
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
public class ItemLoader {

    private static Map<String, ItemData> dataMap = Maps.newHashMap();
    private static Map<String, IQuadrumItem> itemMap = Maps.newHashMap();

    private static boolean initialized = false;

    private static void initialize(File dir) {
        if (initialized) return;

        LogHelper.debug("Initializing ItemLoader...");

        Path base = Paths.get(dir.toURI());
        Collection<File> files = FileUtils.listFiles(dir, new String[] { "json" }, true);
        for (File file : files) {
            String relative = "/items/" + base.relativize(Paths.get(file.toURI())).toString();

            ItemData data;

            LoaderState.setCurrentlyLoading(new LoaderState.State(relative, LoaderState.Type.ITEM));

            LogHelper.info("Starting to load a Item from " + relative);

            try {
                data = GsonLib.gson().fromJson(new FileReader(file), ItemData.class);
            } catch (IOException | JsonIOException ex) {
                data = null;
                LogHelper.warn("Ran into an issue reading data from the file. It will be ignored: [" + ex.getMessage() + "]");
            } catch (JsonSyntaxException ex) {
                data = null;
                LogHelper.warn("Failed to load Item due to an issue with the JSON syntax");
                LogHelper.warn(" - " + ex.getMessage());
            } catch (JsonParseException ex) {
                data = null;
                LogHelper.warn("Failed to load Item due to an issue parsing the file");
                LogHelper.warn(" - " + ex.getMessage());
            }

            LoaderState.setCurrentlyLoading(null);

            if (data == null) continue;

            data.properties.propertyHandler.parent = data;

            dataMap.put(data.name, data);
        }

        LogHelper.info("Loaded " + dataMap.size() + " items into the game");

        initialized = true;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ItemLoader.initialize(Quadrum.itemDirectory);

        for (ItemData data : dataMap.values()) {
            IQuadrumItem item;

            if (data.getTrait(Traits.ITEM_CONSUMABLE) != null) {
                item = new ItemQuadrumConsumable(data);
            } else {
                item = new ItemQuadrum(data);
            }

            ((Item)item).setUnlocalizedName(ModInfo.MOD_ID + ":" + data.name);
            ((Item)item).setRegistryName(ModInfo.MOD_ID, data.name);

            itemMap.put(data.name, item);

            event.getRegistry().register((Item)item);
        }
    }

    public static Collection<IQuadrumItem> getItems() {
        return itemMap.values();
    }
}
