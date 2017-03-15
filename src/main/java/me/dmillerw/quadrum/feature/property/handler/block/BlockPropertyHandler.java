package me.dmillerw.quadrum.feature.property.handler.block;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.item.ItemBlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

/**
 * @author dmillerw
 */
public abstract class BlockPropertyHandler<T extends Comparable<T>, V> extends PropertyHandler<V> {

    private IProperty<T> blockProperty;

    public Block constructBlock(BlockData data) {
        return new BlockQuadrum(data);
    }

    public ItemBlock constructItemBlock(BlockData data, Block block) {
        return new ItemBlockQuadrum(block, hasSubtypes()).setSubtypeNames(getSubtypes());
    }

    public abstract IProperty _getBlockProperty();
    public final IProperty<T> getBlockProperty() {
        if (blockProperty == null) blockProperty = _getBlockProperty();
        return (PropertyHelper<T>) blockProperty;
    }

    public abstract T getDefaultState();
    public abstract int getMetaFromState(IBlockState state);
    public abstract T getStateFromMeta(int meta);
}
