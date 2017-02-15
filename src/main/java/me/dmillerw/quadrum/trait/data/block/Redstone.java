package me.dmillerw.quadrum.trait.data.block;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.trait.Mergeable;
import net.minecraft.util.EnumFacing;

import java.util.EnumSet;

/**
 * @author dmillerw
 */
public class Redstone extends Mergeable<Redstone> {

    @SerializedName("weak_power")
    public int weakPower;
    @SerializedName("strong_power")
    public int strongPower;

    public EnumSet<EnumFacing> sides = EnumSet.noneOf(EnumFacing.class);

    @Override
    public Redstone merge(Redstone other) {
        Redstone redstone = new Redstone();

        redstone.weakPower = merge(other, "weak_power", this.weakPower, other.weakPower);
        redstone.strongPower = merge(other, "strong_power", this.strongPower, other.strongPower);

        redstone.sides.addAll(this.sides);
        redstone.sides.addAll(other.sides);

        return redstone;
    }
}
