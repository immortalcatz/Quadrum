package me.dmillerw.quadrum.block.data.trait;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.lib.trait.Mergeable;
import me.dmillerw.quadrum.lib.trait.Trait;
import net.minecraft.block.material.Material;

import java.util.Map;

/**
 * @author dmillerw
 */
public class PhysicalTrait extends Trait<PhysicalTrait.Data> {

    @SerializedName("default")
    private Data _default = new Data();
    private Map<String, Data> variants = Maps.newHashMap();

    @Override
    protected Data getDefaultValue() {
        return _default;
    }

    @Override
    protected Map<String, Data> getVariants() {
        return variants;
    }

    public static class Data extends Mergeable<Data> {

        public Material material = Material.ROCK;
        public float hardness;
        public float resistance;
        public int light;

        @Override
        public Data merge(Data other) {
            Data data = new Data();

            if (other.isSet("material")) data.material = other.material;
            else data.material = this.material;
            if (other.isSet("hardness")) data.hardness = other.hardness;
            else data.hardness = this.hardness;
            if (other.isSet("resistance")) data.resistance = other.resistance;
            else data.resistance = this.resistance;
            if (other.isSet("light")) data.light = other.light;
            else data.light = this.light;

            return data;
        }
    }
}
