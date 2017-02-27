package me.dmillerw.quadrum.feature.loader;

import com.google.common.collect.Maps;
import me.dmillerw.quadrum.Quadrum;
import me.dmillerw.quadrum.feature.trait.Traits;
import me.dmillerw.quadrum.helper.LogHelper;
import me.dmillerw.quadrum.item.IQuadrumItem;
import me.dmillerw.quadrum.item.ItemQuadrum;
import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.item.sub.ItemQuadrumConsumable;
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
    private static Map<String, IQuadrumItem> itemMap = Maps.newHashMap();

    private static boolean initialized = false;

    private static void initialize(File dir) {
        if (initialized) return;

        LogHelper.debug("Initializing ItemLoader");

        for (File file : dir.listFiles(ExtensionFilter.JSON)) {
            ItemData data;

            TraitState.setCurrentlyLoading(new TraitState.State(file.getName(), TraitState.Type.ITEM));

            LogHelper.debug("Loading Item from " + file.getName());

            try {
                data = GsonLib.gson().fromJson(new FileReader(file), ItemData.class);
            } catch (IOException ex) {
                data = null;

                LogHelper.warn("Failed to load Item from " + file.getName() + " - Reason: " + ex.getMessage());
            }

            TraitState.setCurrentlyLoading(null);

            if (data == null) continue;

            LogHelper.debug("Successfully loaded Item from " + file.getName());

            dataMap.put(data.name, data);
        }

        LogHelper.info("Loaded " + dataMap.size() + " items into the game!");

        initialized = true;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ItemLoader.initialize(Quadrum.itemDirectory);

        for (ItemData data : dataMap.values()) {
            IQuadrumItem item;

            if (data.traits.get(Traits.ITEM_CONSUMABLE) != null) {
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
