package me.dmillerw.quadrum.block;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.data.BlockData;
import me.dmillerw.quadrum.lib.IQuadrumObject;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

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

        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab.getTabLabel().equalsIgnoreCase(blockData.creativeTab)) {
                setCreativeTab(tab);
                break;
            }
        }
        if (getCreativeTabToDisplayOn() == null) setCreativeTab(ModCreativeTab.TAB);

        if (blockData.properties.main != null) {
            IProperty mainProperty = blockData.properties.main.getProperty();
            Comparable defaultValue = blockData.properties.main.getDefaultValue();

            this.setDefaultState(this.blockState.getBaseState().withProperty(mainProperty, defaultValue));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return blockData.properties.main != null ? blockData.properties.main.damageDropped(this, state) : super.damageDropped(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return blockData.properties.main != null ? blockData.properties.main.getStateFromMeta(this, meta) : super.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return blockData.properties.main != null ? blockData.properties.main.getMetaFromState(this, state) : super.getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        List<IProperty> properties = Lists.newArrayList();
        if (HACK.properties.main != null) properties.add(HACK.properties.main.getProperty());
        return new BlockStateContainer(this, properties.toArray(new IProperty[0]));
    }

    @Override
    public BlockData getObject() {
        return blockData;
    }
}
