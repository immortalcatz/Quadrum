package me.dmillerw.quadrum.block.data.trait.impl;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.block.data.trait.BlockTrait;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Map;

/**
 * @author dmillerw
 */
public class AABBTrait extends BlockTrait<AxisAlignedBB> {

    @SerializedName("default")
    private AxisAlignedBB _default = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private Map<String, AxisAlignedBB> variants = Maps.newHashMap();

    @Override
    protected AxisAlignedBB getDefaultValue() {
        return _default;
    }

    @Override
    protected Map<String, AxisAlignedBB> getVariants() {
        return variants;
    }
}
