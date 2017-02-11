package me.dmillerw.quadrum.block;

import me.dmillerw.quadrum.block.data.BlockData;
import me.dmillerw.quadrum.block.data.trait.PhysicalTrait;
import me.dmillerw.quadrum.lib.IQuadrumObject;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import me.dmillerw.quadrum.lib.trait.Trait;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class BlockQuadrum extends Block implements IQuadrumObject<BlockData> {

    public static BlockData HACK = null;

    private final BlockData blockData;

    public BlockQuadrum(BlockData blockData) {
        super(Material.ROCK); // Handled via state-sensitive getter

        this.blockData = blockData;

        // Creative Tabs
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab.getTabLabel().equalsIgnoreCase(blockData.creativeTab)) {
                setCreativeTab(tab);
                break;
            }
        }
        if (getCreativeTabToDisplayOn() == null) setCreativeTab(ModCreativeTab.TAB);

        if (blockData.variants.length > 0)
            this.setDefaultState(this.blockState.getBaseState().withProperty(blockData.getVariantProperty(), blockData.variants[0]));
    }

    // Traits
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return ((AxisAlignedBB) blockData.traits.get(Trait.BLOCK_AABB).getValueFromBlockState(state));
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return ((PhysicalTrait) blockData.traits.get(Trait.BLOCK_PHYSICAL)).getValueFromBlockState(state).material;
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return ((PhysicalTrait) blockData.traits.get(Trait.BLOCK_PHYSICAL)).getValueFromBlockState(blockState).hardness;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return ((PhysicalTrait) blockData.traits.get(Trait.BLOCK_PHYSICAL)).getValueFromBlockState(world.getBlockState(pos)).resistance;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return ((PhysicalTrait) blockData.traits.get(Trait.BLOCK_PHYSICAL)).getValueFromBlockState(state).light;
    }

    // Variants
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (blockData.variants.length > 0) {
            for (int i = 0; i < blockData.variants.length; i++) list.add(new ItemStack(this, 1, i));
        } else {
            super.getSubBlocks(itemIn, tab, list);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (blockData.variants.length > 0)
            return getDefaultState().withProperty(blockData.getVariantProperty(), blockData.variants[meta]);
        else
            return super.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (blockData.variants.length > 0) {
            String variant = state.getValue(blockData.getVariantProperty());
            for (int i = 0; i < blockData.variants.length; i++) {
                if (variant.equals(blockData.variants[i]))
                    return i;
            }
        }

        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if (HACK.variants.length > 0)
            return new BlockStateContainer(this, HACK.getVariantProperty());
        else
            return new BlockStateContainer(this);
    }

    @Override
    public BlockData getObject() {
        return blockData;
    }
}
