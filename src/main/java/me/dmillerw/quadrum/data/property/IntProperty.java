package me.dmillerw.quadrum.data.property;

import com.google.gson.JsonObject;
import me.dmillerw.quadrum.block.BlockQuadrum;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * @author dmillerw
 */
public class IntProperty extends BaseProperty<Integer> {

    private int min = 0;
    private int max = 1;

    @Override
    public void loadAdditionalData(JsonObject object) {
        min = object.get("min").getAsInt();
        max = object.get("max").getAsInt();
    }

    @Override
    public Integer buildDefaultValue() {
        return this.defaultValue.getAsInt();
    }

    @Override
    public IProperty<Integer> buildProperty() {
        return PropertyInteger.create(this.name, min, max);
    }

    @Override
    protected String[] buildPossibleStates() {
        String[] array = new String[max - min];

        for (int i = min; i <= max; i++) {
            array[i] = Integer.toString(i);
        }

        return array;
    }

    @Override
    public int damageDropped(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty());
    }

    @Override
    public IBlockState getStateFromMeta(BlockQuadrum block, int meta) {
        return block.getDefaultState().withProperty(getProperty(), meta);
    }

    @Override
    public int getMetaFromState(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty());
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int i = min; i <= max; i++) {
            list.add(new ItemStack(itemIn, 1, i));
        }
    }
}
