package me.dmillerw.quadrum.data.property;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.lib.GsonLib;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

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
}