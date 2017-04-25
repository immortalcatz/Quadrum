package me.dmillerw.quadrum.block.sub;

import me.dmillerw.quadrum.block.IQuadrumBlock;
import me.dmillerw.quadrum.block.lib.CommonBlockMethods;
import me.dmillerw.quadrum.feature.data.BlockData;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import java.util.Random;

/**
 * @author dmillerw
 */
public class BlockQuadrumSlab extends BlockSlab implements IQuadrumBlock {

    private BlockData blockData;
    private boolean isDouble;

    public BlockQuadrumSlab(BlockData data, boolean isDouble) {
        super(Material.ROCK);

        this.blockData = data;
        this.isDouble = isDouble;

        CommonBlockMethods.construct(this);
    }

    @Override
    public BlockData getObject() {
        return blockData;
    }

    @Override
    public Random getForgeRandom() {
        return RANDOM;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();

        if (!this.isDouble()) {
            state = state.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[0]) : new BlockStateContainer(this, new IProperty[]{BlockSlab.HALF});
    }

    /* BLOCK - SLAB */
    @Override
    public String getUnlocalizedName(int meta) {
        return null;
    }

    @Override
    public boolean isDouble() {
        return isDouble;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return null;
    }
}
