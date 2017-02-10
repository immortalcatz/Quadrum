package me.dmillerw.quadrum.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class ModCreativeTab extends CreativeTabs {

    public static final ModCreativeTab TAB = new ModCreativeTab();

    public ModCreativeTab() {
        super(ModInfo.MOD_ID);
    }

    @Override
    public Item getTabIconItem() {
        return Items.ENDER_EYE;
    }
}
