package me.dmillerw.quadrum.item;

import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.item.lib.CommonItemMethods;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemQuadrum extends Item implements IQuadrumItem {

    private final ItemData itemData;

    public ItemQuadrum(ItemData itemData) {
        super();

        this.itemData = itemData;

        CommonItemMethods.construct(this);
    }

    @Override
    public ItemData getObject() {
        return itemData;
    }

    /* TRAIT - TOOLTIP */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        CommonItemMethods.addLoreToTooltip(this, stack, tooltip);
    }

    /* TRAIT - VISUAL */
    @Override
    public boolean hasEffect(ItemStack stack) {
        return CommonItemMethods.isEnchanted(this, stack);
    }

    /* PROPERTY / VARIANTS */
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return CommonItemMethods.getUnlocalizedStackName(this, stack);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        CommonItemMethods.getItemsForCreativeTab(this, subItems);
    }


}
