package me.dmillerw.quadrum.feature.trait;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import me.dmillerw.quadrum.feature.data.loader.TraitLoader;
import me.dmillerw.quadrum.feature.trait.data.block.*;
import me.dmillerw.quadrum.feature.trait.data.item.Consumable;
import me.dmillerw.quadrum.feature.trait.data.item.ItemVisual;

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

    public static final String BLOCK_BOUNDING_BOX = "bounding_box";
    public static final String BLOCK_PHYSICAL = "physical";
    public static final String BLOCK_PARTICLE = "particle";
    public static final String BLOCK_REDSTONE = "redstone";
    public static final String BLOCK_VISUAL = "visual";

    public static final String ITEM_CONSUMABLE = "consumable";
    public static final String ITEM_VISUAL = "visual";

    private static final Map<String, TypeToken<?>> COMMON_TYPES = Maps.newHashMap();
    private static final Map<String, TypeToken<?>> BLOCK_TYPES = Maps.newHashMap();
    private static final Map<String, TypeToken<?>> ITEM_TYPES = Maps.newHashMap();
    static {
        COMMON_TYPES.put(Traits.LORE, new TypeToken<List<String>>() {});
        COMMON_TYPES.put(Traits.ORE_DICTIONARY, new TypeToken<List<String>>() {});

        BLOCK_TYPES.put(Traits.BLOCK_BOUNDING_BOX, new TypeToken<BoundingBox>() {});
        BLOCK_TYPES.put(Traits.BLOCK_PHYSICAL, new TypeToken<Physical>() {});
        BLOCK_TYPES.put(Traits.BLOCK_PARTICLE, new TypeToken<Particle[]>() {});
        BLOCK_TYPES.put(Traits.BLOCK_REDSTONE, new TypeToken<Redstone>() {});
        BLOCK_TYPES.put(Traits.BLOCK_VISUAL, new TypeToken<BlockVisual>() {});

        ITEM_TYPES.put(Traits.ITEM_CONSUMABLE, new TypeToken<Consumable>() {});
        ITEM_TYPES.put(Traits.ITEM_VISUAL, new TypeToken<ItemVisual>() {});
    }

    private static TypeToken<?> getType(String key) {
        final TraitLoader.State state = TraitLoader.getCurrentlyLoading();

        TypeToken<?> token = null;
        if (state.type == TraitLoader.Type.BLOCK)
            token = BLOCK_TYPES.get(key);
        else if (state.type == TraitLoader.Type.ITEM)
            token = ITEM_TYPES.get(key);

        if (token == null)
            token = COMMON_TYPES.get(key);

        return token;
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

            if (TraitLoader.getCurrentlyLoading() == null)
                throw new JsonParseException("Tried to parse traits without an actively loading file");

            JsonObject object = json.getAsJsonObject();
            Traits container = new Traits();

            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                TypeToken<?> type = getType(entry.getKey());
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
