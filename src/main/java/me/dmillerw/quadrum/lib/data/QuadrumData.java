package me.dmillerw.quadrum.lib.data;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.trait.Traits;

/**
 * @author dmillerw
 */
public class QuadrumData {

    public String name;

    @SerializedName("creative_tab")
    public String creativeTab;

    public String[] variants = new String[0];

    public Traits traits = new Traits();
}
