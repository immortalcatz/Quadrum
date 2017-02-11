package me.dmillerw.quadrum.lib.data;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.lib.trait.TraitContainer;

/**
 * @author dmillerw
 */
public class QuadrumData {

    public String name;

    @SerializedName("creative_tab")
    public String creativeTab;

    public String[] variants = new String[0];

    public TraitContainer traits = new TraitContainer();
}
