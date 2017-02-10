package me.dmillerw.quadrum.block.data.trait;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import me.dmillerw.quadrum.lib.gson.adapter.PostProcessableFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author dmillerw
 */
public abstract class Mergeable<S> implements PostProcessableFactory.PostProcessable {

    private final Set<String> set = Sets.newHashSet();

    public final boolean isSet(String key) {
        return set.contains(key);
    }

    @Override
    public void postProcess(JsonElement element) {
        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet())
            set.add(entry.getKey());
    }

    public abstract S merge(S other);
}
