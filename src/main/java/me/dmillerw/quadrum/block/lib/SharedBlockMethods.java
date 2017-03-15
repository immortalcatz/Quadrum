package me.dmillerw.quadrum.block.lib;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.IQuadrumBlock;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.feature.trait.TraitHolder;
import me.dmillerw.quadrum.feature.trait.Traits;
import me.dmillerw.quadrum.feature.trait.impl.block.*;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * @author dmillerw
 */
public class SharedBlockMethods {

    public static void construct(IQuadrumBlock block) {
        BlockData data = block.getObject();

        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab.getTabLabel().equalsIgnoreCase(data.creativeTab)) {
                ((Block) block).setCreativeTab(tab);
                break;
            }
        }

        if (((Block) block).getCreativeTabToDisplayOn() == null) ((Block) block).setCreativeTab(ModCreativeTab.TAB);
    }

    /* BLOCK STATE */
    public static BlockStateContainer getBlockStateContainer(IQuadrumBlock block) {
        BlockData data = BlockQuadrum.HACK;
        PropertyHandler propertyHandler = BlockQuadrum.HACK.properties.propertyHandler;

        if (propertyHandler.hasSubtypes(data)) {
            return new BlockStateContainer((Block) block, propertyHandler.getBlockProperty(data));
        } else {
            return new BlockStateContainer((Block) block);
        }
    }

    public static IBlockState getDefaultBlockState(IQuadrumBlock block, IBlockState baseState) {
        BlockData data = block.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        if (propertyHandler.hasSubtypes(data)) {
            IProperty property = propertyHandler.getBlockProperty(data);
            return baseState.withProperty(property, propertyHandler.getDefaultState(data));
        } else {
            return baseState;
        }
    }

    public static int getMetadataFromBlockState(IQuadrumBlock block, IBlockState blockState) {
        BlockData data = block.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        if (propertyHandler.hasSubtypes(data)) {
            return propertyHandler.getMetaFromState(data, blockState);
        }

        return 0;
    }

    public static IBlockState getBlockStateFromMetadata(IQuadrumBlock block, int metadata) {
        BlockData data = block.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        IBlockState defaultState = ((Block) block).getDefaultState();

        if (propertyHandler.hasSubtypes(data)) {
            IProperty property = propertyHandler.getBlockProperty(data);
            return defaultState.withProperty(property, propertyHandler.getStateFromMeta(data, metadata));
        } else {
            return defaultState;
        }
    }

    /* SUB BLOCKS */
    public static void getSubBlocks(IQuadrumBlock block, NonNullList<ItemStack> list) {
        BlockData data = block.getObject();
        PropertyHandler propertyHandler = data.properties.propertyHandler;

        if (propertyHandler.hasSubtypes(data)) {
            for (int i = 0; i < propertyHandler.getSubtypes(data).length; i++) {
                list.add(new ItemStack(Item.getItemFromBlock((Block) block), 1, i));
            }
        } else {
            list.add(new ItemStack(Item.getItemFromBlock((Block) block), 1, 0));
        }
    }

    /* TRAIT - BOUNDING BOX */
    public static AxisAlignedBB getSelectionBoundingBox(IQuadrumBlock block, IBlockState state) {
        TraitHolder<BoundingBox> trait = block.getObject().getTrait(Traits.BLOCK_BOUNDING_BOX);
        if (trait != null) {
            return trait.getValueFromBlockState(state).getSelectionBoundingBox();
        } else {
            return Block.FULL_BLOCK_AABB;
        }
    }

    public static AxisAlignedBB getCollisionBoundingBox(IQuadrumBlock block, IBlockState state, IBlockAccess world, BlockPos position) {
        TraitHolder<BoundingBox> trait = block.getObject().getTrait(Traits.BLOCK_BOUNDING_BOX);
        if (trait != null) {
            return trait.getValueFromBlockState(state).getCollisionBoundingBox();
        } else {
            return state.getBoundingBox(world, position);
        }
    }

    /* TRAIT - PHYSICAL */
    public static Material getMaterial(IQuadrumBlock block, IBlockState state) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(state).material;
        } else {
            return Material.ROCK;
        }
    }

    public static float getHardness(IQuadrumBlock block, IBlockState blockState) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(blockState).hardness;
        } else {
            return 1F;
        }
    }

    public static float getExplosionResistance(IQuadrumBlock block, IBlockState blockState) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(blockState).resistance;
        } else {
            return 1F;
        }
    }

    public static int getLightValue(IQuadrumBlock block, IBlockState state) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            return trait.getValueFromBlockState(state).light;
        } else {
            return 0;
        }
    }

    public static String getHarvestTool(IQuadrumBlock block, IBlockState state) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            Physical physical = trait.getValueFromBlockState(state);
            if (physical != null) {
                return physical.harvestLevel.tool;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static int getHarvestLevel(IQuadrumBlock block, IBlockState state) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            Physical physical = trait.getValueFromBlockState(state);
            if (physical != null) {
                return physical.harvestLevel.level;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /* PHYSICAL - GRAVITY */
    public static void onBlockAdded(IQuadrumBlock block, World worldIn, BlockPos pos, IBlockState state) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            Physical physical = trait.getValueFromBlockState(worldIn.getBlockState(pos));
            if (physical.gravity) {
                worldIn.scheduleUpdate(pos, (Block) block, 2);
            }
        }
    }

    public static void neighborChanged(IQuadrumBlock block, IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            Physical physical = trait.getValueFromBlockState(worldIn.getBlockState(pos));
            if (physical.gravity) {
                worldIn.scheduleUpdate(pos, (Block) block, 2);
            }
        }
    }

    public static void updateTick(IQuadrumBlock block, World worldIn, BlockPos pos, IBlockState state, Random rand) {
        TraitHolder<Physical> trait = block.getObject().getTrait(Traits.BLOCK_PHYSICAL);
        if (trait != null) {
            Physical physical = trait.getValueFromBlockState(worldIn.getBlockState(pos));
            if (physical.gravity) {
                if (!worldIn.isRemote) {
                    SharedBlockMethods.checkForFallingConditions(worldIn, pos);
                }
            }
        }
    }

    private static void checkForFallingConditions(World worldIn, BlockPos pos) {
        if ((worldIn.isAirBlock(pos.down()) || BlockFalling.canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0) {
            int i = 32;

            if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
                if (!worldIn.isRemote) {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                    worldIn.spawnEntity(entityfallingblock);
                }
            } else {
                IBlockState state = worldIn.getBlockState(pos);
                worldIn.setBlockToAir(pos);
                BlockPos blockpos;

                for (blockpos = pos.down(); (worldIn.isAirBlock(blockpos) || BlockFalling.canFallThrough(worldIn.getBlockState(blockpos))) && blockpos.getY() > 0; blockpos = blockpos.down()) {
                    ;
                }

                if (blockpos.getY() > 0) {
                    worldIn.setBlockState(blockpos.up(), state); //Forge: Fix loss of state information during world gen.
                }
            }
        }
    }

    /* TRAIT - REDSTONE */
    public static boolean canProvidePower(IQuadrumBlock block, IBlockState state) {
        TraitHolder<Redstone> trait = block.getObject().getTrait(Traits.BLOCK_REDSTONE);
        if (trait != null) {
            return trait.getValueFromBlockState(state) != null;
        } else {
            return false;
        }
    }

    public static int getWeakPower(IQuadrumBlock block, IBlockState state, EnumFacing facing) {
        TraitHolder<Redstone> trait = block.getObject().getTrait(Traits.BLOCK_REDSTONE);
        if (trait != null) {
            Redstone redstone = trait.getValueFromBlockState(state);
            if (redstone.sides.contains(facing.getOpposite()))
                return redstone.weakPower;
        }

        return 0;
    }

    public static int getStrongPower(IQuadrumBlock block, IBlockState state, EnumFacing facing) {
        TraitHolder<Redstone> trait = block.getObject().getTrait(Traits.BLOCK_REDSTONE);
        if (trait != null) {
            Redstone redstone = trait.getValueFromBlockState(state);
            if (redstone.sides.contains(facing.getOpposite()))
                return redstone.strongPower;
        }

        return 0;
    }

    /* TRAIT - VISUAL */
    public static boolean isFullCube(IQuadrumBlock block, IBlockState state) {
        TraitHolder<BlockVisual> trait = block.getObject().getTrait(Traits.BLOCK_VISUAL);
        if (trait != null) {
            return !trait.getValueFromBlockState(state).fullCube;
        } else {
            return state.getMaterial().isOpaque() && state.isFullCube();
        }
    }

    public static boolean isOpaqueCube(IQuadrumBlock block, IBlockState state) {
        TraitHolder<BlockVisual> trait = block.getObject().getTrait(Traits.BLOCK_VISUAL);
        if (trait != null) {
            return !trait.getValueFromBlockState(state).transparent;
        } else {
            return true;
        }
    }

    public static boolean canRenderInLayer(IQuadrumBlock block, IBlockState state, BlockRenderLayer layer) {
        TraitHolder<BlockVisual> trait = block.getObject().getTrait(Traits.BLOCK_VISUAL);
        if (trait != null) {
            BlockVisual visual = trait.getValueFromBlockState(state);
            return visual.transparent ? layer == BlockRenderLayer.TRANSLUCENT : layer == BlockRenderLayer.SOLID;
        } else {
            return layer == BlockRenderLayer.SOLID;
        }
    }

    /* TRAIT - PARTICLES */
    public static void onRandomDisplayTick(IQuadrumBlock block, IBlockState state, World world, BlockPos pos) {
        TraitHolder<Particle[]> trait = block.getObject().getTrait(Traits.BLOCK_PARTICLE);
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

    /* TRAIT - DROPS */
    public static List<ItemStack> getDrops(IQuadrumBlock block, IBlockState state, int fortune) {
        TraitHolder<Drop[]> drops = block.getObject().getTrait(Traits.BLOCK_DROP);
        if (drops != null) {
            List<ItemStack> list = Lists.newArrayList();

            for (Drop drop : drops.getValueFromBlockState(state)) {
                int count = drop.count.intValue();
                if (count == -1) {
                    list.add(drop.item);
                } else if (count > 0) {
                    ItemStack item = drop.item.copy();
                    item.setCount(count);
                    list.add(item);
                }
            }

            return list;
        } else {
            List<ItemStack> ret = Lists.newArrayList();

            int count = ((Block) block).quantityDropped(state, fortune, block.getForgeRandom());
            for (int i = 0; i < count; i++) {
                Item item = ((Block) block).getItemDropped(state, block.getForgeRandom(), fortune);
                if (item != Items.AIR) {
                    ret.add(new ItemStack(item, 1, ((Block) block).damageDropped(state)));
                }
            }

            return ret;
        }
    }
}
