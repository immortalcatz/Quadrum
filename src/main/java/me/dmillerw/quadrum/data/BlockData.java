package me.dmillerw.quadrum.data;

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

        public BaseProperty main;
        public BaseProperty[] others;
    }
}
