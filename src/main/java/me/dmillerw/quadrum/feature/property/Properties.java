package me.dmillerw.quadrum.feature.property;

import com.google.common.reflect.TypeToken;
import me.dmillerw.quadrum.feature.DataType;
import me.dmillerw.quadrum.feature.property.data.Consumable;
import me.dmillerw.quadrum.feature.property.data.Variants;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.feature.property.handler.block.BlockVariantsHandler;
import me.dmillerw.quadrum.feature.property.handler.item.ConsumableHandler;
import me.dmillerw.quadrum.feature.property.handler.item.ItemVariantsHandler;

/**
 * @author dmillerw
 */
public enum Properties {

    BLOCK_VARIANTS(DataType.BLOCK, "variants", new TypeToken<Variants>() {}, BlockVariantsHandler::new),

    ITEM_CONSUMABLE(DataType.ITEM, "consumable", new TypeToken<Consumable>() {}, ConsumableHandler::new),
    ITEM_VARIANTS(DataType.ITEM, "variants", new TypeToken<Variants>() {}, ItemVariantsHandler::new);

    private DataType type;
    private String key;
    public TypeToken<?> dataType;
    private Constructor constructor;

    private Properties(DataType type, String key, TypeToken<?> dataType, Constructor constructor) {
        this.type = type;
        this.key = key;
        this.dataType = dataType;
        this.constructor = constructor;
    }

    public PropertyHandler getPropertyHandler() {
        return constructor.create();
    }

    public static Properties get(DataType type, String key) {
        for (Properties prop : Properties.values()) {
            if (prop.type == type && prop.key.equalsIgnoreCase(key))
                return prop;
        }

        return null;
    }

    public static interface Constructor {

        public PropertyHandler create();
    }
}
