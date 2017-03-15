package me.dmillerw.quadrum.feature.property.handler.item;

import me.dmillerw.quadrum.feature.property.data.Variants;

/**
 * @author dmillerw
 */
public class ItemVariantsHandler extends ItemPropertyHandler<Variants> {

    @Override
    public boolean hasSubtypes() {
        return data != null || data.variants.length >= 1;
    }

    @Override
    public String[] getSubtypes() {
        return data == null ? new String[0] : data.variants;
    }
}
