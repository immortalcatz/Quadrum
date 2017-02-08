package me.dmillerw.quadrum.block;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.block.data.BlockData;
import me.dmillerw.quadrum.lib.IQuadrumObject;
import me.dmillerw.quadrum.lib.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

        // Block States
        if (blockData.property != null) {
            IProperty mainProperty = blockData.property.getProperty();
            Comparable defaultValue = blockData.property.getDefaultValue();

            this.setDefaultState(this.blockState.getBaseState().withProperty(mainProperty, defaultValue));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        if (blockData.property != null && blockData.property.useSubtypes) {
            blockData.property.getSubBlocks(itemIn, tab, list);
        } else {
            super.getSubBlocks(itemIn, tab, list);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return blockData.property != null ? blockData.property.damageDropped(this, state) : super.damageDropped(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return blockData.property != null ? blockData.property.getStateFromMeta(this, meta) : super.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return blockData.property != null ? blockData.property.getMetaFromState(this, state) : super.getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        List<IProperty> properties = Lists.newArrayList();
        if (HACK.property != null) properties.add(HACK.property.getProperty());
        return new BlockStateContainer(this, properties.toArray(new IProperty[0]));
    }

    @Override
    public BlockData getObject() {
        return blockData;
    }
}
