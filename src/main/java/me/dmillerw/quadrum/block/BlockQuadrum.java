package me.dmillerw.quadrum.block;

import me.dmillerw.quadrum.block.lib.SharedBlockMethods;
import me.dmillerw.quadrum.feature.data.BlockData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * @author dmillerw
 */
public class BlockQuadrum extends Block implements IQuadrumBlock {

    public static BlockData HACK = null;

    private final BlockData blockData;

    public BlockQuadrum(BlockData blockData) {
        super(Material.ROCK); // Handled via state-sensitive getter

        this.blockData = blockData;

        SharedBlockMethods.construct(this);
        this.setDefaultState(SharedBlockMethods.getDefaultBlockState(this, blockState.getBaseState()));
    }

    @Override
    public BlockData getObject() {
        return blockData;
    }

    @Override
    public Random getForgeRandom() {
        return RANDOM;
    }

    /* TRAIT - PHYSICAL */
    @Override
    public Material getMaterial(IBlockState state) {
        return SharedBlockMethods.getMaterial(this, state);
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return SharedBlockMethods.getHardness(this, blockState);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return SharedBlockMethods.getExplosionResistance(this, world.getBlockState(pos));
    }

    @Override
    public int getLightValue(IBlockState state) {
        return SharedBlockMethods.getLightValue(this, state);
    }

    @Nullable
    @Override
    public String getHarvestTool(IBlockState state) {
        return SharedBlockMethods.getHarvestTool(this, state);
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return SharedBlockMethods.getHarvestLevel(this, state);
    }

    /* PHYSICAL - GRAVITY */
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        SharedBlockMethods.onBlockAdded(this, worldIn, pos, state);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        SharedBlockMethods.neighborChanged(this, state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        SharedBlockMethods.updateTick(this, worldIn, pos, state, rand);
    }

    /* TRAIT - BOUNDING BOX */
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SharedBlockMethods.getSelectionBoundingBox(this, state);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return SharedBlockMethods.getCollisionBoundingBox(this, blockState, worldIn, pos);
    }

    /* TRAIT - PARTICLES */
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        SharedBlockMethods.onRandomDisplayTick(this, state, world, pos);
    }

    /* TRAIT - REDSTONE */
    @Override
    public boolean canProvidePower(IBlockState state) {
        return SharedBlockMethods.canProvidePower(this, state);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return SharedBlockMethods.getWeakPower(this, blockState, side);
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return SharedBlockMethods.getStrongPower(this, blockState, side);
    }

    /* TRAIT - VISUAL */
    @Override
    public boolean isFullCube(IBlockState state) {
        return SharedBlockMethods.isFullCube(this, state);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return SharedBlockMethods.isOpaqueCube(this, state);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return SharedBlockMethods.canRenderInLayer(this, state, layer);
    }

    /* TRAIT - DROPS */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return SharedBlockMethods.getDrops(this, state, fortune);
    }

    /* VARIANTS / PROPERTIES */
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        SharedBlockMethods.getSubBlocks(this, list);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return SharedBlockMethods.getBlockStateFromMetadata(this, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return SharedBlockMethods.getMetadataFromBlockState(this, state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return SharedBlockMethods.getBlockStateContainer(this);
    }
}
