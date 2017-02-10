package me.dmillerw.quadrum.block.data.trait;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.data.BlockData;
import me.dmillerw.quadrum.lib.trait.Trait;
import net.minecraft.block.state.IBlockState;

/**
 * @author dmillerw
 */
public abstract class BlockTrait<T> extends Trait<IBlockState, T> {

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
}
