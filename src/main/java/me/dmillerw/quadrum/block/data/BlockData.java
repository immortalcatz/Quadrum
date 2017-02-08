package me.dmillerw.quadrum.block.data;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.block.data.property.BaseProperty;

/**
 * @author dmillerw
 */
public class BlockData {

    public String name;
    @SerializedName("creative_tab")
    public String creativeTab;
    public BaseProperty property;
}
