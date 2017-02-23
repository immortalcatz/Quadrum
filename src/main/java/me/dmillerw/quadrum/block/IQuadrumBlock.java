package me.dmillerw.quadrum.block;

import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.data.IQuadrumObject;
import me.dmillerw.quadrum.feature.trait.QuadrumTrait;
import me.dmillerw.quadrum.feature.trait.Traits;
import me.dmillerw.quadrum.feature.trait.impl.block.*;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public interface IQuadrumBlock extends IQuadrumObject<BlockData> {

    public default void construct() {
        final Block block = (Block) this;
        final BlockData blockData = getObject();

        // Creative Tabs
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab.getTabLabel().equalsIgnoreCase(blockData.creativeTab)) {
                block.setCreativeTab(tab);
                break;
            }
        }
        if (block.getCreativeTabToDisplayOn() == null) block.setCreativeTab(ModCreativeTab.TAB);
    }

    /* VARIANTS */
    public default IBlockState i_getDefaultState(Block block, IBlockState baseState) {
        BlockData blockData = ((BlockQuadrum)block).getObject();
        if (blockData.variants.length > 0) {
            return baseState.withProperty(blockData.getVariantProperty(), blockData.variants[0]);
        } else {
            return baseState;
        }
    }

    public default void i_getSubBlocks(Item item, NonNullList<ItemStack> list) {
        BlockData blockData = getObject();
        if (blockData.variants.length > 0) {
            for (int i = 0; i < blockData.variants.length; i++)
                list.add(new ItemStack(item, 1, i));
        } else {
            list.add(new ItemStack(item, 1, 0));
        }
    }

    public default IBlockState i_getStateFromMetadata(int metadata) {
        BlockData blockData = getObject();
        IBlockState defaultState = ((Block)this).getDefaultState();

        if (blockData.variants.length > 0) {
            return defaultState.withProperty(blockData.getVariantProperty(), blockData.variants[metadata]);
        } else {
            return defaultState;
        }
    }

    public default int i_getMetadataFromState(IBlockState state) {
        BlockData blockData = getObject();

        if (blockData.variants.length > 0) {
            String variant = state.getValue(blockData.getVariantProperty());
            for (int i = 0; i < blockData.variants.length; i++) {
                if (variant.equals(blockData.variants[i]))
                    return i;
            }
        }

        return 0;
    }

    public default BlockStateContainer i_getBlockStateContainer() {
        if (BlockQuadrum.HACK.variants.length > 0)
            return new BlockStateContainer((Block)this, BlockQuadrum.HACK.getVariantProperty());
        else
            return new BlockStateContainer((Block)this);
    }

    /* TRAIT - BOUNDING BOX */
    public default AxisAlignedBB i_getSelectionBoundingBox(IBlockState state) {
        QuadrumTrait<BoundingBox> trait = getObject().traits.get(Traits.BLOCK_BOUNDING_BOX);
        if (trait != null) {
            return trait.getValueFromBlockState(state).getSelectionBoundingBox();
        } else {
            return Block.FULL_BLOCK_AABB;
        }
    }

    public default AxisAlignedBB i_getCollisionBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
        QuadrumTrait<BoundingBox> trait = getObject().traits.get(Traits.BLOCK_BOUNDING_BOX);
        if (trait != null) {
            return trait.getValueFromBlockState(state).getCollisionBoundingBox();
        } else {
            return state.getBoundingBox(access, pos);
        }
    }

    /* TRAIT - PHYSICAL */
    public default Material i_getMaterial(IBlockState state) {
        QuadrumTrait<Physical> trait = getObject().traits.get(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(state).material;
        } else {
            return Material.ROCK;
        }
    }

    public default float i_getHardness(IBlockState blockState) {
        QuadrumTrait<Physical> trait = getObject().traits.get(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(blockState).hardness;
        } else {
            return 1F;
        }
    }

    public default float i_getExplosionResistance(IBlockState blockState) {
        QuadrumTrait<Physical> trait = getObject().traits.get(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(blockState).resistance;
        } else {
            return 1F;
        }
    }

    public default int i_getLightValue(IBlockState state) {
        QuadrumTrait<Physical> trait = getObject().traits.get(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(state).light;
        } else {
            return 0;
        }
    }

    public default void i_onRandomDisplayTick(IBlockState state, World world, BlockPos pos) {
        QuadrumTrait<Particle[]> trait = getObject().traits.get(Traits.BLOCK_PARTICLE);
        if (trait != null) {
            Particle[] particles = trait.getValueFromBlockState(state);

            for (Particle p : particles) {
                double x = pos.getX() + p.position.x.doubleValue();
                double y = pos.getY() + p.position.y.doubleValue();
                double z = pos.getZ() + p.position.z.doubleValue();
                double sx = p.speed.x.doubleValue();
                double sy = p.speed.y.doubleValue();
                double sz = p.speed.z.doubleValue();

                world.spawnParticle(p.type, x, y, z, sx, sy, sz);
            }
        }
    }

    public default boolean i_canProvidePower(IBlockState state) {
        QuadrumTrait<Redstone> trait = getObject().traits.get(Traits.BLOCK_REDSTONE);
        if (trait != null) {
            return trait.getValueFromBlockState(state) != null;
        } else {
            return false;
        }
    }

    public default int i_getWeakPower(IBlockState state, EnumFacing facing) {
        QuadrumTrait<Redstone> trait = getObject().traits.get(Traits.BLOCK_REDSTONE);
        if (trait != null) {
            Redstone redstone = trait.getValueFromBlockState(state);
            if (redstone.sides.contains(facing.getOpposite()))
                return redstone.weakPower;
        }

        return 0;
    }

    public default int i_getStrongPower(IBlockState state, EnumFacing facing) {
        QuadrumTrait<Redstone> trait = getObject().traits.get(Traits.BLOCK_REDSTONE);
        if (trait != null) {
            Redstone redstone = trait.getValueFromBlockState(state);
            if (redstone.sides.contains(facing.getOpposite()))
                return redstone.strongPower;
        }

        return 0;
    }

    public default boolean i_isFullCube(IBlockState state) {
        QuadrumTrait<BlockVisual> trait = getObject().traits.get(Traits.BLOCK_VISUAL);
        if (trait != null) {
            return !trait.getValueFromBlockState(state).fullCube;
        } else {
            return state.getMaterial().isOpaque() && state.isFullCube();
        }
    }

    public default boolean i_isOpaqueCube(IBlockState state) {
        QuadrumTrait<BlockVisual> trait = getObject().traits.get(Traits.BLOCK_VISUAL);
        if (trait != null) {
            return !trait.getValueFromBlockState(state).transparent;
        } else {
            return true;
        }
    }

    public default boolean i_canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        QuadrumTrait<BlockVisual> trait = getObject().traits.get(Traits.BLOCK_VISUAL);
        if (trait != null) {
            BlockVisual visual = trait.getValueFromBlockState(state);
            return visual.transparent ? layer == BlockRenderLayer.TRANSLUCENT : layer == BlockRenderLayer.SOLID;
        } else {
            return layer == BlockRenderLayer.SOLID;
        }
    }
}
