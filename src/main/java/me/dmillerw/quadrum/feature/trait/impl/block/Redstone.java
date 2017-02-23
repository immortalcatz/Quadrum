package me.dmillerw.quadrum.feature.trait.impl.block;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.trait.util.Mergeable;
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
    public void mergeSpecials(Redstone newInstance, Redstone defaultValues, Redstone changedValues) {
        newInstance.sides = EnumSet.noneOf(EnumFacing.class);
        newInstance.sides.addAll(defaultValues.sides);
        newInstance.sides.addAll(changedValues.sides);
    }
}
