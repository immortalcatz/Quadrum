package me.dmillerw.quadrum.data.property;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.lib.GsonLib;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

/**
 * @author dmillerw
 */
public class DirectionProperty extends BaseProperty<EnumFacing> {

    @Override
    public EnumFacing buildDefaultValue() {
        return GsonLib.gson().fromJson(this.defaultValue, EnumFacing.class);
    }

    @Override
    public IProperty<EnumFacing> buildProperty() {
        return PropertyDirection.create(this.name);
    }

    @Override
    protected String[] buildPossibleStates() {
        String[] array = new String[EnumFacing.values().length];

        for (EnumFacing facing : EnumFacing.values()) {
            array[facing.getIndex()] = facing.name();
        }

        return array;
    }

    public int damageDropped(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty()).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(BlockQuadrum block, int meta) {
        return block.getDefaultState().withProperty(getProperty(), EnumFacing.values()[meta]);
    }

    @Override
    public int getMetaFromState(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty()).getIndex();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        for (EnumFacing facing : EnumFacing.values()) {
            list.add(new ItemStack(itemIn, 1, facing.getIndex()));
        }
    }
}
