package me.dmillerw.quadrum.feature.trait.impl.block;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.trait.util.Trait;

/**
 * @author dmillerw
 */
public class BlockVisual extends Trait<BlockVisual> {

    public boolean transparent = true;
    @SerializedName("full_cube")
    public boolean fullCube = true;
}
