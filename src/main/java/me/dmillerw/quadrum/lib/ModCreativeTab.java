package me.dmillerw.quadrum.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class ModCreativeTab extends CreativeTabs {

    public static final ModCreativeTab TAB = new ModCreativeTab();

    public ModCreativeTab() {
        super(ModInfo.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.ENDER_EYE);
    }
}
