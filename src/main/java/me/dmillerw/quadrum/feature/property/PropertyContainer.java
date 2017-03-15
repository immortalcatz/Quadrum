package me.dmillerw.quadrum.feature.property;

import com.google.gson.*;
import me.dmillerw.quadrum.feature.DataType;
import me.dmillerw.quadrum.feature.loader.LoaderState;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.feature.property.handler.VariantsHandler;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class PropertyContainer {

    public Properties type = Properties.VARIANTS;
    public PropertyHandler propertyHandler = new VariantsHandler();

    public <T> T getData() {
        return (T) propertyHandler.data;
    }

    public static class Deserializer implements JsonDeserializer<PropertyContainer> {

        private static Properties getPropertyFromString(String key) {
            final LoaderState.State state = LoaderState.getCurrentlyLoading();

            Properties prop = null;
            if (state.type == LoaderState.Type.BLOCK)
                prop = Properties.get(DataType.BLOCK, key);
            else if (state.type == LoaderState.Type.ITEM)
                prop = Properties.get(DataType.ITEM, key);

            if (prop == null)
                prop = Properties.get(DataType.COMMON, key);

            return prop;
        }

        @Override
        public PropertyContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonObject())
                throw new JsonParseException("Tried to parse properties from something other than a JsonObject!");

            if (LoaderState.getCurrentlyLoading() == null)
                throw new JsonParseException("Tried to parse properties without an actively loading file");

            JsonObject object = json.getAsJsonObject();
            PropertyContainer propertyContainer = new PropertyContainer();

            Properties property = getPropertyFromString(object.get("type").getAsString());
            if (property == null)
                throw new JsonParseException("Tried to parse Property of type " + object.get("type").getAsString() + ", which doesn't exist");

            propertyContainer.type = property;
            propertyContainer.propertyHandler = property.getPropertyHandler();
            propertyContainer.propertyHandler.data = context.deserialize(object, property.dataType.getType());

            return propertyContainer;
        }
    }
}
