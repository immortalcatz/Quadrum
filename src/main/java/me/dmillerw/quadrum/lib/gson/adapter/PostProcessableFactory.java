package me.dmillerw.quadrum.lib.gson.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author dmillerw
 */
public class PostProcessableFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {
            public void write(JsonWriter out, T value) throws IOException {
                // Don't care about writing
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {
                JsonElement tree = elementAdapter.read(in);

                T obj = delegate.fromJsonTree(tree);

                if (obj instanceof PostProcessable) {
                    ((PostProcessable) obj).postProcess(tree);
                }

                return obj;
            }
        };
    }

    public interface PostProcessable {

        void postProcess(JsonElement element);
    }
}