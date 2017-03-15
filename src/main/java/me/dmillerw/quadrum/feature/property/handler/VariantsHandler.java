package me.dmillerw.quadrum.feature.property.handler;

import me.dmillerw.quadrum.block.property.PropertyVariant;
import me.dmillerw.quadrum.feature.data.QuadrumData;
import me.dmillerw.quadrum.feature.property.data.Variants;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

/**
 * @author dmillerw
 */
public class VariantsHandler extends PropertyHandler<String> {

    @Override
    public boolean hasSubtypes(QuadrumData data) {
        Variants variants = data.properties.getData();
        return variants != null || variants.variants.length >= 1;
    }

    @Override
    public String[] getSubtypes(QuadrumData data) {
        Variants variants = data.properties.getData();
        return variants == null ? new String[0] : variants.variants;
    }

    @Override
    public IProperty _getBlockProperty(QuadrumData data) {
        Variants variants = data.properties.getData();
        return new PropertyVariant("variant", variants.variants);
    }

    @Override
    public String getDefaultState(QuadrumData data) {
        Variants variants = data.properties.getData();
        return variants.variants[0];
    }

    @Override
    public int getMetaFromState(QuadrumData data, IBlockState state) {
        Variants variants = data.properties.getData();

        String variant = state.getValue(getBlockProperty(data));
        for (int i=0; i<variants.variants.length; i++) {
            String v = variants.variants[i];
            if (variant.equalsIgnoreCase(v))
                return i;
        }

        return 0;
    }

    @Override
    public String getStateFromMeta(QuadrumData data, int meta) {
        Variants variants = data.properties.getData();
        return variants.variants[meta];
    }
}
