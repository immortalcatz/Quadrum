package me.dmillerw.quadrum.feature.trait.data.block;

import me.dmillerw.quadrum.feature.trait.util.Mergeable;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author dmillerw
 */
public class BoundingBox extends Mergeable<BoundingBox> {

    public AxisAlignedBB selection = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
    public AxisAlignedBB collision = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

    public AxisAlignedBB getSelectionBoundingBox() {
        return nullBox(selection) ? Block.NULL_AABB : selection;
    }

    public AxisAlignedBB getCollisionBoundingBox() {
        return nullBox(collision) ? Block.NULL_AABB : collision;
    }

    private boolean nullBox(AxisAlignedBB aabb) {
        return aabb.minX == 0 && aabb.maxX == 0 && aabb.minY == 0 && aabb.maxY == 0 && aabb.minZ == 0 && aabb.maxZ == 0;
    }

    @Override
    public BoundingBox merge(BoundingBox other) {
        BoundingBox boundingBox = new BoundingBox();

        boundingBox.selection = merge(other, "selection", this.selection, other.selection);
        boundingBox.collision = merge(other, "collision", this.collision, other.collision);

        return boundingBox;
    }
}
