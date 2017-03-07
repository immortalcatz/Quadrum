package me.dmillerw.quadrum.feature.data;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.trait.TraitContainer;

/**
 * @author dmillerw
 */
public class QuadrumData {

    public String name;

    @SerializedName("creative_tab")
    public String creativeTab;

    @SerializedName("max_stack_size")
    public int maxStackSize = 64;

    public String[] variants = new String[0];

    public TraitContainer traits = new TraitContainer();
}
