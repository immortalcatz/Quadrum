package me.dmillerw.quadrum.feature.property;

import com.google.common.reflect.TypeToken;
import me.dmillerw.quadrum.feature.DataType;
import me.dmillerw.quadrum.feature.property.data.Variants;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.feature.property.handler.VariantsHandler;

/**
 * @author dmillerw
 */
public enum Properties {

    VARIANTS(DataType.COMMON, "variants", new TypeToken<Variants>() {}, VariantsHandler::new);

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
