package me.dmillerw.quadrum.feature.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.property.PropertyContainer;
import me.dmillerw.quadrum.feature.trait.TraitContainer;
import me.dmillerw.quadrum.feature.trait.TraitHolder;
import me.dmillerw.quadrum.feature.trait.Traits;

/**
 * @author dmillerw
 */
public class QuadrumData {

    public String name;

    @SerializedName("creative_tab")
    public String creativeTab;

    public PropertyContainer properties = new PropertyContainer();
    public TraitContainer traits = new TraitContainer();

    /* PROPERTY HELPERS */
    public <T> T getPropertyData() {
        return (T) properties.propertyHandler.data;
    }

    /* TRAIT HELPERS */
    public <T> TraitHolder<T> getTrait(Traits trait) {
        return traits.get(trait);
    }

    public <T> TraitHolder<T> getTrait(Traits trait, Class<T> clazz) {
        return traits.get(trait, clazz);
    }

    public <T> TraitHolder<T> getTrait(Traits trait, TypeToken<T> clazz) {
        return traits.get(trait, clazz);
    }
}
