package me.dmillerw.quadrum.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dmillerw.quadrum.data.property.BaseProperty;

/**
 * @author dmillerw
 */
public class GsonLib {

    private static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(BaseProperty.class, new BaseProperty.Deserializer());

            gson = builder.create();
        }
        return gson;
    }
}
