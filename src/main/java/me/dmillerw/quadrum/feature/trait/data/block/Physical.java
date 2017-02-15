package me.dmillerw.quadrum.feature.trait.data.block;

import com.google.common.collect.Maps;
import me.dmillerw.quadrum.feature.trait.util.Mergeable;
import net.minecraft.block.material.Material;

import java.util.Map;

public class Physical extends Mergeable<Physical> {

    private static final Map<String, Material> materialMap = Maps.newHashMap();
    static {
        materialMap.put("AIR", Material.AIR);
        materialMap.put("GRASS", Material.GRASS);
        materialMap.put("GROUND", Material.GROUND);
        materialMap.put("WOOD", Material.WOOD);
        materialMap.put("ROCK", Material.ROCK);
        materialMap.put("IRON", Material.IRON);
        materialMap.put("LEAVES", Material.LEAVES);
        materialMap.put("PLANTS", Material.PLANTS);
        materialMap.put("CLOTH", Material.CLOTH);
        materialMap.put("SAND", Material.SAND);
        materialMap.put("GLASS", Material.GLASS);
        materialMap.put("ICE", Material.ICE);
        materialMap.put("SNOW", Material.SNOW);
        materialMap.put("CLAY", Material.CLAY);
    }

    public static Material getMaterialFromString(String material) {
        return materialMap.getOrDefault(material.toUpperCase(), Material.GROUND);
    }

    public Material material = Material.ROCK;
    public float hardness;
    public float resistance;
    public int light;

    public boolean gravity;

    @Override
    public Physical merge(Physical other) {
        Physical physical = new Physical();

        physical.material = merge(other, "material", this.material, other.material);
        physical.hardness = merge(other, "hardness", this.hardness, other.hardness);
        physical.resistance = merge(other, "resistance", this.resistance, other.resistance);
        physical.light = merge(other, "light", this.light, other.light);
        physical.gravity = merge(other, "gravity", this.gravity, other.gravity);

        return physical;
    }
}