package me.dmillerw.quadrum.block.data;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.block.data.property.PropertyVariant;
import me.dmillerw.quadrum.block.data.trait.impl.AABBTrait;
import me.dmillerw.quadrum.block.data.trait.impl.PhysicalTrait;

/**
 * @author dmillerw
 */
public class BlockData {

    public String name;
    @SerializedName("creative_tab")
    public String creativeTab;

    // Variants
    public String[] variants = new String[0];
    // TraitsContainer
    public TraitsContainer traits = new TraitsContainer();
    private PropertyVariant _property;

    public PropertyVariant getVariantProperty() {
        if (_property == null) _property = new PropertyVariant("variant", variants);
        return _property;
    }

    public static class TraitsContainer {

        public AABBTrait aabb = new AABBTrait();
        public PhysicalTrait physical = new PhysicalTrait();

        public void merge() {
            aabb.merge();
            physical.merge();
        }
    }
}
