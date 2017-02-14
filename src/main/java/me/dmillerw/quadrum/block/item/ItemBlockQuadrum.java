package me.dmillerw.quadrum.block.item;

import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.trait.QuadrumTrait;
import me.dmillerw.quadrum.trait.Traits;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemBlockQuadrum extends ItemBlock {

    private String[] subtypeNames;

    public ItemBlockQuadrum(Block block, boolean hasSubtypes) {
        super(block);

        setMaxStackSize(((BlockQuadrum)block).getObject().maxStackSize);

        if (hasSubtypes) {
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        QuadrumTrait<List<String>> trait = ((BlockQuadrum)block).getObject().traits.get(Traits.LORE);
        if (trait != null) {
            tooltip.addAll(trait.getValueFromItemStack(stack));
        }
    }

    public int getMetadata(int damage) {
        return damage;
    }

    public ItemBlockQuadrum setSubtypeNames(String[] names) {
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