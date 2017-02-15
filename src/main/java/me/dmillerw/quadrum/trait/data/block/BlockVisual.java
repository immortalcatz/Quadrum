package me.dmillerw.quadrum.trait.data.block;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.trait.Mergeable;
import net.minecraft.util.BlockRenderLayer;

/**
 * @author dmillerw
 */
public class BlockVisual extends Mergeable<BlockVisual> {

    public boolean transparent = true;
    @SerializedName("full_cube")
    public boolean fullCube = true;
    @SerializedName("render_type")
    public BlockRenderLayer renderType = BlockRenderLayer.SOLID;

    @Override
    public BlockVisual merge(BlockVisual other) {
        BlockVisual visual = new BlockVisual();

        merge(other, "transparent", this.transparent, other.transparent);
        merge(other, "full_cube", this.fullCube, other.fullCube);
        merge(other, "render_type", this.renderType, other.renderType);

        return visual;
    }
}
