package me.dmillerw.quadrum.item.data;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.item.data.trait.impl.LoreTrait;

/**
 * @author dmillerw
 */
public class ItemData {

    public String name;
    @SerializedName("creative_tab")
    public String creativeTab;

    public LoreTrait lore = new LoreTrait();

    public String[] variants = new String[0];
}
