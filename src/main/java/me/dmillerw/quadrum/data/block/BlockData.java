package me.dmillerw.quadrum.data.block;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.data.property.BaseProperty;

/**
 * @author dmillerw
 */
public class BlockData {

    public String name;
    @SerializedName("creative_tab")
    public String creativeTab;
    public Properties properties;

    public static class Properties {

        public BaseProperty serialized;
        @SerializedName("use_subtypes")
        public boolean useSubtypes;
    }
}
