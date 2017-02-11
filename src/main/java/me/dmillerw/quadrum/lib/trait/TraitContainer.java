package me.dmillerw.quadrum.lib.trait;

import com.google.common.collect.Maps;
import com.google.gson.*;
import me.dmillerw.quadrum.block.data.trait.AABBTrait;
import me.dmillerw.quadrum.block.data.trait.PhysicalTrait;
import me.dmillerw.quadrum.lib.trait.impl.StringListTrait;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author dmillerw
 */
public class TraitContainer {

    private static final Map<String, Class<?>> TRAIT_TYPES = Maps.newHashMap();
    static {
        // Common
        TRAIT_TYPES.put(Trait.LORE, StringListTrait.class);
        TRAIT_TYPES.put(Trait.ORE_DICTIONARY, StringListTrait.class);

        // Item

        // Block
        TRAIT_TYPES.put(Trait.BLOCK_AABB, AABBTrait.class);
        TRAIT_TYPES.put(Trait.BLOCK_PHYSICAL, PhysicalTrait.class);
    }

    protected Map<String, Trait<?>> backingMap = Maps.newHashMap();

    protected final void merge() {
        backingMap.values().forEach(Trait::merge);
    }

    public <T extends Trait> T get(String key) {
        return (T) backingMap.get(key);
    }

    public static class Deserializer implements JsonDeserializer<TraitContainer> {

        @Override
        public TraitContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            TraitContainer container = new TraitContainer();

            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Class type = TRAIT_TYPES.get(entry.getKey());
                if (type != null) {
                    container.backingMap.put(entry.getKey(), context.deserialize(entry.getValue(), type));
                }
            }

            container.merge();

            return container;
        }
    }
}
