package me.dmillerw.quadrum.item.sub;

import me.dmillerw.quadrum.item.IQuadrumItem;
import me.dmillerw.quadrum.item.data.ItemData;
import me.dmillerw.quadrum.trait.QuadrumTrait;
import me.dmillerw.quadrum.trait.Traits;
import me.dmillerw.quadrum.trait.item.Edible;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemQuadrumFood extends ItemFood implements IQuadrumItem {

    private final ItemData itemData;
    private final QuadrumTrait<Edible> edible;

    public ItemQuadrumFood(ItemData itemData) {
        super(0, 0, false); // Retrieved via stack-sensitive getters

        this.itemData = itemData;
        this.edible = itemData.traits.get(Traits.ITEM_EDIBLE);

        this.construct();
    }

    /* ITEM_FOOD */
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return edible.getValueFromItemStack(stack).duration;
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        return edible.getValueFromItemStack(stack).health;
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        return edible.getValueFromItemStack(stack).saturation;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(edible.getValueFromItemStack(itemstack).canAlwaysEat)) {
            playerIn.setActiveHand(handIn);
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult(EnumActionResult.FAIL, itemstack);
        }
    }

    /* I_QUADRUM_ITEM */
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
