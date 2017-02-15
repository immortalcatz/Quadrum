package me.dmillerw.quadrum.lib.gson.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import me.dmillerw.quadrum.feature.trait.data.block.Physical;
import net.minecraft.block.material.Material;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class MaterialDeserializer implements JsonDeserializer<Material> {

    @Override
    public Material deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Physical.getMaterialFromString(json.getAsString());
    }
}
