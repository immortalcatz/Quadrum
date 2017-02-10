package me.dmillerw.quadrum.lib.gson.deserializer;

import com.google.gson.*;
import net.minecraft.util.math.AxisAlignedBB;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class AABBDeserializer implements JsonDeserializer<AxisAlignedBB> {

    @Override
    public AxisAlignedBB deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();

            double minX = array.get(0).getAsDouble();
            double minY = array.get(1).getAsDouble();
            double minZ = array.get(2).getAsDouble();
            double maxX = array.get(3).getAsDouble();
            double maxY = array.get(4).getAsDouble();
            double maxZ = array.get(5).getAsDouble();

            return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        } else if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();

            double minX = object.get("minX").getAsDouble();
            double minY = object.get("minY").getAsDouble();
            double minZ = object.get("minZ").getAsDouble();
            double maxX = object.get("maxX").getAsDouble();
            double maxY = object.get("maxY").getAsDouble();
            double maxZ = object.get("maxZ").getAsDouble();

            return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        } else {
            return null;
        }
    }
}
