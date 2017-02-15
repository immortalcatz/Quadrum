package me.dmillerw.quadrum.block;

import me.dmillerw.quadrum.block.data.BlockData;
import me.dmillerw.quadrum.trait.QuadrumTrait;
import me.dmillerw.quadrum.trait.Traits;
import me.dmillerw.quadrum.trait.data.block.Physical;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author dmillerw
 */
public class BlockQuadrum extends Block implements IQuadrumBlock {

    public static BlockData HACK = null;

    private final BlockData blockData;
    private final QuadrumTrait<Physical> traitPhysical;

    public BlockQuadrum(BlockData blockData) {
        super(Material.ROCK); // Handled via state-sensitive getter

        this.blockData = blockData;
        this.traitPhysical = blockData.traits.get(Traits.BLOCK_PHYSICAL);

        this.construct();
        this.setDefaultState(this.i_getDefaultState(this, blockState.getBaseState()));
    }

    /* TRAIT - PHYSICAL */
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (traitPhysical != null) {
            Physical physical = traitPhysical.getValueFromBlockState(worldIn.getBlockState(pos));
            if (physical.gravity) {
                worldIn.scheduleUpdate(pos, this, 2);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (traitPhysical != null) {
            Physical physical = traitPhysical.getValueFromBlockState(worldIn.getBlockState(pos));
            if (physical.gravity) {
                worldIn.scheduleUpdate(pos, this, 2);
            }
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (traitPhysical != null) {
            Physical physical = traitPhysical.getValueFromBlockState(worldIn.getBlockState(pos));
            if (physical.gravity) {
                if (!worldIn.isRemote) {
                    this.checkForFallingConditions(worldIn, pos);
                }
            }
        }
    }

    private void checkForFallingConditions(World worldIn, BlockPos pos) {
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

    /* I_QUADRUM_BLOCK */
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.i_getSelectionBoundingBox(state);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return this.i_getCollisionBoundingBox(blockState, worldIn, pos);
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return this.i_getMaterial(state);
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return this.i_getHardness(blockState);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return this.i_getExplosionResistance(world.getBlockState(pos));
    }

    @Override
    public int getLightValue(IBlockState state) {
        return this.i_getLightValue(state);
    }

    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        this.i_onRandomDisplayTick(state, world, pos);
    }

    // Variants
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        this.i_getSubBlocks(itemIn, list);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.i_getStateFromMetadata(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.i_getMetadataFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return this.i_getBlockStateContainer();
    }

    @Override
    public BlockData getObject() {
        return blockData;
    }
}
