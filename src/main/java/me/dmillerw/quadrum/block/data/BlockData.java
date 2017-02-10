package me.dmillerw.quadrum.block.data;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.block.data.property.PropertyVariant;

/**
 * @author dmillerw
 */
public class BlockData {

    public String name;
    @SerializedName("creative_tab")
    public String creativeTab;

    public String[] variants = new String[0];

    private PropertyVariant _property;

    public PropertyVariant getVariantProperty() {
        if (_property == null) _property = new PropertyVariant("variant", variants);
        return _property;
    }
}
