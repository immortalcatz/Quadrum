package me.dmillerw.quadrum.data.property;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.lib.GsonLib;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;

/**
 * @author dmillerw
 */
public class ColorProperty extends BaseProperty<EnumDyeColor> {

    @Override
    public EnumDyeColor buildDefaultValue() {
        return GsonLib.gson().fromJson(this.defaultValue, EnumDyeColor.class);
    }

    @Override
    public IProperty<EnumDyeColor> buildProperty() {
        return PropertyEnum.create(this.name, EnumDyeColor.class);
    }

    public int damageDropped(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty()).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(BlockQuadrum block, int meta) {
        return block.getDefaultState().withProperty(getProperty(), EnumDyeColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(BlockQuadrum block, IBlockState state) {
        return state.getValue(getProperty()).getMetadata();
    }
}
