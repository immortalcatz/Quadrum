package me.dmillerw.quadrum.feature.data;

import me.dmillerw.quadrum.block.property.PropertyVariant;
import me.dmillerw.quadrum.feature.data.QuadrumData;

/**
 * @author dmillerw
 */
public class BlockData extends QuadrumData {

    private PropertyVariant _property;

    public PropertyVariant getVariantProperty() {
        if (_property == null) _property = new PropertyVariant("variant", variants);
        return _property;
    }
}
