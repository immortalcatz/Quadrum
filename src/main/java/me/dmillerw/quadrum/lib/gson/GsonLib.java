package me.dmillerw.quadrum.lib.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dmillerw.quadrum.feature.trait.TraitContainer;
import me.dmillerw.quadrum.lib.gson.adapter.NumberFactory;
import me.dmillerw.quadrum.lib.gson.adapter.PostProcessableFactory;
import me.dmillerw.quadrum.lib.gson.deserializer.AABBDeserializer;
import me.dmillerw.quadrum.lib.gson.deserializer.ItemStackDeserializer;
import me.dmillerw.quadrum.lib.gson.deserializer.MaterialDeserializer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
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
            builder.registerTypeAdapterFactory(new NumberFactory());

            builder.registerTypeAdapter(TraitContainer.class, new TraitContainer.Deserializer());
            builder.registerTypeAdapter(AxisAlignedBB.class, new AABBDeserializer());
            builder.registerTypeAdapter(Material.class, new MaterialDeserializer());
            builder.registerTypeAdapter(ItemStack.class, new ItemStackDeserializer());

            gson = builder.create();
        }
        return gson;
    }
}
