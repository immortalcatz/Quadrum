package me.dmillerw.quadrum.feature.property.handler;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.item.ItemBlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.data.QuadrumData;
import me.dmillerw.quadrum.feature.property.data.Variants;
import me.dmillerw.quadrum.item.ItemQuadrum;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * @author dmillerw
 */
public abstract class PropertyHandler<T extends Comparable<T>> {

    public QuadrumData parent;
    public Object data = new Variants();

    private IProperty<T> blockProperty;

    public abstract boolean hasSubtypes(QuadrumData data);
    public abstract String[] getSubtypes(QuadrumData data);

    /* CONSTRUCTORS */
    public Block constructBlock(BlockData data) {
        return new BlockQuadrum(data);
    }

    public ItemBlock constructItemBlock(BlockData data, Block block) {
        return new ItemBlockQuadrum(block, hasSubtypes(data)).setSubtypeNames(getSubtypes(data));
    }

    public Item constructItem(ItemData data) {
        return new ItemQuadrum(data);
    }

    /* BLOCK PROPERTY HANDLING */
    public abstract IProperty _getBlockProperty(QuadrumData data);
    public final IProperty<T> getBlockProperty(QuadrumData data) {
        if (blockProperty == null) blockProperty = _getBlockProperty(data);
        return (PropertyHelper<T>) blockProperty;
    }

    public abstract T getDefaultState(QuadrumData data);
    public abstract int getMetaFromState(QuadrumData data, IBlockState state);
    public abstract T getStateFromMeta(QuadrumData data, int meta);
}
