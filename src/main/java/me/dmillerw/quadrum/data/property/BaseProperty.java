package me.dmillerw.quadrum.data.property;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.block.BlockQuadrum;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

/**
 * @author dmillerw
 */
public abstract class BaseProperty<T extends Comparable<T>> {

    public String name;
    public Type type;
    @SerializedName("default")
    public JsonElement defaultValue;
    private IProperty<T> property;
    private T _default;

    public final IProperty<T> getProperty() {
        return property;
    }

    public final T getDefaultValue() {
        return _default;
    }

    public void loadAdditionalData(JsonObject object) {
    }

    protected abstract T buildDefaultValue();

    protected abstract IProperty<T> buildProperty();

    public abstract int damageDropped(BlockQuadrum block, IBlockState state);

    public abstract IBlockState getStateFromMeta(BlockQuadrum block, int meta);

    public abstract int getMetaFromState(BlockQuadrum block, IBlockState state);

    public static enum Type {

        BOOLEAN(BoolProperty.class),
        INTEGER(IntProperty.class),
        DIRECTION(DirectionProperty.class),
        COLOR(ColorProperty.class);

        private Class<? extends BaseProperty> clazz;

        private Type(Class<? extends BaseProperty> clazz) {
            this.clazz = clazz;
        }

        public static Type fromString(String string) {
            if (string.equalsIgnoreCase("boolean")) {
                return BOOLEAN;
            } else if (string.equalsIgnoreCase("integer") || string.equalsIgnoreCase("int")) {
                return INTEGER;
            } else if (string.equalsIgnoreCase("direction")) {
                return DIRECTION;
            } else if (string.equalsIgnoreCase("color")) {
                return COLOR;
            } else {
                return null;
            }
        }
    }

    public static class Deserializer implements JsonDeserializer<BaseProperty> {

        @Override
        public BaseProperty deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Type type = Type.fromString(object.get("type").getAsString());
            if (type == null)
                return null;

            BaseProperty property = context.deserialize(json, type.clazz);
            property.type = type;

            property.property = property.buildProperty();
            property._default = property.buildDefaultValue();

            return property;
        }
    }
}
