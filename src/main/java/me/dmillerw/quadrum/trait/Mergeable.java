package me.dmillerw.quadrum.trait;

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

    public final <T> T merge(Mergeable other, String data, T ourValue, T otherValue) {
        if (other.isSet(data))
            return otherValue;
        else
            return ourValue;
    }

    //TODO: Make this use reflection or something to automagically merge shiz
    public abstract S merge(S other);
}
