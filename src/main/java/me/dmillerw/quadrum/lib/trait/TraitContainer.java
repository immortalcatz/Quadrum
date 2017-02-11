package me.dmillerw.quadrum.lib.trait;

import com.google.common.collect.Maps;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author dmillerw
 */
public class TraitContainer {

    protected Map<String, Trait> backingMap = Maps.newHashMap();

    protected final void merge() {
        backingMap.values().forEach(Trait::merge);
    }

    public Trait get(String key) {
        return backingMap.get(key);
    }

    public static class Deserializer implements JsonDeserializer<TraitContainer> {

        @Override
        public TraitContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            TraitContainer container = new TraitContainer();

            for (String trait : Trait.TRAIT_TYPES.keySet()) {
                if (object.has(trait)) {
                    JsonObject jtrait = object.getAsJsonObject(trait);
                    Trait t = new Trait();

                    if (jtrait.has("default")) {
                        t.defaultValue = context.deserialize(jtrait.get("default"), Trait.TRAIT_TYPES.get(trait).getType());
                    } else {
                        t.defaultValue = Trait.DEFAULT_VALUES.get(trait).get();
                    }

                    if (jtrait.has("variants")) {
                        Map<String, Object> variants = Maps.newHashMap();

                        for (Map.Entry<String, JsonElement> entry : jtrait.get("variants").getAsJsonObject().entrySet()) {
                            variants.put(entry.getKey(), context.deserialize(entry.getValue(), Trait.TRAIT_TYPES.get(trait).getType()));
                        }

                        t.variants = variants;
                    }

                    container.backingMap.put(trait, t);
                } else {
                    Trait t = new Trait();
                    t.defaultValue = Trait.DEFAULT_VALUES.get(trait).get();
                    container.backingMap.put(trait, t);
                }
            }

            container.merge();

            return container;
        }
    }
}
