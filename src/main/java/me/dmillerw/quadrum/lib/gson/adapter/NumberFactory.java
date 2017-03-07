package me.dmillerw.quadrum.lib.gson.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.dmillerw.quadrum.feature.trait.impl.util.RandomNumber;

import java.io.IOException;

/**
 * @author dmillerw
 */
public class NumberFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        if (type.getRawType() != Number.class)
            return null;

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {
                JsonElement element = elementAdapter.read(in);
                if (element.isJsonPrimitive()) {
                    return delegate.fromJsonTree(element);
                } else if (element.isJsonArray()) {
                    Number min = (Number) delegate.fromJsonTree(element.getAsJsonArray().get(0));
                    Number max = (Number) delegate.fromJsonTree(element.getAsJsonArray().get(1));

                    RandomNumber randomNumber = new RandomNumber();
                    randomNumber.value = new double[] { min.doubleValue(), max.doubleValue() };

                    return (T) randomNumber;
                } else {
                    return null;
                }
            }
        };
    }
}