package me.dmillerw.quadrum.block.data;

import me.dmillerw.quadrum.block.data.property.PropertyVariant;
import me.dmillerw.quadrum.trait.QuadrumData;

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
