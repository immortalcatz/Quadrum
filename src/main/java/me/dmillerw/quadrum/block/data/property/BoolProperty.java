package me.dmillerw.quadrum.block.data.property;

import me.dmillerw.quadrum.block.BlockQuadrum;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * @author dmillerw
 */
public class BoolProperty extends BaseProperty<Boolean> {

    @Override
    public Boolean buildDefaultValue() {
        return this.defaultValue.getAsBoolean();
    }

    @Override
    public IProperty<Boolean> buildProperty() {
        return PropertyBool.create(this.name);
    }

    @Override
    protected String[] buildPossibleStates() {
        return new String[]{"false", "true"};
    }

    @Override
    public int damageDropped(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty()) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(BlockQuadrum block, int meta) {
        return block.getDefaultState().withProperty(getProperty(), meta != 0);
    }

    @Override
    public int getMetaFromState(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty()) ? 1 : 0;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
    }
}
