package me.dmillerw.quadrum.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class ItemHasSubtypes extends ItemBlock {

    private String[] subtypeNames;

    public ItemHasSubtypes(Block block) {
        super(block);

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int damage) {
        return damage;
    }

    public ItemHasSubtypes setSubtypeNames(String[] names) {
        this.subtypeNames = names;
        return this;
    }

    public String getUnlocalizedName(ItemStack stack) {
        if (this.subtypeNames == null) {
            return super.getUnlocalizedName(stack);
        } else {
            int i = stack.getMetadata();
            return i >= 0 && i < this.subtypeNames.length ? super.getUnlocalizedName(stack) + "." + this.subtypeNames[i] : super.getUnlocalizedName(stack);
        }
    }
}