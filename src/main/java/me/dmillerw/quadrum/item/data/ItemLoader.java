package me.dmillerw.quadrum.block.data;

import com.google.common.collect.Maps;
import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.item.ItemQuadrum;
import me.dmillerw.quadrum.item.data.ItemData;
import me.dmillerw.quadrum.lib.ExtensionFilter;
import me.dmillerw.quadrum.lib.ModInfo;
import me.dmillerw.quadrum.lib.gson.GsonLib;
import net.minecraft.item.Item;
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
public class ItemLoader {

    private static Map<String, ItemData> dataMap = Maps.newHashMap();
    private static Map<String, Item> itemMap = Maps.newHashMap();

    private static boolean initialized = false;

    private static void initialize(File dir) {
        if (initialized) return;

        for (File file : dir.listFiles(ExtensionFilter.JSON)) {
            ItemData data;
            try {
                data = GsonLib.gson().fromJson(new FileReader(file), ItemData.class);
            } catch (IOException ex) {
                ex.printStackTrace();
                data = null;
            }

            if (data == null) continue;

            // Trait merging
            data.lore.merge();

            dataMap.put(data.name, data);
        }

        initialized = true;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ItemLoader.initialize(Quadrum.itemDirectory);

        for (ItemData data : dataMap.values()) {

            ItemQuadrum item = new ItemQuadrum(data);
            item.setUnlocalizedName(ModInfo.MOD_ID + ":" + data.name);
            item.setRegistryName(ModInfo.MOD_ID, data.name);

            itemMap.put(data.name, item);

            event.getRegistry().register(item);
        }
    }

    public static Collection<Item> getItems() {
        return itemMap.values();
    }
}
