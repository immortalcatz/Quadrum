package me.dmillerw.quadrum.feature.data;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import java.util.List;

/**
 * @author dmillerw
 */
public class BlockData extends QuadrumData {

    public List<Block> blocks = Lists.newArrayList();
    public List<ItemBlock> itemBlocks = Lists.newArrayList();
}
