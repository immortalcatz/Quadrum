package me.dmillerw.quadrum.block.data.trait;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.data.BlockData;
import net.minecraft.block.state.IBlockState;

import java.util.Map;

/**
 * @author dmillerw
 */
public abstract class Trait<T> {

    protected abstract T getDefaultValue();

    protected abstract Map<String, T> getVariants();

    public final T getValue(IBlockState state) {
        BlockData data = ((BlockQuadrum) state.getBlock()).getObject();
        if (data.variants.length > 0) {
            String value = state.getValue(data.getVariantProperty());
            T variant = getVariants().get(value);

            if (variant != null) {
                return variant;
            } else {
                return getDefaultValue();
            }
        } else {
            return getDefaultValue();
        }
    }

    public final void merge() {
        if (getDefaultValue() instanceof Mergeable) {
            for (String key : getVariants().keySet()) {
                T merged = (T) ((Mergeable) getDefaultValue()).merge(getVariants().get(key));
                getVariants().put(key, merged);
            }
        }
    }
}
