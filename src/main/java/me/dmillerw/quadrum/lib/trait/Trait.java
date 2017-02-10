package me.dmillerw.quadrum.lib.trait;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public abstract class Trait<S, T> {

    protected abstract T getDefaultValue();
    protected abstract Map<String, T> getVariants();

    public abstract T getValue(S state);

    public final void merge() {
        for (String key : getVariants().keySet()) {
            if (getDefaultValue() instanceof Mergeable) {
                T merged = (T) ((Mergeable) getDefaultValue()).merge(getVariants().get(key));
                getVariants().put(key, merged);
            } else if (getDefaultValue() instanceof Collection) {
                List merged = Lists.newArrayList();
                merged.addAll((Collection) getDefaultValue());
                merged.addAll((Collection) getVariants().get(key));
                getVariants().put(key, (T) merged);
            }
        }
    }
}
