package me.dmillerw.quadrum.block.data.trait;

import me.dmillerw.quadrum.lib.trait.Mergeable;
import net.minecraft.block.material.Material;

public class Physical extends Mergeable<Physical> {

    public Material material = Material.ROCK;
    public float hardness;
    public float resistance;
    public int light;

    @Override
    public Physical merge(Physical other) {
        Physical physical = new Physical();

        if (other.isSet("material")) physical.material = other.material;
        else physical.material = this.material;
        if (other.isSet("hardness")) physical.hardness = other.hardness;
        else physical.hardness = this.hardness;
        if (other.isSet("resistance")) physical.resistance = other.resistance;
        else physical.resistance = this.resistance;
        if (other.isSet("light")) physical.light = other.light;
        else physical.light = this.light;

        return physical;
    }
}