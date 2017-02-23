package me.dmillerw.quadrum.feature.trait.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.lib.gson.adapter.PostProcessableFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author dmillerw
 */
public abstract class Mergeable<S> implements PostProcessableFactory.PostProcessable {

    private static Map<Class, Map<String, Field>> CLASS_FIELD_MAP = Maps.newHashMap();

    //TODO Better logging and exception handling
    public static Mergeable merge(Class clazz, Mergeable defaultValues, Mergeable changedValues) {
        Mergeable mergedValues = null;
        try {
            mergedValues = (Mergeable) clazz.newInstance();
        } catch (Exception ignore) {
        }

        Map<String, Field> map = CLASS_FIELD_MAP.get(clazz);
        if (map == null) {
            map = Maps.newHashMap();

            for (Field field : clazz.getFields()) {
                String name = field.getName();

                SerializedName annotation = field.getAnnotation(SerializedName.class);
                if (annotation != null) name = annotation.value();

                map.put(name, field);
            }

            CLASS_FIELD_MAP.put(clazz, map);
        }

        for (Map.Entry<String, Field> entry : map.entrySet()) {
            try {
                Object defaultValue = entry.getValue().get(defaultValues);
                if (defaultValue == null) {
                    try {
                        defaultValue = entry.getValue().getType().newInstance();
                    } catch (Exception ignore) {}
                }

                if (changedValues.isSet(entry.getKey())) {
                    Object changedValue = entry.getValue().get(changedValues);

                    if (Mergeable.class.isAssignableFrom(entry.getValue().getType())) {
                        changedValue = Mergeable.merge(entry.getValue().getType(), (Mergeable) defaultValue, (Mergeable) changedValue);
                    }

                    entry.getValue().set(mergedValues, changedValue);
                } else {
                    entry.getValue().set(mergedValues, defaultValue);
                }
            } catch (Exception ignore) {
            }
        }

        mergedValues.mergeSpecials(mergedValues, defaultValues, changedValues);

        return mergedValues;
    }

    private final Set<String> set = Sets.newHashSet();

    public final boolean isSet(String key) {
        return set.contains(key);
    }

    @Override
    public void postProcess(JsonElement element) {
        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet())
            set.add(entry.getKey());
    }

    public void mergeSpecials(S newInstance, S defaultValues, S changedValues) {

    }
}
