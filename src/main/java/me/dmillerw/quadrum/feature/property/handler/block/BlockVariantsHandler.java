package me.dmillerw.quadrum.feature.property.handler.block;

import me.dmillerw.quadrum.block.property.PropertyVariant;
import me.dmillerw.quadrum.feature.property.data.Variants;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

/**
 * @author dmillerw
 */
public class BlockVariantsHandler extends BlockPropertyHandler<String, Variants> {

    @Override
    public boolean hasSubtypes() {
        return data != null || data.variants.length >= 1;
    }

    @Override
    public String[] getSubtypes() {
        return data == null ? new String[0] : data.variants;
    }

    @Override
    public IProperty _getBlockProperty() {
        return new PropertyVariant("variant", data.variants);
    }

    @Override
    public String getDefaultState() {
        return data.variants[0];
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        String variant = state.getValue(getBlockProperty());
        for (int i=0; i<data.variants.length; i++) {
            String v = data.variants[i];
            if (variant.equalsIgnoreCase(v))
                return i;
        }

        return 0;
    }

    @Override
    public String getStateFromMeta(int meta) {
        return data.variants[meta];
    }
}
