package me.dmillerw.quadrum.block.item;

import me.dmillerw.quadrum.feature.data.BlockData;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

/**
 * @author dmillerw
 */
public class ItemBlockQuadrumSlab extends ItemSlab {

    public ItemBlockQuadrumSlab(BlockData data, BlockSlab singleSlab, BlockSlab doubleSlab) {
        super(singleSlab, singleSlab, doubleSlab);
    }
}
