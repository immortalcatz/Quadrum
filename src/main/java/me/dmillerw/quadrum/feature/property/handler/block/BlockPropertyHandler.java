package me.dmillerw.quadrum.feature.property.handler.block;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.item.ItemBlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.lib.ModInfo;
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

    public Block[] loadBlocks(BlockData data) {
        Block block = new BlockQuadrum(data);

        block.setUnlocalizedName(ModInfo.MOD_ID + ":" + data.name);
        block.setRegistryName(ModInfo.MOD_ID, data.name);

        return new Block[] { block };
    }

    public ItemBlock[] loadItemBlocks(BlockData data) {
        ItemBlockQuadrum itemBlock = new ItemBlockQuadrum(data.blocks.get(0), hasSubtypes());
        if (hasSubtypes()) itemBlock.setSubtypeNames(getSubtypes());

        itemBlock.setRegistryName(ModInfo.MOD_ID, data.name);

        return new ItemBlock[] { itemBlock };
    }

    public String getVariantFromBlockState(IBlockState state) {
        IProperty property = getBlockProperty();
        return property.getName(state.getValue(property));
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
