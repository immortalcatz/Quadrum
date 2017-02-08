package me.dmillerw.quadrum.item.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author dmillerw
 */
public class ItemData {

    public String name;
    @SerializedName("creative_tab")
    public String creativeTab;

    public String[] variants = new String[0];
}
