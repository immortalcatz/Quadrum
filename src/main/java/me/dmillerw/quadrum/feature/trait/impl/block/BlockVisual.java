package me.dmillerw.quadrum.feature.trait.impl.block;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.trait.util.Mergeable;

/**
 * @author dmillerw
 */
public class BlockVisual extends Mergeable<BlockVisual> {

    public boolean transparent = true;
    @SerializedName("full_cube")
    public boolean fullCube = true;
}
