package me.dmillerw.quadrum.feature.property.handler.block;

import me.dmillerw.quadrum.block.item.ItemBlockQuadrumSlab;
import me.dmillerw.quadrum.block.sub.BlockQuadrumSlab;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.property.data.Slab;
import me.dmillerw.quadrum.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

/**
 * @author dmillerw
 */
public class BlockSlabHandler extends BlockPropertyHandler<BlockSlab.EnumBlockHalf, Slab> {

    @Override
    public Block[] loadBlocks(BlockData data) {
        BlockQuadrumSlab slab = new BlockQuadrumSlab(data, false);
        slab.setUnlocalizedName(ModInfo.MOD_ID + ":" + data.name);
        slab.setRegistryName(ModInfo.MOD_ID, data.name);

        BlockQuadrumSlab doubleSlab = new BlockQuadrumSlab(data, true);
        doubleSlab.setUnlocalizedName(ModInfo.MOD_ID + ":" + data.name + "_double");
        doubleSlab.setRegistryName(ModInfo.MOD_ID, data.name + "_double");

        return new Block[]{slab, doubleSlab};
    }

    @Override
    public ItemBlock[] loadItemBlocks(BlockData data) {
        ItemBlockQuadrumSlab slab = new ItemBlockQuadrumSlab(data, (BlockSlab) data.blocks.get(0), (BlockSlab) data.blocks.get(1));
        slab.setUnlocalizedName(ModInfo.MOD_ID + ":" + data.name);
        slab.setRegistryName(ModInfo.MOD_ID, data.name);

        return new ItemBlock[]{slab};
    }

    @Override
    public String getVariantFromBlockState(IBlockState state) {
        Block block = state.getBlock();
        if (((BlockSlab) block).isDouble()) {
            return "full";
        } else {
            return super.getVariantFromBlockState(state);
        }
    }

    @Override
    public boolean hasSubtypes() {
        return true;
    }

    @Override
    public String[] getSubtypes() {
        return new String[]{"bottom", "top", "full"};
    }

    @Override
    public IProperty _getBlockProperty() {
        return BlockSlab.HALF;
    }

    @Override
    public BlockSlab.EnumBlockHalf getDefaultState() {
        return null;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public BlockSlab.EnumBlockHalf getStateFromMeta(int meta) {
        return null;
    }
}
