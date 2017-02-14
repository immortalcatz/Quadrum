package me.dmillerw.quadrum.item;

import me.dmillerw.quadrum.item.data.ItemData;
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

        this.construct();
    }

    /* I_QUADRUM_ITEM */

    @Override
    public boolean hasEffect(ItemStack stack) {
        return this.isEnchanted(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        this.addLoreToTooltip(stack, tooltip);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedStackName(stack);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        this.getItemsForCreativeTab(subItems);
    }

    @Override
    public ItemData getObject() {
        return itemData;
    }
}
