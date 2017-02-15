package me.dmillerw.quadrum.lib.gson.deserializer;

import com.google.gson.*;
import me.dmillerw.quadrum.feature.trait.data.util.RandomNumber;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class RandomNumberDeserializer implements JsonDeserializer<RandomNumber> {

    @Override
    public RandomNumber deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RandomNumber random = new RandomNumber();

        if (json.isJsonPrimitive()) {
            random.value = new double[] { json.getAsDouble() };
        } else if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            if (array.size() == 2) {
                random.value = new double[] { array.get(0).getAsDouble(), array.get(1).getAsDouble() };
            }
        } else if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            random.value = new double[] { object.get("min").getAsDouble(), object.get("max").getAsDouble() };
        }

        return random;
    }
}
