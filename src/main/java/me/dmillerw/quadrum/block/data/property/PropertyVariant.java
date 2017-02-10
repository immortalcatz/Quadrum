package me.dmillerw.quadrum.block.data.property;

import com.google.common.base.Optional;
import net.minecraft.block.properties.PropertyHelper;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author dmillerw
 */
public class PropertyVariant extends PropertyHelper<String> {

    private final String[] variants;

    public PropertyVariant(String name, String[] variants) {
        super(name, String.class);
        this.variants = variants;
    }

    @Override
    public Collection<String> getAllowedValues() {
        return Arrays.asList(variants);
    }

    @Override
    public Optional<String> parseValue(String value) {
        return Optional.of(value);
    }

    @Override
    public String getName(String value) {
        return value;
    }
}
