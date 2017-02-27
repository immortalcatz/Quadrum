package me.dmillerw.quadrum.feature.trait;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import me.dmillerw.quadrum.feature.loader.TraitState;
import me.dmillerw.quadrum.feature.trait.util.Trait;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public class TraitContainer {

    public static final TypeToken<List<String>> TYPE_LIST = new TypeToken<List<String>>() {};

    private static Traits getTraitFromString(String key) {
        final TraitState.State state = TraitState.getCurrentlyLoading();

        Traits trait = null;
        if (state.type == TraitState.Type.BLOCK)
            trait = Traits.get(Traits.Type.BLOCK, key);
        else if (state.type == TraitState.Type.ITEM)
            trait = Traits.get(Traits.Type.ITEM, key);

        if (trait == null)
            trait = Traits.get(Traits.Type.COMMON, key);

        return trait;
    }

    protected EnumMap<Traits, TraitHolder<?>> backingMap = Maps.newEnumMap(Traits.class);

    protected final void merge() {
        backingMap.values().forEach(TraitHolder::merge);
    }

    public <T> TraitHolder<T> get(Traits trait) {
        return (TraitHolder<T>) backingMap.get(trait);
    }
    public <T> TraitHolder<T> get(Traits trait, Class<T> clazz) {
        return (TraitHolder<T>) backingMap.get(trait);
    }
    public <T> TraitHolder<T> get(Traits trait, TypeToken<T> clazz) {
        return (TraitHolder<T>) backingMap.get(trait);
    }

    public static class Deserializer implements JsonDeserializer<TraitContainer> {

        @Override
        public TraitContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonObject())
                throw new JsonParseException("Tried to parse traits from something other than a JsonObject!");

            if (TraitState.getCurrentlyLoading() == null)
                throw new JsonParseException("Tried to parse traits without an actively loading file");

            JsonObject object = json.getAsJsonObject();
            TraitContainer container = new TraitContainer();

            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Traits traitEnum = getTraitFromString(entry.getKey());
                if (traitEnum == null) {
                    throw new JsonParseException("Tried to load a trait of type `" + entry.getKey() + "`, which doesn't exist");
                }

                TypeToken type = traitEnum.typeToken;

                TraitHolder trait = new TraitHolder();

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

                if (trait.defaultValue != null || (trait.variants != null && !trait.variants.isEmpty())) {
                    if (trait.defaultValue instanceof Trait && !((Trait) trait.defaultValue).isValid())
                        throw new JsonParseException("Failed to properly parse a trait!");

                    for (Object obj : trait.variants.values()) {
                        if (obj instanceof Trait && !((Trait) obj).isValid()) {
                            throw new JsonParseException("Failed to properly parse a trait!");
                        }
                    }

                    container.backingMap.put(traitEnum, trait);
                }
            }

            container.merge();
            return container;
        }
    }
}
