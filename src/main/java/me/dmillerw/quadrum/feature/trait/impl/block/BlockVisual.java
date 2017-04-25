package me.dmillerw.quadrum.feature.trait.impl.block;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.trait.util.Trait;

/**
 * @author dmillerw
 */
public class BlockVisual extends Trait<BlockVisual> {

    public static final String TRANSPARENCY_NONE = "none";
    public static final String TRANSPARENCY_PARTIAL = "partial";
    public static final String TRANSPARENCY_FULL = "full";

    public String transparency;
    @SerializedName("full_cube")
    public boolean fullCube = true;
}
