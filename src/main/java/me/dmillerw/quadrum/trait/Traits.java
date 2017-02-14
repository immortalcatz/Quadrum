package me.dmillerw.quadrum.trait;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import me.dmillerw.quadrum.trait.data.block.BoundingBox;
import me.dmillerw.quadrum.trait.data.block.Physical;
import me.dmillerw.quadrum.trait.data.item.Consumable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public class Traits {

    public static final TypeToken<List<String>> TYPE_LIST = new TypeToken<List<String>>() {};

    public static final String LORE = "lore";
    public static final String ORE_DICTIONARY = "ore_dictionary";

    public static final String ITEM_CONSUMABLE = "consumable";

    public static final String BLOCK_BOUNDING_BOX = "bounding_box";
    public static final String BLOCK_PHYSICAL = "physical";

    private static final Map<String, TypeToken<?>> TYPES = Maps.newHashMap();
    static {
        TYPES.put(Traits.LORE, new TypeToken<List<String>>() {});
        TYPES.put(Traits.ORE_DICTIONARY, new TypeToken<List<String>>() {});

        TYPES.put(Traits.ITEM_CONSUMABLE, new TypeToken<Consumable>() {});

        TYPES.put(Traits.BLOCK_BOUNDING_BOX, new TypeToken<BoundingBox>() {});
        TYPES.put(Traits.BLOCK_PHYSICAL, new TypeToken<Physical>() {});
    }

    protected Map<String, QuadrumTrait<?>> backingMap = Maps.newHashMap();

    protected final void merge() {
        backingMap.values().forEach(QuadrumTrait::merge);
    }

    public <T> QuadrumTrait<T> get(String key) {
        return (QuadrumTrait<T>) backingMap.get(key);
    }

    public <T> QuadrumTrait<T> get(String key, Class<T> clazz) {
        return (QuadrumTrait<T>) backingMap.get(key);
    }

    public <T> QuadrumTrait<T> get(String key, TypeToken<T> clazz) {
        return (QuadrumTrait<T>) backingMap.get(key);
    }

    public static class Deserializer implements JsonDeserializer<Traits> {

        @Override
        public Traits deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonObject())
                throw new JsonParseException("Tried to parse traits from something other than a JsonObject!");

            JsonObject object = json.getAsJsonObject();
            Traits container = new Traits();

            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                TypeToken<?> type = TYPES.get(entry.getKey());
                if (type == null) {
                    throw new JsonParseException("Tried to load a trait of type `" + entry.getKey() + "`, which doesn't exist");
                }

                QuadrumTrait trait = new QuadrumTrait();

                JsonElement element = entry.getValue();
                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();

                    if (obj.has("default") || obj.has("variants")) {
                        if (obj.has("default"))
                            trait.defaultValue = context.deserialize(obj.get("default"), type.getType());

                        if (obj.has("variants")) {
                            Map<String, Object> variants = Maps.newHashMap();

                            for (Map.Entry<String, JsonElement> variant : obj.get("variants").getAsJsonObject().entrySet()) {
                                variants.put(variant.getKey(), context.deserialize(variant.getValue(), type.getType()));
                            }

                            trait.variants = variants;
                        }
                    } else {
                        trait.defaultValue = context.deserialize(element, type.getType());
                    }
                } else {
                    trait.defaultValue = context.deserialize(element, type.getType());
                }

                if (trait.defaultValue != null || (trait.variants != null && !trait.variants.isEmpty()))
                    container.backingMap.put(entry.getKey(), trait);
            }

            container.merge();
            return container;
        }
    }
}
