package me.dmillerw.quadrum.lib.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dmillerw.quadrum.lib.gson.adapter.PostProcessableFactory;
import me.dmillerw.quadrum.lib.gson.deserializer.AABBDeserializer;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author dmillerw
 */
public class GsonLib {

    private static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapterFactory(new PostProcessableFactory());

            builder.registerTypeAdapter(AxisAlignedBB.class, new AABBDeserializer());

            gson = builder.create();
        }
        return gson;
    }
}
