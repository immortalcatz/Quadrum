package me.dmillerw.quadrum.lib.trait.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.lib.trait.Trait;

import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public class StringListTrait extends Trait<List<String>> {

    @SerializedName("default")
    private List<String> _default = Lists.newArrayList();
    private Map<String, List<String>> variants = Maps.newHashMap();

    @Override
    protected List<String> getDefaultValue() {
        return _default;
    }

    @Override
    protected Map<String, List<String>> getVariants() {
        return variants;
    }
}
